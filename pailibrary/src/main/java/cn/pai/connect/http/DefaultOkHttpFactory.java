package cn.pai.connect.http;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import cn.pai.connect.http.cookie.CookieJarImpl;
import cn.pai.connect.http.cookie.CookieMemory;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author pany
 * @description 默认OkHttpClient创建
 * @date 2017年6月9日下午2:50:26
 */
public class DefaultOkHttpFactory implements OkHttpFactory {

    public static final int DEFAULT_TIME = 30 * 1000; // 默认的超时时间

    private Context context;

    public DefaultOkHttpFactory(Context context) {
        this.context = context;
    }

    @Override
    public OkHttpClient create() {
        final File cacheFile = new File(context.getExternalCacheDir(),
                "httpCache");
        // 创建缓存对象
        final Cache cache = new Cache(cacheFile, 10 * 1024 * 1024);// 缓存大小为10M
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder
                .retryOnConnectionFailure(false)
                // 失败不重发
                .connectTimeout(DEFAULT_TIME, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIME, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIME, TimeUnit.MILLISECONDS).cache(cache)// 缓存
                .cookieJar(new CookieJarImpl(new CookieMemory())).build();

        return clientBuilder.build();
    }
}
