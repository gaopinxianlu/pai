package cn.pai.mvp.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;

import cn.pai.common.listener.OnPromptListener;
import cn.pai.mvp.presenter.IPresenter;

/**
 * @author pany
 * @description fragment基类
 * @date 2017年3月2日下午9:59:00
 */
public abstract class PaiFragment<V extends IView, P extends IPresenter<V>>
        extends MvpFragment<V, P> {

    /**
     * 承载fragment的activity
     */
    protected IView activityView;

    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return getContext().registerReceiver(receiver, filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        getContext().sendBroadcast(intent);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return getContext().bindService(service,conn,flags);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        getContext().unbindService(conn);
    }

    @Override
    public void onAttach(Context context) {
        // TODO Auto-generated method stub
        super.onAttach(context);
        if (context instanceof IView) {
            activityView = (IView) context;
        }
    }

    @Override
    public void onDetach() {
        // TODO Auto-generated method stub
        super.onDetach();
        activityView = null;
    }

    @Override
    public void loading() {
        // TODO Auto-generated method stub
        if (isActive()) {
            activityView.loading();
        }
    }

    @Override
    public void loading(String msg, DialogInterface.OnKeyListener onKeyListener) {
        // TODO Auto-generated method stub
        if (isActive()) {
            activityView.loading(msg, onKeyListener);
        }
    }

    @Override
    public void loading(String msg) {
        // TODO Auto-generated method stub
        if (isActive()) {
            activityView.loading(msg);
        }
    }

    @Override
    public void loaded() {
        // TODO Auto-generated method stub
        if (isActive()) {
            activityView.loaded();
        }
    }

    @Override
    public void prompt(boolean cancelable, String message, OnPromptListener onPromptListener) {
        if (isActive()) {
            activityView.prompt(cancelable, message, onPromptListener);
        }
    }

    @Override
    public void prompt(String message) {
        // TODO Auto-generated method stub
        if (isActive()) {
            activityView.prompt(message);
        }
    }

    @Override
    public void prompted() {
        // TODO Auto-generated method stub
        if (isActive()) {
            activityView.prompted();
        }
    }

    @Override
    public void toast(String message) {
        // TODO Auto-generated method stub
        if (isActive()) {
            activityView.toast(message);
        }
    }

    @Override
    public void snack(String message) {
        if (isActive()) {
            activityView.snack(message);
        }
    }

    @Override
    public void doNetworkError(String message) {
        // TODO Auto-generated method stub
        if (isActive()) {
            activityView.doNetworkError(message);
        }
    }

    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        return activityView != null && activityView.isActive();
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        if (isActive()) {
            activityView.finish();
        }
    }

    @Override
    public void quit() {
        finish();
    }

    @Override
    public void toActivityForResult(Class cls, Bundle bundle, int reqCode) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, reqCode);
    }

    @Override
    public void toActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void setResult(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null)
            intent.putExtras(bundle);
        getActivity().setResult(resultCode, intent);
    }
}
