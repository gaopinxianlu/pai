package cn.pai.connect.http.param;

import okhttp3.Callback;
import okhttp3.Request.Builder;

/**
 * requestBody请求提交
 * @author pany
 * 2017年7月23日
 */
public abstract class BodySubmit extends Submit {

	public BodySubmit(Builder builder) {
		super(builder);
		// TODO Auto-generated constructor stub
	}

	public void post(Callback call) {
		enqueue(builder.post(buildBody()).build(), call);
	}

	public void patch(Callback call) {
		enqueue(builder.patch(buildBody()).build(), call);
	}

	public void put(Callback call) {
		enqueue(builder.put(buildBody()).build(), call);
	}
	
	public void delete(Callback call) {
		enqueue(builder.delete(buildBody()).build(), call);
	}
}
