package cn.pai.connect.http.respcall;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Response;

/**
 * JSON格式回调
 * 
 * @author pany 2017年7月22日
 */
public abstract class JsonRespCall extends RespCall<JSONObject> {

	@Override
	protected JSONObject convert(Response response) throws IOException {
		// TODO Auto-generated method stub
		return JSON.parseObject(response.body().string());
	}
}
