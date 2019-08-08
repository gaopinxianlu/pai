package cn.pai.connect.http.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 
 * @description 使用内存存储cookie
 * @author pany
 * @date 2017年6月9日上午11:16:47
 */
public class CookieMemory implements CookieStore {
	
	/**
	 * 存储cookie
	 */
	private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

	@Override
	public void saveCookie(HttpUrl url, List<Cookie> cookies) {
		// TODO Auto-generated method stub
		cookieStore.put(url.host(), cookies);
	}

	@Override
	public List<Cookie> loadCookie(HttpUrl url) {
		// TODO Auto-generated method stub
		List<Cookie> cookies = cookieStore.get(url.host());
		return cookies != null ? cookies : new ArrayList<Cookie>();
	}

}
