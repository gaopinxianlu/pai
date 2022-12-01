package cn.sc.pai.test.image;


import cn.pai.mvp.presenter.IPresenter;
import cn.pai.mvp.view.IView;
import cn.sc.pai.test.databinding.ActivityImageBinding;

/**
 * 登录 契约
 *
 * @author pany
 * @date 2017年3月2日下午10:21:40
 */
public interface ImageContract {
    interface View extends IView<ActivityImageBinding> {

    }

    interface Presenter extends IPresenter<View> {


    }
}
