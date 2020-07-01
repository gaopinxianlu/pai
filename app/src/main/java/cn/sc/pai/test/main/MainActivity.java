package cn.sc.pai.test.main;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentTransaction;

import cn.pai.mvp.view.PaiActivity;
import cn.sc.pai.test.R;
import cn.sc.pai.test.databinding.ActivityMainBinding;
import cn.sc.pai.test.main.fragment.MainFragment;

public class MainActivity extends PaiActivity<ActivityMainBinding, MainContract.View, MainContract.Presenter>
        implements MainContract.View {

    @Override
    protected ActivityMainBinding layoutViewBinding(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(inflater);
    }

    @Override
    protected MainContract.Presenter bindPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void layoutView(Bundle savedInstanceState) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frag,new MainFragment());
        ft.commit();
    }
}
