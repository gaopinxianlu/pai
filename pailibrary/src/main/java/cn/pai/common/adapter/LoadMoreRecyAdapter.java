package cn.pai.common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 加载更多适配器
 *
 * @author pany
 * @description RecyclerView适配器
 * @date 2017年3月2日下午9:54:59
 */
public abstract class LoadMoreRecyAdapter<T extends RecyclerView.ViewHolder, D> extends
        RecyclerView.Adapter<T> implements IAdapter<D>, View.OnClickListener {

    protected List<D> datas;
    private OnAdapterListener<D> onAdapterListener;

    private static final int TYPE_CONTENT = 1;//正常视图
    private static final int TYPE_FOOT = 2;//加载更多视图

    public static final int STATE_LOADING = 0;//正在加载
    public static final int STATE_MORE = 1;//加载更多
    public static final int STATE_NOMORE = 2;//没有更多
    public static final int STATE_FAIL = 3;//加载时出错误
    public static final int STATE_HIDE = 4;//隐藏加载更多

    private RecyclerView recyclerView;
    private FootView footView;//底部视图

    private int state;//加载状态
    private boolean isLoadMore = false;//是否加载更多

    public LoadMoreRecyAdapter(List<D> datas) {
        // TODO Auto-generated constructor stub
        this.datas = datas;
    }

    public LoadMoreRecyAdapter() {
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
    public int getItemCount() {
        // TODO Auto-generated method stub
        int count = (datas != null ? datas.size() : 0);
        return isLoadMore ? count + 1 : count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isFootView(position)) {
            return TYPE_FOOT;
        }
        return TYPE_CONTENT;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        // TODO Auto-generated method stub
        if (viewType == TYPE_FOOT) {
            if (footView == null) {
                footView = new FootView(parent.getContext());
                setLoadState(state);
                footView.setOnClickListener(this);
            }
            return onCreateViewHolder(footView);
        }
        return onCreateViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutId(), parent, false));
    }

    @Override
    public void onBindViewHolder(final T holder, final int position) {
        // TODO Auto-generated method stub
        if (isFootView(position)) {
            return;
        }
        if (onAdapterListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (state != STATE_LOADING) {
                        onAdapterListener.onItemClick(holder.itemView, position, datas.get(position));
                    }
                }
            });
        }
        onBindViewHolder(holder, position, datas.get(position));
    }

    /**
     * StaggeredGridLayoutManager模式时，HeaderView、FooterView可占据一行
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(T holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isFootView(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * GridLayoutManager模式时， HeaderView、FooterView可占据一行，判断RecyclerView是否到达底部
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) layoutManager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isFootView(position)) {
                        return gridManager.getSpanCount();
                    }
                    return 1;
                }
            });
        }
        setScrollListener(recyclerView);
    }

    @Override
    public void onClick(View v) {
        if (state == STATE_MORE || state == STATE_FAIL || state == STATE_HIDE) {
            startLoadMore();
        }
    }

    public void setLoadMore(boolean loadMore) {
        isLoadMore = loadMore;
    }

    /**
     * 开始加载
     */
    private void startLoadMore() {
        if (isLoadMore && footView != null) {
            setLoadState(STATE_LOADING);
            onAdapterListener.onLoadMore();
        }
    }

    /**
     * 是否是FooterView
     *
     * @param position
     * @return
     */
    private boolean isFootView(int position) {
        return isLoadMore && position >= getItemCount() - 1;
    }

    /**
     * StaggeredGridLayoutManager模式时，HeaderView、FooterView可占据一行
     */
    public void setScrollListener(RecyclerView recycler) {
        if (recyclerView == null) {
            recyclerView = recycler;
            final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {//停止滚动
                        if (findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {

                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (findLastVisibleItemPosition(layoutManager) + 1 == getItemCount()) {

                    }
                }
            });
        }
    }

    /**
     * 最后一个可见item的位置
     *
     * @param layoutManager
     * @return
     */
    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return findMax(lastVisibleItemPositions);
        }
        return -1;
    }

    /**
     * StaggeredGridLayoutManager时，查找position最大的列
     *
     * @param lastVisiblePositions
     * @return
     */
    private int findMax(int[] lastVisiblePositions) {
        int max = lastVisiblePositions[0];
        for (int value : lastVisiblePositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public void setLoadState(int state) {
        setLoadState(state, null);
    }

    /**
     * 设置加载的状态
     *
     * @param state
     */
    public void setLoadState(int state, String text) {
        this.state = state;
        if (isLoadMore && footView != null) {
            switch (state) {
                case STATE_LOADING:
                    footView.loading(text);
                    break;
                case STATE_MORE:
                    footView.loadMore(text);
                    break;
                case STATE_NOMORE:
                    footView.loadNoMore(text);
                    break;
                case STATE_FAIL:
                    footView.loadFail(text);
                    break;
                case STATE_HIDE:
                    footView.loadHide();
                    break;
            }
        }
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
     * @param <D>
     */
    public static abstract class OnAdapterListener<D> {
        /**
         * @param view
         * @param position
         * @param data
         */
        protected abstract void onItemClick(View view, int position, D data);

        /**
         * 加载更多的回调方法
         */
        protected void onLoadMore() {
        }
    }

    /**
     * 加载更多
     */
    private class FootView extends RelativeLayout {

        private TextView textView;
        private ProgressBar progressBar;

        public FootView(Context context) {
            super(context);
            init(context);
        }

        public FootView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        @SuppressLint("ResourceType")
        private void init(Context context) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutParams);

            textView = new TextView(context);
            textView.setId(1);
            LayoutParams textLp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textLp.addRule(RelativeLayout.CENTER_IN_PARENT);
            textView.setLayoutParams(textLp);
            textView.setTextColor(Color.BLACK);
            textView.setText("正在加载...");

            progressBar = new ProgressBar(context);
            LayoutParams barLp = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            barLp.addRule(RelativeLayout.CENTER_IN_PARENT);
            barLp.addRule(RelativeLayout.LEFT_OF, textView.getId());
            progressBar.setLayoutParams(barLp);
            progressBar.setScaleX(0.4f);
            progressBar.setScaleY(0.4f);

            addView(progressBar);
            addView(textView);
        }

        /**
         * 正在加载
         */
        private void loading(String text) {
            state = STATE_LOADING;
            setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            textView.setText(text == null ? "正在加载..." : text);
        }

        /**
         * 加载更多
         */
        private void loadMore(String text) {
            state = STATE_MORE;
            setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            textView.setText(text == null ? "加载更多" : text);
        }

        /**
         * 加载完毕
         */
        private void loadNoMore(String text) {
            state = STATE_NOMORE;
            setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            textView.setText(text == null ? "数据加载完毕" : text);
        }

        /**
         * 加载错误
         */
        private void loadFail(String text) {
            state = STATE_FAIL;
            setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            textView.setText(text == null ? "加载异常，重新加载" : text);
        }

        /**
         * 隐藏加载更多
         */
        private void loadHide() {
            state = STATE_HIDE;
            setVisibility(View.GONE);
        }
    }
}
