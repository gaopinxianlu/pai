package cn.pai.connect.http.param;


import cn.pai.common.log.Loger;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author pany
 * @description okhttp 文本格式参数 ,mediaType默认是text/plain;charset=utf-8
 * @date 2017年6月8日下午3:54:24
 */
public class TextSubmit extends BodySubmit {

    private String mediaType = "text/plain;charset=utf-8";
    private String text;

    public TextSubmit(Request.Builder builder) {
        super(builder);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected RequestBody buildBody() {
        // TODO Auto-generated method stub
        RequestBody body = RequestBody.create(MediaType.parse(mediaType), text);
        return body;
    }

    /**
     * 默认是text/plain;charset=utf-8
     *
     * @param mediaType
     * @return
     */
    public TextSubmit mediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    /**
     * 设置请求文本
     *
     * @param text
     * @return
     */
    public TextSubmit param(String text) {
        // TODO Auto-generated method stub
        Loger.d("请求参数：" + text);
        this.text = text;
        return this;
    }
}
