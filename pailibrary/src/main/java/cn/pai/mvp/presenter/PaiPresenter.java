package cn.pai.mvp.presenter;

import cn.pai.mvp.intervenor.Intervenor;
import cn.pai.mvp.view.IView;

/**
 * Presenter 基类
 * author：pany
 * on 2017/10/19 11:42
 */
public abstract class PaiPresenter<V extends IView> implements IPresenter<V> {

    /**
     * 视图
     */
    protected V view = null;

    /**
     * activity,fragment生命周期介入者
     */
    private Intervenor intervenor = null;

    /**
     * 绑定
     * @param view
     */
    @Override
    public void attach(V view) {
        this.view = view;
        this.intervenor = createIntervenor();
    }

    /**
     * 子类需要可重写该方法
     * 介入activity或者fragment的生命周期
     *
     * @return
     */
    protected Intervenor createIntervenor() {
        return null;
    }

    /**
     * 获取生命周期介入者
     *
     * @return
     */
    @Override
    public Intervenor getIntervenor() {
        return intervenor;
    }

    /**
     * 解除
     */
    @Override
    public void detach() {
        this.view = null;
        this.intervenor = null;
    }
}
