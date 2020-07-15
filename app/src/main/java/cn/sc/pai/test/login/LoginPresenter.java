package cn.sc.pai.test.login;

import android.os.Bundle;
import android.os.Handler;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pai.common.log.Loger;
import cn.pai.mvp.presenter.PaiPresenter;
import cn.sc.pai.test.main.MainActivity;

/**
 * 登录 presenter
 * author：pany
 * on 2017/12/1 11:27
 */
public class LoginPresenter extends PaiPresenter<LoginContract.View> implements LoginContract.Presenter {

    private LoginModel model = new LoginModel();
    private Handler handler = new Handler();

    @Override
    public void start() {

    }

    /**
     * 登陆
     */
    @Override
    public void reqLogin() {
        String userName = pv.vb().etUserName.getText().toString().trim();
        String userPwd = pv.vb().etUserPwd.getText().toString().trim();
        pv.loading("登录中...");
        handler.postDelayed(() -> {
            pv.loaded();
            Bundle bundle = new Bundle();
            bundle.putString("UserName",userName);
            bundle.putString("UserPwd",userPwd);
            pv.toActivity(MainActivity.class,bundle);
        },1000);


//        TextSubmit submit = new OkRequest().url("https://route.showapi.com/9-5").text().param("");
//        submit.post(new StringRespCall() {
//            @Override
//            public void onResponse(String response) {
//                view.loaded();
//            }
//
//            @Override
//            public void onFailure(String response, String message) {
//                view.loaded();
//            }
//        });
    }

    /**
     * 商户请求流水号
     * 商户请求流水号,32个字符以内、只能包含字母、数字；需保证在商户端不重复
     *
     * @return
     */
    protected String createReqSsn() {
        String reqTime = createReqTime();//请求时间
        String sn = "12345678";//设备序列号
        String reqSsn = "1234567890123456" + reqTime.substring(2) + sn.substring(sn.length() - 3);//请求流水
        return reqSsn;
    }

    /**
     * 商户请求时间
     *
     * @return
     */
    protected String createReqTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate(LifecycleOwner owner) {
        Loger.d("p---------activity--------onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart(LifecycleOwner owner) {
        Loger.d("p----------activity-------onStart");
    }
}
