package cn.pai.mvp.contracts;


import cn.pai.mvp.model.IModel;
import cn.pai.mvp.presenter.IPresenter;
import cn.pai.mvp.view.IView;

/**
 * 
 * @description 契约类
 * @author pany
 * @date 2017年3月2日下午10:21:40
 */
public interface IContract {
	interface View extends IView {

	}

	interface Presenter extends IPresenter {

	}

	interface Model extends IModel {

	}
}
