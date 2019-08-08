package cn.pai.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import cn.pai.common.log.Loger;
import cn.pai.mvp.presenter.IPresenter;

/**
 * MVP Fragment 基类
 * author：pany
 * on 2017/10/21 21:01
 */
public abstract class MvpFragment<V extends IView, P extends IPresenter<V>>
        extends Fragment implements IView {

    /**
     * 当前视图
     */
    private View contentView;

    /**
     * presenter
     */
    protected P presenter;

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
        presenter = bindPresenter();        // 绑定presenter
        if (presenter != null) {
            presenter.attach((V) this);     //presenter绑定view和Interveno
        }
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onCreate(savedInstanceState);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(layoutId(), container, false);
        return contentView;
    }

    /**
     * 视图资源ID
     */
    protected abstract int layoutId();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Loger.p("frag-onViewCreated");
        ButterKnife.bind(this, contentView);// 注解绑定控件
        layoutView();               // 视图初始化操作
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Loger.p("frag-onActivityCreated");
        if (presenter != null) {
            presenter.start();      //presenter初始化操作
        }
    }

    /**
     * 页面绑定presenter
     */
    protected abstract P bindPresenter();

    /**
     * 加载视图
     */
    protected abstract void layoutView();

    @Override
    public void onStart() {
        super.onStart();
        Loger.p("frag-onStart");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Loger.p("frag-onResume");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Loger.p("frag-onPause");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Loger.p("frag-onStop");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onStop();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Loger.p("frag-onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Loger.p("frag-onDestroy");
        if (isIntervenorNotNull()) {
            presenter.getIntervenor().onDestroy();
        }
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

    /**
     * 获取view对象便捷方法
     *
     * @param id 资源id
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <H extends View> H find(int id) {
        return (H) contentView.findViewById(id);
    }
}
