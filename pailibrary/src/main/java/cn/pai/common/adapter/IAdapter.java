package cn.pai.common.adapter;

import java.util.List;

/**
 * description：adapter接口
 * author：pany
 * on 2018/5/16 09:51
 */
public interface IAdapter<D> {

    /**
     * 清空数据和视图
     */
    void clean();

    /**
     * 改变全部数据集
     *
     * @param datas 新的数据集
     */
    void change(List<D> datas);

    /**
     * 改变某一项数据对象
     *
     * @param position 待改变项的位置
     * @param data     新的数据对象
     */
    void changeOne(int position, D data);

    /**
     * 插入数据集
     *
     * @param datas 新插入的数据集
     */
    void insert(List<D> datas);


    /**
     * 插入数据
     * @param data 新插入的数据对象
     */
    void insertOne(D data);

    /**
     * 删除数据集
     * @param positionStart 开始位置
     * @param itemCount 要删除的个数
     */
    void remove(int positionStart, int itemCount);

    /**
     * 删除某一项
     * @param position 待删除项的位置
     */
    void removeOne(int position);

    /**
     * 获取数据
     *
     * @return
     */
    List<D> getDatas();
}
