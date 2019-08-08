package cn.sc.pai.test.login;

import android.os.Handler;
import android.text.TextUtils;

import cn.pai.mvp.presenter.PaiPresenter;
import cn.sc.pai.test.main.MainActivity;

/**
 * 登录 presenter
 * author：pany
 * on 2017/12/1 11:27
 */
public class LoginPresenter extends PaiPresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginModel model = new LoginModel();
    private Handler handler = new Handler();

    @Override
    public void start() {

    }

    /**
     * 登陆
     */
    @Override
    public void reqLogin() {
        view.loading("登录中...");
        handler.postDelayed(() -> {
            view.loaded();
            view.toActivity(MainActivity.class,null);
        },1000);
    }
}
