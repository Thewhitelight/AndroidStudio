package cn.libery.scrollstatelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ־ǿ on 2015/6/30.
 */
public class ListViewAdapter extends BaseAdapter {
    private List<String> list;
    private LayoutInflater layoutInflater;
    private boolean scrollState;

    public ListViewAdapter(Context context, List<String> list) {
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setScrollState(boolean scrollState) {
        this.scrollState = scrollState;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.listview_item, parent, false);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (!scrollState) {
            viewHolder.tv.setText(list.get(position).toString());
            viewHolder.tv.setTag("0");
        } else {
            viewHolder.tv.setTag("1");
            viewHolder.tv.setText("Loading");
        }
        return convertView;
    }

    public class ViewHolder {
        public TextView tv;
    }
}
