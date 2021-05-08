package cn.pai.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import butterknife.ButterKnife;
import cn.pai.common.log.Loger;
import cn.pai.mvp.presenter.IPresenter;

/**
 * Mvp Activity 基类
 * author：pany
 * on 2017/10/21 18:45
 */
public abstract class MvpActivity<VB extends ViewBinding, V extends IView<VB>, P extends IPresenter<V>>
        extends AppCompatActivity implements IView<VB> {

    /**
     * presenter
     */
    protected P p;

    /**
     * viewBinding
     */
    protected VB vb;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Loger.i("act-onActivityResult");
        if (isIntervenorNotNull()) {
            p.getIntervenor().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Loger.i("act-onSaveInstanceState");
        if (isIntervenorNotNull()) {
            p.getIntervenor().onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loger.i("act-onCreate");
        layoutBeforeViewBind(savedInstanceState);  // 绑定视图前（某些样式设置需要放在setContentView之前）
        setContentView((vb = layoutViewBinding(LayoutInflater.from(this))).getRoot());  // 设置视图
        ButterKnife.bind(this);  // butterknife绑定控件
        attachBindPresenter();          //连接绑定的presenter
        if (isIntervenorNotNull()) {
            p.getIntervenor().onCreate();
        }
        layoutAfterViewBind();          // 视图初始化操作
        if (p != null) {
            p.start();          //presenter初始化操作
        }
    }

    /**
     * 绑定视图前的操作
     * 在setContentView和butterKnife.bind之前执行
     */
    protected void layoutBeforeViewBind(Bundle savedInstanceState) {

    }

    /**
     * 连接视图和绑定的presenter
     */
    private void attachBindPresenter() {
        p = bindPresenter();    // 绑定presenter
        if (p != null) {
            p.attach((V) this);//presenter绑定view和Interveno
        }
    }

    /**
     * 页面绑定presenter
     */
    protected P bindPresenter() {
        return null;
    }

    /**
     * 视图资源ID
     */
    protected abstract VB layoutViewBinding(LayoutInflater inflater);

    /**
     * 加载视图，一些初始化
     */
    protected void layoutAfterViewBind(){

    }

    @Override
    protected void onStart() {
        super.onStart();
        Loger.i("act-onStart");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Loger.i("act-onPostCreate");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Loger.i("act-onResume");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Loger.i("act-onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Loger.i("act-onStop");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Loger.i("act-onDestroy");
        if (p != null) {
            p.detach();
            p = null;
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (isIntervenorNotNull()) {
            p.getIntervenor().onFinish();
        }
    }

    /**
     * 介入者不为空
     *
     * @return
     */
    private boolean isIntervenorNotNull() {
        return p != null && p.getIntervenor() != null;
    }
}
