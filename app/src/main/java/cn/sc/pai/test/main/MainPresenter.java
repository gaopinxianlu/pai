package cn.sc.pai.test.main;

import android.os.Bundle;

import cn.pai.mvp.intervenor.Intervenor;
import cn.pai.mvp.presenter.PaiPresenter;

/**
 * 登录 presenter
 * author：pany
 * on 2017/12/1 11:27
 */
public class MainPresenter extends PaiPresenter<MainContract.View> implements MainContract.Presenter {

    private MainModel model = new MainModel();
    String userName;
    String userPwd;

    @Override
    protected Intervenor createIntervenor() {
        return new Intervenor(){
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                Bundle bundle = view.getBundle();
                userName = bundle.getString("UserName");
                userPwd = bundle.getString("UserPwd");
            }
        };
    }

    @Override
    public void start() {

        view.prompt("用户名："+userName+"\n密码："+userPwd);
    }


}
