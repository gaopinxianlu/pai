package cn.sc.pai.test.main;

import android.os.Bundle;

import cn.pai.mvp.view.PaiActivity;
import cn.sc.pai.test.R;

public class MainActivity extends PaiActivity<MainContract.View, MainContract.Presenter>
        implements MainContract.View {

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainContract.Presenter bindPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void layoutView(Bundle savedInstanceState) {

    }
}
