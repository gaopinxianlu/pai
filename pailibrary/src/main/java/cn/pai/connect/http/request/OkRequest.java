package cn.pai.connect.http.request;

import android.support.v4.util.ArrayMap;

import java.util.Map.Entry;

import cn.pai.common.log.Loger;
import cn.pai.connect.http.param.FormSubmit;
import cn.pai.connect.http.param.JoinSubmit;
import cn.pai.connect.http.param.JsonSubmit;
import cn.pai.connect.http.param.MultSubmit;
import cn.pai.connect.http.param.TextSubmit;
import okhttp3.CacheControl;
import okhttp3.Request;

/**
 * @author pany
 * @description okhttp请求
 * @date 2017年6月8日下午4:23:55
 */
public class OkRequest {

    /**
     * request构造器
     */
    protected Request.Builder builder = new Request.Builder();

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求标志
     *
     * @param tag
     * @return
     */
    public OkRequest tag(Object tag) {
        Loger.d( "请求tag：" + tag);
        builder.tag(tag);
        return this;
    }

    /**
     * 请求url
     *
     * @param url
     * @return
     */
    public OkRequest url(String url) {
        Loger.d( "请求url：" + url);
        this.url = url;
        builder.url(url);
        return this;
    }

    /**
     * 请求头 可添加key值相同的header 例如:.addHeader("Accept", "1").addHeader("Accept","2")
     *
     * @param key
     * @param value
     * @return
     */
    public OkRequest headers(String key, String value) {
        Loger.d( "请求头：" + key + "=" + value);
        builder.addHeader(key, value);// 可添加同key的header
        return this;
    }

    /**
     * 请求头 覆盖key值相同的header
     *
     * @param key
     * @param value
     * @return
     */
    public OkRequest header(String key, String value) {
        Loger.d( "请求头：" + key + "=" + value);
        builder.header(key, value);
        return this;
    }

    /**
     * 缓存
     *
     * @param cacheControl
     * @return
     */
    public OkRequest cache(CacheControl cacheControl) {
        Loger.d( "请求cache：" + cacheControl);
        builder.cacheControl(cacheControl);
        return this;
    }

    /**
     * 公共参数 默认为空,子类可重写该参数
     *
     * @return
     */
    protected ArrayMap<String, String> commonParam() {
        return null;
    }

    /**
     * url拼接参数提交
     * 适合get或者head请求
     *
     * @return
     */
    public JoinSubmit join() {
        JoinSubmit submit = new JoinSubmit(builder, url);
        ArrayMap<String, String> commonParam = commonParam();
        if (commonParam != null && commonParam.size() > 0) {
            for (Entry<String, String> entry : commonParam.entrySet()) {
                submit.param(entry.getKey(), entry.getValue());
            }
        }
        return submit;
    }

    /**
     * 表单提交
     *
     * @return
     */
    public FormSubmit form() {
        FormSubmit submit = new FormSubmit(builder);
        ArrayMap<String, String> param = commonParam();
        if (param != null && param.size() > 0) {
            for (Entry<String, String> entry : param.entrySet()) {
                submit.param(entry.getKey(), entry.getValue());
            }
        }
        return submit;
    }

    /**
     * mult参数
     *
     * @return
     */
    public MultSubmit mult() {
        MultSubmit submit = new MultSubmit(builder);
        ArrayMap<String, String> param = commonParam();
        if (param != null && param.size() > 0) {
            for (Entry<String, String> entry : param.entrySet()) {
                submit.param(entry.getKey(), entry.getValue());
            }
        }
        return submit;
    }

    /**
     * json参数
     *
     * @return
     */
    public JsonSubmit json() {
        JsonSubmit submit = new JsonSubmit(builder);
        ArrayMap<String, String> param = commonParam();
        if (param != null && param.size() > 0) {
            for (Entry<String, String> entry : param.entrySet()) {
                submit.param(entry.getKey(), entry.getValue());
            }
        }
        return submit;
    }

    /**
     * json参数
     *
     * @return
     */
    public TextSubmit text() {
        TextSubmit submit = new TextSubmit(builder);
        return submit;
    }
}
