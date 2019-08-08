package cn.pai.connect.http.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 
 * @description CookieJar的实现类,用于处理cookie
 * @author pany
 * @date 2017年6月9日下午2:28:14
 */
public class CookieJarImpl implements CookieJar{
	
	private CookieStore cookieStore;

    public CookieJarImpl(CookieStore cookieStore) {
        if (cookieStore == null) {
            throw new IllegalArgumentException("cookieStore can not be null!");
        }
        this.cookieStore = cookieStore;
    }

	@Override
	public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
		// TODO Auto-generated method stub
		cookieStore.saveCookie(url, cookies);
	}

	@Override
	public List<Cookie> loadForRequest(HttpUrl url) {
		// TODO Auto-generated method stub
		return cookieStore.loadCookie(url);
	}

}
