package cn.pai.common.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView,Spinner配器
 *
 * @author pany
 */
public abstract class BaseListAdapter<D> extends BaseAdapter implements IAdapter<D> {

    protected Context context;
    protected List<D> datas;

    public BaseListAdapter(Context context, List<D> datas) {
        this.context = context;
        this.datas = datas;
    }

    public BaseListAdapter(Context context) {
        this.context = context;
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
    public void change(List list) {
        if (datas != null && list != null) {
            datas.clear();
            datas.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void changeOne(int position, D data) {
        if (datas != null && data != null && datas.size() > position) {
            datas.remove(position);
            datas.add(position, data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void insert(List<D> list) {
        if (datas != null && list != null && !list.isEmpty()) {
            datas.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public void insertOne(D data) {
        if (datas != null && data != null) {
            datas.add(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public void remove(int positionStart, int itemCount) {
        if (datas != null) {
            for (int i = 0; i < itemCount; i++) {
                datas.remove(positionStart + i);
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public void removeOne(int position) {
        if (datas != null) {
            datas.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public List<D> getDatas() {
        return datas;
    }

    @Override
    public int getCount() {
        return datas != null ? datas.size() : 0;
    }

    @Override
    public D getItem(int position) {
        //spinner在没数据时返回-1
        if (datas != null && !datas.isEmpty() && position >= 0 && datas.size() > position) {
            return datas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

}
