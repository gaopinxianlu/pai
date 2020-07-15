package cn.sc.pai.test.main.fragment;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import cn.pai.common.log.Loger;
import cn.pai.mvp.presenter.PaiPresenter;

public class MainFragPresenter extends PaiPresenter<MainFragContract.View> implements MainFragContract.Presenter{

    @Override
    public void start() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner){
        Loger.d("p---------frag--------onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart(LifecycleOwner owner) {
        Loger.d("p----------frag-------onStart");
    }
}
