package cn.sc.pai.test.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import cn.pai.mvp.presenter.IPresenter;
import cn.pai.mvp.view.IView;
import cn.pai.mvp.view.PaiActivity;
import cn.pai.mvp.view.PaiFragment;
import cn.sc.pai.test.databinding.FragmentMainBinding;

public class MainFragment extends PaiFragment<FragmentMainBinding, IView, IPresenter<IView>> {


    @Override
    protected FragmentMainBinding layoutViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainBinding.inflate(inflater);
    }

    @Override
    protected void layoutView() {
        vBind.tvMsg.setText("我在水里游泳");
    }
}
