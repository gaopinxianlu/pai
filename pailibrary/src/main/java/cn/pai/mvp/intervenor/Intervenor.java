package cn.pai.mvp.intervenor;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 生命周期介入
 * 整合了activity和fragment常用的生命周期
 * 目的是为了能在presenter处理activity或者fragment生命周期发生时的
 * 数据，比如屏幕旋转，电话进入等
 * author：pany
 * on 2017/10/19 10:55
 */
public class Intervenor implements LifecycleObserver {

    /**
     * 注：该介入函数发生在attachBindPresenter之后，layoutAfterViewBind之前执行，view和presenter已经绑定完成
     * 不使用@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)是因为，lifecycle的oncreate,onstart,onresume都会在父类对应的
     * 方法执行完后才会执行，也就是presenter.start执行完后才执行
     * Must be called from {@link android.app.Activity#onCreate(Bundle)}
     *
     */
    public void onCreate() {

    }

    /**
     * 没焦点，不可操作，用户所见
     * Must be called from {@link android.app.Activity#onStart()}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {

    }

    /**
     * 有焦点，用户交互
     * Must be called from {@link android.app.Activity#onResume()}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {

    }

    /**
     * 已暂停，可见但不在前台，不可交互
     * Must be called from {@link android.app.Activity#onPause()}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {

    }

    /**
     * 停止，不可见
     * Must be called from {@link android.app.Activity#onStop()}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {

    }

    /**
     * 被销毁
     * Must be called from {@link android.app.Activity#onDestroy()}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {

    }

    /**
     * Must be called from {@link android.app.Activity#onSaveInstanceState(Bundle)}
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {

    }

    /**
     * Must be called from {@link android.app.Activity#onActivityResult(int, int, Intent)}
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     * Fragment不会调用
     * ust be called from {@link android.app.Activity#finish()}
     */
    public void onFinish(){

    }
}
