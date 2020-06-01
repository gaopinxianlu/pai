package cn.pai.connect.http.ssl;

import android.content.Context;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.pai.connect.http.OkHttpFactory;
import cn.pai.connect.http.cookie.CookieJarImpl;
import cn.pai.connect.http.cookie.CookieMemory;
import okhttp3.OkHttpClient;

/**
 * @author pany
 * @description SSL单向认证OkHttpClient创建
 * @date 2017年6月9日下午2:50:26
 */
public abstract class SingleSSLOkHttpFactory extends OkHttpFactory {


    public SingleSSLOkHttpFactory(Context context) {
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
                .sslSocketFactory(SSLSocketHelper.getSingleSSLFactory(getCertificateStream(context)))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        //这里必须验证主机名是否与服务器的身份验证方案匹配，否则会报异常
                        return true;
                    }
                }).build();
        return clientBuilder.build();
    }

    protected abstract InputStream[] getCertificateStream(Context context);
}
