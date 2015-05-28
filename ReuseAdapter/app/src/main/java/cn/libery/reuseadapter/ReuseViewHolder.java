package cn.libery.reuseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SZQ on 2015/5/28.
 */
public class ReuseViewHolder {
    private SparseArray<View> mViews = null;
    private View mConvertView;
    private int mPosition;

    public ReuseViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mPosition = position;
        this.mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    public static ReuseViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ReuseViewHolder(context, parent, layoutId, position);
        }
        return (ReuseViewHolder) convertView.getTag();
    }

    public View getConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ReuseViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public int position() {
        return mPosition;
    }

    /**
     * 加载drawable
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ReuseViewHolder setImageResource(int viewId, int resId) {
        ImageView img = getView(viewId);
        img.setImageResource(resId);
        return this;
    }

    /**
     * 加载bitmap
     *
     * @param viewId
     * @param bitmap
     * @return
     */
    public ReuseViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView img = getView(viewId);
        img.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 加载网络图片
     *
     * @param viewId
     * @param url
     * @return
     */
    public ReuseViewHolder setImageUrl(int viewId, String url) {
        ImageView img = getView(viewId);
        Uri uri = Uri.parse(url);
        img.setImageURI(uri);
        return this;
    }
}
