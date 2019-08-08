package cn.pai.connect.http.cookie;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 
 * @description cookie的保存接口
 * @author pany
 * @date 2017年6月9日上午11:14:05
 */
public interface CookieStore {

	/** 保存url对应所有cookie */
    void saveCookie(HttpUrl url, List<Cookie> cookie);
    
    /** 加载url所有的cookie */
    List<Cookie> loadCookie(HttpUrl url);
}
