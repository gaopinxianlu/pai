package cn.pai.mvp.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;

import cn.pai.common.listener.OnPromptListener;

/**
 * @description mvp 的 View基类,视图的基本操作
 * @author pany
 * @date 2017年6月15日上午11:06:01
 */
public interface IView {

	/**
	 * 获取当前Activity
	 * @return
	 */
	Activity getActivity();
	
	/**
	 * 获取上下文
	 * 因为有些model会用到
	 */
	Context getContext();

	/**
	 * 获取bundle
	 * @return
	 */
	Bundle getBundle();

	/**
	 * 注册广播
	 * @param receiver
	 * @param filter
	 * @return
	 */
	Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter);

	/**
	 * 发送广播
	 * @param intent
	 */
	void sendBroadcast(Intent intent);

    /**
     * 绑定服务
     *
     * @param service
     * @param conn
     * @param flags
     * @return
     */
    boolean bindService(Intent service, ServiceConnection conn, int flags);

    /**
     * 接触绑定服务
     *
     * @param conn
     */
    void unbindService(ServiceConnection conn);

    /**
     * 加载中
     */
    void loading();

	/**
	 * 加载中
	 */
	void loading(String msg, DialogInterface.OnKeyListener onKeyListener);


	/**
	 * 加载中
	 */
	void loading(String msg);

	/**
	 * 加载完成
	 */
	void loaded();

	/**
	 * 弹出框信息提示
	 * 普通信息提示
	 * @param cancelable
	 * @param message
	 * @param onPromptListener 提示事件
	 */
	void prompt(boolean cancelable,String message, final OnPromptListener onPromptListener) ;

	/**
	 * 弹出框信息提示
	 * 普通信息提示
	 * 
	 * @param message
	 */
	void prompt(String message);

	/**
	 * 关闭弹出框
	 */
	void prompted();

	/**
	 * 吐司信息提示
	 * 
	 * @param message
	 */
	void toast(String message);

	/**
	 * snackbar提示
	 * @param message
	 */
	void snack(String message);

	/**
	 * 通信异常处理
	 * 
	 * @param message
	 */
	void doNetworkError(String message);
	
	/**
	 * activity 是否活动
	 * 
	 * @return
	 */
	boolean isActive();
	
	/**
	 * 结束
	 */
	void finish();

	/**
	 * 退出应用
	 */
	void quit();

	/**
	 * 启动需要返回的Activity
	 *
	 * @param cls
	 * @param bundle 可以为空
	 * @param reqCode
	 */
	void toActivityForResult(Class cls, Bundle bundle, int reqCode);

	/**
	 * 启动Activity
	 *
	 * @param cls
	 * @param bundle 可以为空
	 */
	void toActivity(Class cls, Bundle bundle);

	/**
	 * 返回结果
	 * @param resultCode
	 * @param bundle 可以为空
	 */
	void setResult(int resultCode,Bundle bundle);
}
