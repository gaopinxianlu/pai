package cn.pai.mvp.intervenor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * 生命周期介入
 * 整合了activity和fragment常用的生命周期
 * 目的是为了能在presenter处理activity或者fragment生命周期发生时的
 * 数据，比如屏幕旋转，电话进入等
 * 官方将@OnLifecycleEvent(Lifecycle.Event.ON_CREATE)注解废弃掉，推荐使用DefaultLifecycleObserver
 * author：pany
 * on 2017/10/19 10:55
 */
public class Intervenor {

    private DefaultLifecycleObserver lifecycleObserver = new DefaultLifecycleObserver() {
        //观察者的oncreate方法是在oncreate所有方法执行完最后再执行的
        @Override
        public void onCreate(@NonNull LifecycleOwner owner) {
            //因为业务逻辑需要所以不在此执行oncreate
        }

        @Override
        public void onStart(@NonNull LifecycleOwner owner) {
            Intervenor.this.onStart();
        }

        @Override
        public void onResume(@NonNull LifecycleOwner owner) {
            Intervenor.this.onResume();
        }

        @Override
        public void onPause(@NonNull LifecycleOwner owner) {
            Intervenor.this.onPause();
        }

        @Override
        public void onStop(@NonNull LifecycleOwner owner) {
            Intervenor.this.onStop();
        }

        @Override
        public void onDestroy(@NonNull LifecycleOwner owner) {
            Intervenor.this.onDestroy();
        }
    };

    public DefaultLifecycleObserver getLifecycleObserver() {
        return lifecycleObserver;
    }

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
    public void onStart() {

    }

    /**
     * 有焦点，用户交互
     * Must be called from {@link android.app.Activity#onResume()}
     */
    public void onResume() {

    }

    /**
     * 已暂停，可见但不在前台，不可交互
     * Must be called from {@link android.app.Activity#onPause()}
     */
    public void onPause() {

    }

    /**
     * 停止，不可见
     * Must be called from {@link android.app.Activity#onStop()}
     */
    public void onStop() {

    }

    /**
     * 被销毁
     * Must be called from {@link android.app.Activity#onDestroy()}
     */
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
