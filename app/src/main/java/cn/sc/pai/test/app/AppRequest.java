package cn.sc.pai.test.app;

import android.content.Context;
import android.os.Handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import cn.pai.common.log.Loger;
import cn.pai.connect.http.request.OkRequest;
import cn.pai.connect.http.respcall.JsonRespCall;

/**
 * 应用请求
 * author：pany
 * on 2017/12/1 14:59
 */
public class AppRequest {

    /**
     * 系统参数
     */
    public static final String CHARSET = "UTF-8";//编码格式
    public static final String ALGORITHM = "SHA256WithRSA";//签名算法
    public static final String CLIENTID = "liandi-scrcu-scanCodeToPay";//生产-开放平台分配唯一的客户端ID
    public static final String SECRET = "YX6LXXXf0eM5kb/vuuX9zbweFB80AVmZZO7tnIi4RY=";//生产-开放平台分配秘钥
    public static final String PRIKEYPATH = "pri_scr.pfx";//生产-加签私钥证书
    public static final String PRIKEYPWD = "12345678";//生产-加签私钥密码
    public static final String PUBKEYPATH = "pub_scr.cer";//生产-验签公钥证书


//    public static final String CLIENTID = "test-ebap-sm3";//测试-开放平台分配唯一的客户端ID
//    public static final String SECRET = "FOFFrakbZnKb7sVZhw+N2Q==";//测试-开放平台分配秘钥
//    public static final String PRIKEYPATH = "test.pfx";//测试-加签私钥证书
//    public static final String PRIKEYPWD = "test";//测试-加签私钥密码
//    public static final String PUBKEYPATH = "test.cer";//测试-验签公钥证书

    private Context context;
    private Handler handler;
    private StringBuffer urlBuffer;//url

    private Map<String, String> param;//参数

    public AppRequest(Context context) {
        this.context = context;
        this.handler = new Handler();
        this.param = new HashMap<>();
    }

    public AppRequest baseUrl(String url) {
        urlBuffer = new StringBuffer(url);
        return this;
    }

    public AppRequest action(String action) {
        urlBuffer.append(action);
        return this;
    }

    /**
     * 业务参数
     *
     * @param key
     * @param value
     */
    public AppRequest param(String key, String value) {
        param.put(key, value);
        return this;
    }

    /**
     * 回到主线程
     */
    private void toUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    /**
     * 发送数据
     *
     * @param requestCall
     */
    public void send(final RequestCall requestCall) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    param.put("signAlgorithm", ALGORITHM);//签名算法
                    param.put("clientId", CLIENTID);//开放平台分配唯一的客户端ID
                    param.put("secret", SECRET);//开放平台分配秘钥
                    param.put("isDevice", "0");//是否终端 0-是 1-否 （其他值默认否）
                    //参数加签
                    String signData = SignUtil.signMessageMap2(param);
                    InputStream priKeyStream = context.getAssets().open(PRIKEYPATH);
                    PrivateKey priKey = SignUtil.getPrivateKey(PRIKEYPATH, priKeyStream, PRIKEYPWD);
                    String sign = SignUtil.signBase64UrlEncode(CHARSET, signData, priKey, ALGORITHM);
                    param.put("signature", sign);//签名后的业务参数
                    //放入业务参数
                    //转换成key1=value1&key2=value2的形式
                    param.put("secret", URLEncoder.encode(SECRET,CHARSET));//这里需要转义开放平台分配秘钥
                    final String sendParam = SignUtil.messageMap(param);
                    //将发送参数拼接到url后面
                    urlBuffer.append("?").append(sendParam);
                    //发送请求数据
                    post(requestCall);
                } catch (final Exception e) {
                    toUiThread(new Runnable() {
                        @Override
                        public void run() {
                            requestCall.onFailure(null, e.getMessage());
                        }
                    });
                }
            }
        }).start();
    }

    /**
     * 发送请求
     */
    private void post(final RequestCall requestCall) {
        new OkRequest().url(urlBuffer.toString()).form().post(new JsonRespCall() {
            @Override
            public void onResponse(JSONObject response) {
                if ("0000000000".equals(response.getString("respCode"))) {
                    try {
                        //验签
                        InputStream pubKeyStream = context.getAssets().open(PUBKEYPATH);
                        PublicKey pubKey = SignUtil.getPublicKey(pubKeyStream);
                        String encryptData = response.getString("encryptData");
                        String signature = response.getString("signature");
                        Boolean b = SignUtil.verifyBase64(encryptData, CHARSET,
                                signature, pubKey, ALGORITHM);
                        if (b) {
                            byte[] arr = SignUtil.base64Decode(encryptData);
                            final String bussData = new String(arr, CHARSET);
                            Loger.d("业务数据:" + bussData);
                            final JSONObject bussJson = JSON.parseObject(bussData);
                            if ("0000000000".equals(bussJson.getString("respCode"))) {
                                toUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        requestCall.onSuccess(bussJson);
                                    }
                                });
                            } else {
                                onFailure(bussJson, bussJson.getString("respMsg"));
                            }
                        } else {
                            onFailure(response, "数据验签失败");
                        }
                    } catch (Exception e) {
                        onFailure(response, e.getMessage());
                    }
                } else {
                    onFailure(response, response.getString("respMsg"));
                }
            }

            @Override
            public void onFailure(final JSONObject response, final String message) {
                toUiThread(new Runnable() {
                    @Override
                    public void run() {
                        requestCall.onFailure(response, message);
                    }
                });
            }
        });
    }


    /**
     * 平台服务器定制回调
     *
     * @author pany 2017年7月24日
     */
    public static abstract class RequestCall extends JsonRespCall {

        @Override
        public void onResponse(JSONObject response) {

        }

        protected abstract void onSuccess(JSONObject response);
    }
}
