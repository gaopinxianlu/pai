package cn.pai.mvp.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;

import cn.pai.common.listener.OnPromptListener;
import cn.pai.mvp.presenter.IPresenter;

/**
 * @author pany
 * @description activity基类
 * @date 2017年6月30日下午2:59:23
 */
public abstract class PaiActivity<V extends IView, P extends IPresenter<V>>
        extends MvpActivity<V, P> {

    /**
     * 消息提示
     */
    protected AlertDialog promptDialog;
    /**
     * 加载框
     */
    private ProgressDialog loadDialog;

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    /**
     * 重写该方法需要 同时重写loaded方法
     */
    @Override
    public void loading() {
        // TODO Auto-generated method stub
        loading("加载中...");
    }

    @Override
    public void loading(String msg,DialogInterface.OnKeyListener onKeyListener) {
        // TODO Auto-generated method stub
        if (isActive()) {
            if (loadDialog == null) {
                loadDialog = new ProgressDialog(this);
                loadDialog.setCancelable(false);
            }
            loadDialog.setMessage(msg);
            loadDialog.show();
            loadDialog.setOnKeyListener(onKeyListener);
        }
    }

    @Override
    public void loading(String msg) {
        loading(msg,null);
    }

    @Override
    public void loaded() {
        // TODO Auto-generated method stub
        if (isActive() && loadDialog != null) {
            loadDialog.dismiss();
        }
    }

    @Override
    public void prompt(boolean cancelable, String message, final OnPromptListener onPromptListener) {
        if (isActive()) {
            AlertDialog.Builder onceBuilder = new AlertDialog.Builder(this).setTitle("提示").setMessage(message);
            onceBuilder.setCancelable(cancelable);
            if (!TextUtils.isEmpty(onPromptListener.positive)) {
                onceBuilder.setPositiveButton(onPromptListener.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onPromptListener.onPositive(dialog, which);
                    }
                });
            }
            if (!TextUtils.isEmpty(onPromptListener.negative)) {
                onceBuilder.setNegativeButton(onPromptListener.negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onPromptListener.onNegative(dialog, which);
                    }
                });
            }
            onceBuilder.create().show();
        }
    }

    /**
     * 重写该方法需要 同时重写prompted方法
     */
    @Override
    public void prompt(String message) {
        // TODO Auto-generated method stub
        if (isActive()) {
            if (promptDialog == null) {
                promptDialog = new AlertDialog.Builder(this).setTitle("提示")
                        .setPositiveButton("确定", null).create();
            }
            promptDialog.setMessage(message);
            promptDialog.show();
        }
    }

    @Override
    public void prompted() {
        // TODO Auto-generated method stub
        if (isActive() && promptDialog != null)
            promptDialog.dismiss();
    }

    @Override
    public void toast(String message) {
        // TODO Auto-generated method stub
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void snack(String message) {
        Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void doNetworkError(String message) {// 网络异常处理
        // TODO Auto-generated method stub
        loaded();
        prompt(message);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            return !(isDestroyed() || isFinishing());
        }
        return !isFinishing();
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        loaded();
        prompted();
    }

    @Override
    public void quit() {
        finish();
    }

    @Override
    public void toActivityForResult(Class cls, Bundle bundle, int reqCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, reqCode);
    }

    @Override
    public void toActivity(Class cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void setResult(int resultCode, Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null)
            intent.putExtras(bundle);
        setResult(resultCode, intent);
    }
}
