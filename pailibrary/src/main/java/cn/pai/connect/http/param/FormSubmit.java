package cn.pai.connect.http.param;


import cn.pai.common.log.Loger;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author pany
 * @description 表单参数提交
 * @date 2017年6月8日下午3:52:28
 */
public class FormSubmit extends BodySubmit {

    private FormBody.Builder builder = new FormBody.Builder();

    public FormSubmit(Request.Builder builder) {
        super(builder);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected RequestBody buildBody() {
        // TODO Auto-generated method stub
        return builder.build();
    }

    /**
     * 设置参数
     *
     * @param key
     * @param value
     * @return
     */
    public FormSubmit param(String key, String value) {
        Loger.d("请求参数：" + key + "=" + value);
        builder.add(key, value);
        return this;
    }

    /**
     * 可编码参数设置
     *
     * @param key
     * @param value
     * @return
     */
    public FormSubmit paramEn(String key, String value) {
        Loger.d("请求参数：" + key + "=" + value);
        builder.addEncoded(key, value);
        return this;
    }
}
