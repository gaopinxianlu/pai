package cn.pai.connect.http.progress;

/**
 * 进度接口
 * author：pany
 * on 2017/5/5 10:47
 */
public interface ProgressListener {

    void onProgress(long bytes, long contentLength, boolean done);
}
