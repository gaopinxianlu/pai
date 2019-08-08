package cn.pai.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import cn.pai.common.log.Loger;
import cn.pai.mvp.presenter.IPresenter;

/**
 * Mvp Activity 基类
 * author：pany
 * on 2017/10/21 18:45
 */
public abstract class MvpActivity<V extends IView, P extends IPresenter<V>>
        extends AppCompatActivity implements IView {

    /**
     * presenter
     */
    protected P presenter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Loger.i("act-onActivityResult");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Loger.i("act-onSaveInstanceState");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loger.i("act-onCreate");
        layoutPre(savedInstanceState);  // 加载视图前
        setContentView(layoutId());     // 设置视图
        ButterKnife.bind(this); // 注解绑定控件
        layoutView(savedInstanceState); // 视图初始化操作
        if (presenter != null) {
            presenter.start();          //presenter初始化操作
        }
    }

    /**
     * 加载视图前的操作 setContentView之前的操作
     */
    protected void layoutPre(Bundle savedInstanceState) {
        presenter = bindPresenter();    // 绑定presenter
        if (presenter != null) {
            presenter.attach((V) this);//presenter绑定view和Interveno
        }
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onCreate(savedInstanceState);
        }
    }

    /**
     * 视图资源ID
     */
    protected abstract int layoutId();

    /**
     * 页面绑定presenter
     */
    protected abstract P bindPresenter();

    /**
     * 加载视图，一些初始化
     */
    protected abstract void layoutView(Bundle savedInstanceState);

    @Override
    protected void onStart() {
        super.onStart();
        Loger.i("act-onStart");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onStart();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Loger.i("act-onPostCreate");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Loger.i("act-onResume");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onResume();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Loger.i("act-onPause");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Loger.i("act-onStop");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onStop();
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Loger.i("act-onDestroy");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onDestroy();
        }
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onFinish();
        }
    }

    /**
     * 介入者不为空
     *
     * @return
     */
    private boolean isIntervenorNotNull() {
        return presenter != null && presenter.getIntervenor() != null;
    }

    /**
     * 获取view对象便捷方法
     *
     * @param id 视图资源id
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <H extends View> H find(int id) {
        return (H) findViewById(id);
    }
}
