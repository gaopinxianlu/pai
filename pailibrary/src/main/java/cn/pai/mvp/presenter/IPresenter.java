package cn.pai.mvp.presenter;

import androidx.lifecycle.LifecycleObserver;

import cn.pai.mvp.intervenor.Intervenor;
import cn.pai.mvp.view.IView;

/**
 * @author pany
 * @description mvp presenter基类
 * @date 2017年3月2日下午8:37:20
 */
public interface IPresenter<V extends IView> extends LifecycleObserver {

    /**
     * 绑定View和介入者
     */
    void attach(V view);

    /**
     * 生命周期介入者
     */
    Intervenor getIntervenor();

    /**
     * presenter启动时处理一些事情
     */
    void start();

    /**
     * 解绑view
     */
    void detach();

}
