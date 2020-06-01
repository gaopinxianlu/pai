package cn.pai.connect.http;

import android.content.Context;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author pany
 * @description Okhttp创建接口
 * @date 2017年6月9日下午2:50:26
 */
public abstract class OkHttpFactory {

    protected Context context;

    public OkHttpFactory(Context context) {
        this.context = context;
    }

    /**
     * 创建OkHttpClient
     *
     * @return
     */
    public abstract OkHttpClient create();

    /**
     * 超时时间
     *
     * @return 默认超时未20秒
     */
    protected int getTimeOut() {
        return 20000;
    }

    /**
     * 网络缓存路径及大小
     *
     * @return
     */
    protected Cache getCache() {
        File cacheFile = new File(context.getExternalCacheDir(),
                "httpCache");
        // 创建缓存对象
        Cache cache = new Cache(cacheFile, 10 * 1024 * 1024);// 缓存大小为10M
        return cache;
    }
}
