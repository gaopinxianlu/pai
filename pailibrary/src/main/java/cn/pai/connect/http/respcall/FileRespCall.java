package cn.pai.connect.http.respcall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * okhttp响应文件 author：pany on 2017/5/5 09:40
 */
public abstract class FileRespCall extends RespCall<File> {

	private String fileDirs;
	private String fileName;

	public FileRespCall(String fileDirs, String fileName) {
		// TODO Auto-generated constructor stub
		this.fileDirs = fileDirs;
		this.fileName = fileName;
	}

	public abstract void onProgress(long bytes, long contentLength);

	@Override
	protected File convert(Response response) throws IOException {
		// TODO Auto-generated method stub

		File dir = new File(fileDirs);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(fileDirs, fileName);

		InputStream is = null;
		FileOutputStream fos = null;
		try {
			is = response.body().byteStream();
			fos = new FileOutputStream(file);
			
			final long total = response.body().contentLength();// 总长度
			byte[] buf = new byte[2048];//缓冲区
			int len = 0;//缓冲区读取长度
			long sum = 0;//输入流读取长度
			while ((len = is.read(buf)) != -1) {
				sum += len;
				fos.write(buf, 0, len);
				final long progress = sum;
				toUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						onProgress(progress, total);
					}
				});
			}
			fos.flush();
			return file;
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}