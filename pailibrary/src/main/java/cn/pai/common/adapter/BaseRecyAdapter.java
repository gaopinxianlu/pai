package cn.pai.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pany
 * @description RecyclerView适配器
 * @date 2017年3月2日下午9:54:59
 */
public abstract class BaseRecyAdapter<T extends RecyclerView.ViewHolder, D> extends RecyclerView.Adapter<T> implements IAdapter<D> {

    protected List<D> datas;
    private OnAdapterListener<D> onAdapterListener;

    public BaseRecyAdapter(List<D> datas) {
        // TODO Auto-generated constructor stub
        this.datas = datas;
    }

    public BaseRecyAdapter() {
        this.datas = new ArrayList<>(0);
    }

    @Override
    public void clean() {
        if (datas != null && !datas.isEmpty()) {
            datas.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void change(List<D> list) {
        if (datas != null && list != null) {
            datas.clear();
            datas.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void changeOne(int position, D data) {
        if (datas != null && data != null) {
            datas.remove(position);
            datas.add(position, data);
            notifyItemChanged(position);
        }
    }

    @Override
    public void insert(List<D> list) {
        if (datas != null && list != null) {
            int start = datas.size();
            datas.addAll(list);
            //列表从positionStart位置到itemCount数量的列表项批量添加数据时调用，伴有动画效果
            notifyItemRangeInserted(start, list.size());
        }
    }

    @Override
    public void insertOne(D data) {
        if (datas != null && data != null) {
            datas.add(data);
            notifyItemInserted(datas.size() - 1);
        }
    }

    @Override
    public void remove(int positionStart, int itemCount) {
        if (datas != null) {
            for (int i = 0; i < itemCount; i++) {
                datas.remove(positionStart + i);
            }
            notifyItemRangeRemoved(positionStart, itemCount);
        }
    }

    @Override
    public void removeOne(int position) {
        if (datas != null) {
            datas.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public List<D> getDatas() {
        return datas;
    }
    

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return datas != null ? datas.size() : 0;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO Auto-generated method stub
        return onCreateViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(final T holder, final int position) {
        // TODO Auto-generated method stub
        if (onAdapterListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAdapterListener.onItemClick(holder.itemView, position, datas.get(position));
                }
            });
        }
        onBindViewHolder(holder, position,
                datas == null ? null : datas.get(position));
    }

    /**
     * 列表视图的布局id
     *
     * @return
     */
    public abstract int layoutId();

    /**
     * holder视图
     *
     * @param view
     * @return
     */
    public abstract T onCreateViewHolder(View view);

    /**
     * 数据绑定
     *
     * @param holder
     * @param position
     * @param data
     */
    public abstract void onBindViewHolder(T holder, int position, D data);


    /**
     * 添加监听
     *
     * @param onAdapterListener
     */
    public void setOnAdapterListener(OnAdapterListener<D> onAdapterListener) {
        this.onAdapterListener = onAdapterListener;
    }

    /**
     * 点击事件监听
     *
     * @param <D>
     */
    public interface OnAdapterListener<D> {

        void onItemClick(View view, int position, D data);

    }

}
