package cn.pai.connect.http.param;

import cn.pai.common.log.Loger;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author pany
 * @description url携带参数提交, get和head请求
 * @date 2017年6月8日下午3:52:28
 */
public class JoinSubmit extends Submit {

    private StringBuffer param = new StringBuffer();

    private StringBuffer urlBuffer;

    public JoinSubmit(Request.Builder builder, String url) {
        super(builder);
        // TODO Auto-generated constructor stub
        this.urlBuffer = new StringBuffer(url);
    }

    @Override
    public RequestBody buildBody() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 设置参数
     */
    public JoinSubmit param(String key, String value) {
        Loger.d("请求参数：" + key + "=" + value);
        param.append(key).append("=").append(value).append("&");
        return this;
    }

    public void get(Callback call) {
        String url = param.length() > 0 ? urlBuffer.append("?").append(param.substring(0, param.length() - 1)).toString()
                : urlBuffer.toString();
        builder.url(url);
        Loger.d("请求url：" + url);
        enqueue(builder.get().build(), call);
    }

    public void head(Callback call) {
        enqueue(builder.head().build(), call);
    }
}
