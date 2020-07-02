package cn.sc.pai.test.login;


import cn.pai.mvp.presenter.IPresenter;
import cn.pai.mvp.view.IView;
import cn.sc.pai.test.databinding.ActivityLoginBinding;

/**
 * 登录 契约
 *
 * @author pany
 * @date 2017年3月2日下午10:21:40
 */
public interface LoginContract {
    interface View extends IView<ActivityLoginBinding> {

    }

    interface Presenter extends IPresenter<View> {

        void reqLogin();

    }
}
