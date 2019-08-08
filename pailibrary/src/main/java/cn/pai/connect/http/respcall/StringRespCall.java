package cn.pai.connect.http.respcall;

import java.io.IOException;

import okhttp3.Response;

/**
 * 
 * @description okhttp返回string
 * @author pany
 * @date 2017年6月19日上午9:23:22
 */
public abstract class StringRespCall extends RespCall<String> {

	@Override
	protected String convert(Response response) throws IOException {
		// TODO Auto-generated method stub
		return response.body().string();
	}

}
