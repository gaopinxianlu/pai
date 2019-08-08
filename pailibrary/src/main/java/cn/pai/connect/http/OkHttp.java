package cn.pai.connect.http;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * @author pany
 * @description
 * @date 2017年6月9日下午2:50:26
 */
public class OkHttp {

    /**
     * client
     */
    private OkHttpClient client;

    /**
     * handler
     */
    private final Handler mDelivery;

    private static class OkHttpHolder {
        private static final OkHttp holder = new OkHttp();
    }

    public static OkHttp getInstance() {
        return OkHttpHolder.holder;
    }

    private OkHttp() {
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static void initialize(OkHttpFactory factory) {
        OkHttp.getInstance().initClient(factory.create());
    }

    /**
     * 初始化一个OkhttpClient对象
     *
     * @param client
     */
    private void initClient(OkHttpClient client) {
        this.client = client;
    }

    /**
     * handler
     *
     * @return
     */
    public void toUiThread(Runnable r) {
        mDelivery.post(r);
    }

    /**
     * 异步提交
     *
     * @param call
     */
    public void enqueue(Request request, Callback call) {
        client.newCall(request).enqueue(call);
    }

    /**
     * 同步提交
     *
     * @throws IOException
     */
    public void execute(Request request) throws IOException {
        client.newCall(request).execute();
    }

    /**
     * 取消全部请求
     */
    public void cancel() {
        if (client != null) {
            client.dispatcher().cancelAll();
        }
    }

    /**
     * 取消某个请求
     *
     * @param tag
     */
    public void cancel(Object tag) {
        for (Call call : client.dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
        for (Call call : client.dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag))
                call.cancel();
        }
    }
}
