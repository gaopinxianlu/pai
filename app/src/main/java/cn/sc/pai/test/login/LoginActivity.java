package cn.sc.pai.test.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.OnClick;
import cn.pai.common.log.Loger;
import cn.pai.mvp.view.PaiActivity;
import cn.sc.pai.test.R;
import cn.sc.pai.test.databinding.ActivityLoginBinding;


/**
 * activity 登录
 * author：pany
 * on 2017/12/1 11:14
 */
public class LoginActivity extends PaiActivity<ActivityLoginBinding ,LoginContract.View, LoginContract.Presenter>
        implements LoginContract.View {

    @Override
    protected ActivityLoginBinding layoutViewBinding(LayoutInflater inflater) {
        return ActivityLoginBinding.inflate(inflater);
    }

    @Override
    protected LoginContract.Presenter bindPresenter() {
        return new LoginPresenter();
    }


    @OnClick(R.id.btnLogin)
    void onClick(View v) {
        presenter.reqLogin();
    }
}
