package cn.pai.connect.http.param;

import android.util.SparseArray;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;

import cn.pai.common.log.Loger;
import cn.pai.connect.http.progress.ProgressListener;
import cn.pai.connect.http.progress.ProgressRequestBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author pany
 * @description mult参数, 可设置上传进度监听, MultipartBody默认是MultipartBody.FORM
 * @date 2017年6月8日下午3:52:51
 */
public class MultSubmit extends BodySubmit {

    // 上传进度监听
    private ProgressListener progressListener;
    // 默认的MediaType
    private MultipartBody.Builder builder = new MultipartBody.Builder()
            .setType(MultipartBody.FORM);

    public MultSubmit(Request.Builder builder) {
        super(builder);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected RequestBody buildBody() {
        // TODO Auto-generated method stub
        RequestBody body = builder.build();
        if (progressListener != null) {
            body = new ProgressRequestBody(body, progressListener);
        }
        return body;
    }

    /**
     * 默认是MultipartBody.FORM
     *
     * @param mediaType
     * @return
     */
    public MultSubmit multType(MediaType mediaType) {
        builder.setType(mediaType);
        return this;
    }

    /**
     * 设置请求参数
     *
     * @param key
     * @param value
     * @return
     */
    public MultSubmit param(String key, String value) {
        Loger.d("请求参数：" + key + "=" + value);
        builder.addFormDataPart(key, value);
        return this;
    }

    /**
     * 设置请求参数
     *
     * @param key
     * @param file
     * @return
     */
    public MultSubmit param(String key, File file) {
        Loger.d("请求参数：" + key + "=" + file);
        RequestBody fileBody = RequestBody.create(
                MediaType.parse(getFileContentType(file)), file);
        builder.addFormDataPart(key, file.getName(), fileBody);
        return this;
    }

    /**
     * 设置请求参数
     *
     * @return
     */
    public MultSubmit param(SparseArray<File> files) {
        for (int i = 0; i < files.size(); i++) {
            param(i + "", files.get(i));
        }
        return this;
    }

    /**
     * 设置请求参数
     *
     * @return
     */
    public MultSubmit param(List<File> files) {
        for (int i = 0; i < files.size(); i++) {
            param(i + "", files.get(i));
        }
        return this;
    }

    /**
     * 请求参数上传进度监听
     *
     * @param progressListener
     */
    public MultSubmit setOnRequestListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
        return this;
    }

    /**
     * @param file
     * @return
     */
    private String getFileContentType(File file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentType = fileNameMap.getContentTypeFor(file.getPath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

}