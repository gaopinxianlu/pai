package cn.pai.common.adapter;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * description：
 * author：pany
 * on 2018/5/13 10:50
 */
public abstract class CommRecyAdapter<D> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IAdapter<D> {

    protected List<D> datas;
    private CommRecyAdapter.OnAdapterListener<D> onAdapterListener;

    public CommRecyAdapter(List<D> datas) {
        // TODO Auto-generated constructor stub
        this.datas = datas;
    }

    public CommRecyAdapter() {
        // TODO Auto-generated constructor stub
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (onAdapterListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAdapterListener.onItemClick(holder.itemView, position, datas.get(position));
                }
            });
        }
        convert(holder, position,
                datas == null ? null : datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas != null ? datas.size() : 0;
    }

    /**
     * 添加监听
     *
     * @param onAdapterListener
     */
    public void setOnAdapterListener(CommRecyAdapter.OnAdapterListener<D> onAdapterListener) {
        this.onAdapterListener = onAdapterListener;
    }

    /**
     * 列表视图的布局id
     *
     * @return
     */
    public abstract int layoutId();

    /**
     * 数据绑定
     *
     * @param holder
     * @param position
     * @param data
     */
    public abstract void convert(RecyclerView.ViewHolder holder, int position, D data);

    /**
     * 点击事件监听
     *
     * @param <D>
     */
    public interface OnAdapterListener<D> {

        void onItemClick(View view, int position, D data);

    }


    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected SparseArray<View> views;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            // TODO Auto-generated constructor stub
            this.views = new SparseArray<>();
        }

        /**
         * 创建ViewHolder
         *
         * @param layoutId
         * @param parent
         * @return
         */
        public static ViewHolder create(ViewGroup parent, int viewType, int layoutId) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
            return new ViewHolder(itemView, viewType);
        }

        public <T extends View> T get(int id) {
            View view = views.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                views.put(id, view);
            }
            return (T) view;
        }

        public ImageView getImageView(int id) {
            return get(id);
        }

        public TextView getTextView(int id) {
            return get(id);
        }

        public CheckBox getCheckBox(int id) {
            return get(id);
        }

        public Spinner getSpinner(int id) {
            return get(id);
        }

        public EditText getEditText(int id) {
            return get(id);
        }
    }
}
