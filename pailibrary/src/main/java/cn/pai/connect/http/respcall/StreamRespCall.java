package cn.pai.connect.http.respcall;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import okhttp3.Response;

/**
 * description：okhttp响应byteStream结果
 * author：pany
 * on 2019/3/11 17:00
 */
public abstract class StreamRespCall extends RespCall<InputStream> {
    @Override
    protected InputStream convert(Response response) throws Exception {
        return new ByteArrayInputStream(response.body().bytes());
    }
}
