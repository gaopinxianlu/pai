package cn.sc.pai.test.main.fragment;


import cn.pai.mvp.presenter.IPresenter;
import cn.pai.mvp.view.IView;
import cn.sc.pai.test.databinding.ActivityMainBinding;
import cn.sc.pai.test.databinding.FragmentMainBinding;

/**
 * 登录 契约
 *
 * @author pany
 * @date 2017年3月2日下午10:21:40
 */
public interface MainFragContract {
    interface View extends IView<FragmentMainBinding> {

    }

    interface Presenter extends IPresenter<View> {


    }
}
