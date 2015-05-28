package cn.libery.reuseadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by SZQ on 2015/5/28.
 */
public abstract class ReuseAdapter<T> extends BaseAdapter {
    //private LayoutInflater mLayoutInflater;
    private List<T> list;
    private Context mContext;
    private int mItemLayoutId;

    public ReuseAdapter(Context context, List<T> list, int layoutId) {
        this.mContext = context;
        // mLayoutInflater = LayoutInflater.from(context);
        this.list = list;
        this.mItemLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract void convertView(ReuseViewHolder viewHolder, T item);

    private ReuseViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ReuseViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ReuseViewHolder viewHolder = getViewHolder(position, convertView, parent);
        convertView(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }
}
