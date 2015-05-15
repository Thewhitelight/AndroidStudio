package libery.cn.excursionsdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


public class DrawableActivity extends ActionBarActivity {
    private ListView lv_drawable;
    private String[] imgUrl = MainActivity.add;
    private DisplayImageOptions options;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        options = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.mipmap.ic_launcher).
                showImageOnFail(R.mipmap.ic_launcher).
                showImageOnLoading(R.mipmap.ic_launcher).
                cacheInMemory(true).cacheOnDisk(true).
                imageScaleType(ImageScaleType.EXACTLY).
                considerExifParams(true)
                .build();
        setContentView(R.layout.activity_drawable);
        lv_drawable = (ListView) findViewById(R.id.lv_drawable);
        adapter = new MyAdapter(this);
        lv_drawable.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return imgUrl.length;
        }

        @Override
        public Object getItem(int position) {
            return imgUrl[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            ImageView img = (ImageView) convertView.findViewById(R.id.imageView);
            if (imgUrl == null)
                Log.e("DrawableActivity", "imgUrl is null");
            ImageLoader.getInstance().displayImage(imgUrl[position], img, options);
            return convertView;
        }
    }
}