package cn.pai.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pai.common.log.Loger;
import cn.pai.mvp.presenter.IPresenter;

/**
 * MVP Fragment 基类
 * author：pany
 * on 2017/10/21 21:01
 */
public abstract class MvpFragment<VB extends ViewBinding, V extends IView<VB>, P extends IPresenter<V>>
        extends Fragment implements IView<VB> {

    /**
     * butternif
     */
    private Unbinder unbinder;
    /**
     * presenter
     */
    protected P presenter;

    /**
     * viewBinding
     */
    protected VB vb;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Loger.p("frag-onActivityResult");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Loger.p("frag-onSaveInstanceState");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onSaveInstanceState(outState);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Loger.p("frag-onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loger.p("frag-onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        contentView = inflater.inflate(layoutId(), container, false);
        return (vb = layoutViewBinding(inflater)).getRoot();
    }

    /**
     * 视图资源ID
     */
    protected abstract VB layoutViewBinding(LayoutInflater inflater);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Loger.p("frag-onViewCreated");
        unbinder = ButterKnife.bind(view);// butterknife绑定控件
        attachBindPresenter();// 连接绑定的presenter
    }

    /**
     * 连接视图和绑定的presenter
     */
    private void attachBindPresenter() {
        presenter = bindPresenter();    // 绑定presenter
        if (presenter != null) {
            presenter.attach((V) this);//presenter绑定view和Interveno
        }
    }

    /**
     * 页面绑定presenter
     */
    protected P bindPresenter() {
        return null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Loger.p("frag-onActivityCreated");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onCreate();
        }
        layoutAfterViewBind();               // 视图初始化操作
        if (presenter != null) {
            presenter.start();      //presenter初始化操作
        }
    }

    /**
     * 加载视图
     */
    protected void layoutAfterViewBind() {

    }

    @Override
    public void onStart() {
        super.onStart();
        Loger.p("frag-onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Loger.p("frag-onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Loger.p("frag-onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Loger.p("frag-onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Loger.p("frag-onDestroyView");
        //butterKnife在fragment中使用需要解绑
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Loger.p("frag-onDestroy");
        if (presenter != null) {
            presenter.detach();
            presenter = null;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Loger.p("frag-onDetach");
    }

    /**
     * 介入者不为空
     *
     * @return
     */
    private boolean isIntervenorNotNull() {
        return presenter != null && presenter.getIntervenor() != null;
    }
}
