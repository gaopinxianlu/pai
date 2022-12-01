package cn.pai.connect.http.ssl;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.pai.connect.http.OkHttpFactory;
import cn.pai.connect.http.cookie.CookieJarImpl;
import cn.pai.connect.http.cookie.CookieMemory;
import okhttp3.OkHttpClient;

/**
 * description：
 * author：pany
 * on 2019/4/26 23:25
 */
public class NoValidSSLOkHttpFactory extends OkHttpFactory {

    public NoValidSSLOkHttpFactory(Context context) {
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
                .cache(getCache()).cookieJar(new CookieJarImpl(new CookieMemory()))
                .sslSocketFactory(SSLSocketHelper.getNoValidSSLFactory(), SSLSocketHelper.getX509TrustManager())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                }).build();

        return clientBuilder.build();
    }
}
