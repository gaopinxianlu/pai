package cn.sc.pai.test.image;

import android.view.LayoutInflater;

import cn.pai.mvp.view.PaiActivity;
import cn.sc.pai.test.databinding.ActivityImageBinding;


/**
 * activity 图片
 * author：pany
 * on 2017/12/1 11:14
 */
public class ImageActivity extends PaiActivity<ActivityImageBinding , ImageContract.View, ImageContract.Presenter>
        implements ImageContract.View {

    @Override
    protected ActivityImageBinding layoutViewBinding(LayoutInflater inflater) {
        return ActivityImageBinding.inflate(inflater);
    }

    @Override
    protected ImageContract.Presenter bindPresenter() {
        return new ImagePresenter();
    }
}
