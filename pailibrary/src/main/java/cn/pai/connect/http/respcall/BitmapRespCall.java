package cn.pai.connect.http.respcall;

import okhttp3.Response;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * okhttp响应bitmap结果
 * author：pany on 2017/5/5 09:40
 */
public abstract class BitmapRespCall extends RespCall<Bitmap> {

	@Override
	protected Bitmap convert(Response response) {
		// TODO Auto-generated method stub
		Bitmap bitmap = BitmapFactory
				.decodeStream(response.body().byteStream());
		return bitmap;
	}

}