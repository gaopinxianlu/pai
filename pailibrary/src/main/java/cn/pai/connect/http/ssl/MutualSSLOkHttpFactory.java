package cn.pai.connect.http.ssl;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import cn.pai.connect.http.OkHttpFactory;
import cn.pai.connect.http.cookie.CookieJarImpl;
import cn.pai.connect.http.cookie.CookieMemory;
import okhttp3.OkHttpClient;

/**
 * @author pany
 * @description SSL双向认证OkHttpClient创建
 * @date 2017年6月9日下午2:50:26
 */
public class MutualSSLOkHttpFactory extends OkHttpFactory {

    public MutualSSLOkHttpFactory(Context context) {
        super(context);
    }

    @Override
    public OkHttpClient create() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream priKey = assetManager.open("certificate/ca-cert.pem");
            InputStream pubKey = assetManager.open("certificate/ca-cert.pem");
            clientBuilder
                    .retryOnConnectionFailure(false)// 失败不重发
                    .connectTimeout(getTimeOut(), TimeUnit.MILLISECONDS)
                    .writeTimeout(getTimeOut(), TimeUnit.MILLISECONDS)
                    .readTimeout(getTimeOut(), TimeUnit.MILLISECONDS)
                    .cache(getCache()).cookieJar(new CookieJarImpl(new CookieMemory()))
                    .sslSocketFactory(SSLSocketHelper.getMutualSSLFactory(priKey, "", pubKey, ""))
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clientBuilder.build();
    }
}
