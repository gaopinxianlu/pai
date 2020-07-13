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
public class ByteSubmit extends BodySubmit {

    private String mediaType = "text/plain;charset=utf-8";
    private byte[] bytes;

    public ByteSubmit(Request.Builder builder) {
        super(builder);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected RequestBody buildBody() {
        // TODO Auto-generated method stub
        RequestBody body = RequestBody.create(MediaType.parse(mediaType), bytes);
        return body;
    }

    /**
     * 默认是text/plain;charset=utf-8
     *
     * @param mediaType
     * @return
     */
    public ByteSubmit mediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    /**
     * 设置bytes请求数据
     *
     * @param bytes
     * @return
     */
    public ByteSubmit param(byte[] bytes) {
        // TODO Auto-generated method stub
        Loger.d("请求参数：" + bytes);
        this.bytes = bytes;
        return this;
    }
}
