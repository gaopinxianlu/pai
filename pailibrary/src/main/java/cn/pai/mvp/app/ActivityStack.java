package cn.pai.mvp.app;

import android.app.Activity;

import java.util.Stack;

/**
 * @author pany
 * @description activity栈控制器
 * @date 2017年4月7日上午11:26:06
 */
public class ActivityStack {

    private static class ActivityStackHolder {
        private static ActivityStack INSTATNCE = new ActivityStack();
    }

    public static ActivityStack getInstance() {
        return ActivityStackHolder.INSTATNCE;
    }

    private final Stack<Activity> activityStack = new Stack();

    private ActivityStack() {

    }

    /**
     * 栈内activity数量
     * @return
     */
    public int size() {
        return activityStack.size();
    }

    /**
     * 栈是否为空
     * @return
     */
    public boolean empty() {
        return activityStack.empty();
    }

    /******************Stack类的方法***********************/
    /**
     * 压入Activity到堆栈
     */
    public Activity push(Activity activity) {
        return activityStack.push(activity);
    }

    /**
     * 弹出栈顶activity
     */
    public Activity pop() {
        return activityStack.pop();
    }

    /**
     * 返回栈顶的activity
     *
     * @return
     */
    public Activity peek() {
        return activityStack.peek();
    }
    /******************Stack类的方法***********************/


    /******************Stack父类的方法***********************/

    /**
     * 添加Activity到堆栈
     */
    public boolean add(Activity activity) {
        return activityStack.add(activity);
    }

    /**
     * 移除指定activity
     */
    public boolean remove(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            return activityStack.remove(activity);
        }
        return false;
    }

    /**
     * 获取任意activity
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 结束指定的Activity
     */
    private void finishActivity(Activity activity) {
        if (activity != null) {
            remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        finishActivity(getActivity(cls));
    }

    /**
     * 结束除第一个入栈的activity
     */
    public void finishAbove() {
        for (int i = 1, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.lastElement();
            if (null != activity) {
                activity.finish();
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            Activity activity = activityStack.lastElement();
            if (null != activity) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void exit() {
        try {
            finishAllActivity();
//            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}