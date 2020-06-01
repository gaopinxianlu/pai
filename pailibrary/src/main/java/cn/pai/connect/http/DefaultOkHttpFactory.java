package cn.pai.connect.http;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import cn.pai.connect.http.cookie.CookieJarImpl;
import cn.pai.connect.http.cookie.CookieMemory;
import okhttp3.OkHttpClient;

/**
 * @author pany
 * @description 默认OkHttpClient创建
 * @date 2017年6月9日下午2:50:26
 */
public class DefaultOkHttpFactory extends OkHttpFactory {

    public DefaultOkHttpFactory(Context context) {
        super(context);
    }

    @Override
    public OkHttpClient create() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder
                .retryOnConnectionFailure(false)// 失败不重发
                .connectTimeout(getTimeOut(), TimeUnit.MILLISECONDS)
                .writeTimeout(getTimeOut(), TimeUnit.MILLISECONDS)
                .readTimeout(getTimeOut(), TimeUnit.MILLISECONDS)
                .cache(getCache())// 缓存
                .cookieJar(new CookieJarImpl(new CookieMemory()))
                .build();
        return clientBuilder.build();
    }
}
