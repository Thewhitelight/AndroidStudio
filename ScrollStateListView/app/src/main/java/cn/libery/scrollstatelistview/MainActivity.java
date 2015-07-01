package cn.libery.scrollstatelistview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ListViewAdapter adapter;
    private ListView listView;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        for (int i = 0; i < 501; i++) {
            list.add("ScrollStateListView");
        }
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        adapter.setScrollState(false);
                        int count = view.getChildCount();
                        for (int i = 0; i < count; i++) {
                            TextView tv = (TextView) view.getChildAt(i).findViewById(R.id.tv);
                            if (tv.getTag() == "true") {
                                tv.setText("ScrollStateListView");
                                tv.setTag("false");
                            }
                        }
                        if (view.getLastVisiblePosition() == view.getCount() - 1) {
                            for (int i = 0; i < 51; i++) {
                                list.add("ScrollStateListView");
                            }
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        adapter.setScrollState(true);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        adapter.setScrollState(true);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
