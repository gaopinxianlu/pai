package cn.pai.connect.http.progress;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * okhttp带进度的响应体
 * author：pany
 * on 2017/5/5 09:40
 */
public class ProgressResponseBody extends ResponseBody {

	// 实际的待包装响应
	private final ResponseBody responseBody;
	// 进度回调接口
	private final ProgressListener progressListener;
	// 包装完成的BufferedSource
	private BufferedSource bufferedSource;

	/**
	 *
	 * @param responseBody
	 *            待包装的响应
	 * @param progressListener
	 *            回调接口
	 */
	public ProgressResponseBody(ResponseBody responseBody,
								ProgressListener progressListener) {
		this.responseBody = responseBody;
		this.progressListener = progressListener;
	}
	/**
	 * 重写调用实际的响应体的contentType
	 * @return MediaType
	 */
	@Override
	public MediaType contentType() {
		// TODO Auto-generated method stub
		return responseBody.contentType();
	}
	/**
	 * 重写调用实际的响应体的contentLength
	 * @return contentLength
	 * @throws IOException 异常
	 */
	@Override
	public long contentLength() {
		// TODO Auto-generated method stub
		return responseBody.contentLength();
	}
	/**
	 * 重写进行包装source
	 * @return BufferedSource
	 * @throws IOException 异常
	 */
	@Override
	public BufferedSource source() {
		// TODO Auto-generated method stub
		if (bufferedSource == null) {
			// 包装
			bufferedSource = Okio.buffer(source(responseBody.source()));
		}
		return bufferedSource;
	}

	/**
	 * 读取，回调进度接口
	 * @param source Source
	 * @return Source
	 */
	private Source source(Source source) {

		return new ForwardingSource(source) {
			// 当前读取字节
			long totalBytes = 0L;
			@Override
			public long read(Buffer sink, long byteCount) throws IOException {
				long bytesRead = super.read(sink, byteCount);
				//增加当前读取的字节数，如果读取完成了bytesRead会返回-1
				totalBytes += bytesRead != -1 ? bytesRead : 0;
				//回调，如果contentLength()不知道长度，会返回-1
				progressListener.onProgress(totalBytes,
						responseBody.contentLength(), bytesRead == -1);
				return bytesRead;
			}
		};
	}

}
