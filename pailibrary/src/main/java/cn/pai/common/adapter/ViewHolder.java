package cn.pai.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * description：
 * author：pany
 * on 2018/5/13 10:06
 */
public class ViewHolder extends RecyclerView.ViewHolder {

    protected SparseArray<View> views;
    private int viewType;

    public ViewHolder(View itemView, int viewType) {
        super(itemView);
        // TODO Auto-generated constructor stub
        this.views = new SparseArray<>();
        this.viewType = viewType;
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

    public int getViewType() {
        return viewType;
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
