package cn.sc.pai.test.app;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import cn.pai.connect.http.DefaultOkHttpFactory;
import cn.pai.connect.http.OkHttp;
import cn.pai.connect.http.ssl.NoValidSSLOkHttpFactory;
import cn.pai.connect.http.ssl.SingleSSLOkHttpFactory;
import cn.pai.mvp.app.PaiApp;

public class ThisApp extends PaiApp {

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttp.initialize(new NoValidSSLOkHttpFactory(this));
    }
}
