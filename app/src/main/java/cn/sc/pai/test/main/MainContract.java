package cn.sc.pai.test.main;


import cn.pai.mvp.presenter.IPresenter;
import cn.pai.mvp.view.IView;
import cn.sc.pai.test.databinding.ActivityMainBinding;

/**
 * 登录 契约
 *
 * @author pany
 * @date 2017年3月2日下午10:21:40
 */
public interface MainContract {
    interface View extends IView<ActivityMainBinding> {

    }

    interface Presenter extends IPresenter<View> {


    }
}
