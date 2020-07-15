package cn.sc.pai.test.main.fragment;

import android.view.LayoutInflater;

import cn.pai.mvp.presenter.IPresenter;
import cn.pai.mvp.view.IView;
import cn.pai.mvp.view.PaiFragment;
import cn.sc.pai.test.databinding.FragmentMainBinding;

public class MainFragment extends PaiFragment<FragmentMainBinding, MainFragContract.View, MainFragContract.Presenter> {

    @Override
    protected FragmentMainBinding layoutViewBinding(LayoutInflater inflater) {
        return FragmentMainBinding.inflate(inflater);
    }

    @Override
    protected MainFragContract.Presenter bindPresenter() {
        return new MainFragPresenter();
    }

    @Override
    protected void layoutView() {
        vb.tvMsg.setText("我在水里游泳");
    }
}
