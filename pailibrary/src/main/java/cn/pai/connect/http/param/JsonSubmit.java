package cn.pai.connect.http.param;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.pai.common.log.Loger;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author pany
 * @description json格式提交
 * @date 2017年6月7日下午2:41:53
 */
public class JsonSubmit extends BodySubmit {

    private String mediaType = "application/json;charset=utf-8";

    private JSONObject json = new JSONObject();

    public JsonSubmit(Request.Builder builder) {
        super(builder);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected RequestBody buildBody() {
        // TODO Auto-generated method stub
        RequestBody body = RequestBody.create(MediaType.parse(mediaType),
                json.toString());
        Loger.d( "请求json：" + json.toString());
        return body;
    }

    /**
     * 默认是application/json;charset=utf-8
     *
     * @param mediaType
     * @return
     */
    public JsonSubmit mediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    /**
     * 设置参数
     *
     * @param key
     * @param value
     * @return
     */
    public JsonSubmit param(String key, String value) {
        Loger.d("请求参数：" + key + "=" + value);
        json.put(key, value);
        return this;
    }

    /**
     * 设置参数
     *
     * @param key
     * @param value
     * @return
     */
    public JsonSubmit param(String key, JSONObject value) {
        Loger.d("请求参数：" + key + "=" + value);
        json.put(key, value);
        return this;
    }

    /**
     * 设置参数
     *
     * @param key
     * @param value
     * @return
     */
    public JsonSubmit param(String key, JSONArray value) {
        Loger.d("请求参数：" + key + "=" + value);
        json.put(key, value);
        return this;
    }
}
