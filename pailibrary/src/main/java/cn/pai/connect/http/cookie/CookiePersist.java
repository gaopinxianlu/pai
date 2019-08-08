package cn.pai.connect.http.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 
 * @description 持久化cookie
 * @author pany
 * @date 2017年6月9日上午11:22:37
 */
public class CookiePersist implements CookieStore{

	@Override
	public void saveCookie(HttpUrl url, List<Cookie> cookie) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Cookie> loadCookie(HttpUrl url) {
		// TODO Auto-generated method stub
		return null;
	}

}
