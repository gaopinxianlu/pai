package cn.pai.connect.http.respcall;

import java.io.IOException;

import cn.pai.common.log.Loger;
import cn.pai.connect.http.OkHttp;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @param <T>
 * @author pany
 * @description 响应回调
 * @date 2017年6月12日下午2:33:51
 */
public abstract class RespCall<T> implements Callback {

    @Override
    public void onResponse(Call call, Response response) {
        Loger.d("响应编码:" + response.code());
        if (response.isSuccessful()) {
            try {
                final T result = convert(response);
                Loger.d("响应结果:" + result);
                Loger.d("---------------okhttp end---------------------");
                if (result != null) {
                    toUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            deliverResponse(result);
                        }
                    });
                } else {
                    deliverFailure(null, "response is null");
                }
            } catch (Exception e) {
                // TODO: handle exception
                deliverFailure(null, e.getMessage());
            } finally {
                response.body().close();// 转换完成后关闭
            }
        } else {
            Loger.d("---------------okhttp end---------------------");
            response.body().close();// 关闭
            deliverFailure(null, "网络异常(" + response.code() + ")," + response.message());
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Loger.d("请求失败:" + e.getMessage());
        Loger.d("---------------okhttp end---------------------");
        deliverFailure(null, "网络异常(" + e.getMessage() + ")");
    }

    /**
     * 分发服务器响应 子类可重写该方法,处理响应是走onResponse还是onFailure
     *
     * @param result
     */
    protected void deliverResponse(T result) {
        onResponse(result);
    }

    /**
     * 返回主线程
     *
     * @param message
     */
    private void deliverFailure(final T response, final String message) {
        toUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                onFailure(response, message);
            }
        });
    }

    /**
     * 返回主线程
     */
    protected void toUiThread(Runnable runnable) {
        OkHttp.getInstance().toUiThread(runnable);
    }

    /**
     * 转换响应结果,将Response对象转换成需要的对象
     *
     * @param response okhttp响应的对象
     * @return
     * @throws IOException
     */
    protected abstract T convert(Response response) throws Exception;

    /**
     * 服务器响应成功
     *
     * @param response
     */
    public abstract void onResponse(T response);

    /**
     * 服务器响应失败
     *
     * @param message
     */
    public abstract void onFailure(T response, String message);
}
