package cn.sc.pai.test.image;

import android.graphics.Bitmap;

import java.io.IOException;
import java.io.InputStream;

import cn.pai.common.utils.BitmapUtil;
import cn.pai.mvp.presenter.PaiPresenter;

/**
 * 登录 presenter
 * author：pany
 * on 2017/12/1 11:27
 */
public class ImagePresenter extends PaiPresenter<ImageContract.View> implements ImageContract.Presenter {

    @Override
    public void start() {
        InputStream is = null;
        try {
            is = pv.getActivity().getResources().getAssets().open("test.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapUtil.compressSampleSize(is,100,100);
//        bitmap = BitmapUtil.compressMatrix(bitmap,0.5f,0.5f);
//        Bitmap bitmap = BitmapUtil.compressSampleSize(is, 10, false);
        pv.vb().ivImg.setImageBitmap(bitmap);
    }

}
