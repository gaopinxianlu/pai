package cn.sc.pai.test.main;

import cn.pai.mvp.presenter.PaiPresenter;

/**
 * 登录 presenter
 * author：pany
 * on 2017/12/1 11:27
 */
public class MainPresenter extends PaiPresenter<MainContract.View> implements MainContract.Presenter {

    private MainModel model = new MainModel();

    @Override
    public void start() {

    }


}
