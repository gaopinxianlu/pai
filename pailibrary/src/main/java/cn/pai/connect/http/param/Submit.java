package cn.pai.connect.http.param;

import cn.pai.connect.http.OkHttp;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 
 * @description 参数基类
 * @author pany
 * @date 2017年6月8日下午3:50:23
 */
public abstract class Submit {

	protected Request.Builder builder;

	public Submit(Request.Builder builder) {
		// TODO Auto-generated constructor stub
		this.builder = builder;
	}

	/**
	 * 组建请求体
	 * 
	 * @return
	 */
	protected abstract RequestBody buildBody();

	/**
	 * 异步提交
	 * 
	 * @param call
	 */
	protected void enqueue(Request request, Callback call) {
		OkHttp.getInstance().enqueue(request, call);
	}

}
