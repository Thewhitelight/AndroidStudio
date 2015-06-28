package cn.libery.swiperefreshlayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements OnRefreshListener, SwipeLayout.OnLoadListener {
    private SwipeLayout swipeRefreshLayout;
    private ListView listView;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout.setFooterView(this, listView, R.layout.listview_footer);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, getData());

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadListener(this);
        listView.setAdapter(adapter);

    }

    private List getData() {
        list = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            list.add("Item" + i);
        }
        return list;
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

    @Override
    public void onRefresh() {
        list.add(0, "New Item");
        handler.sendEmptyMessageDelayed(0, 4000);
    }

    @Override
    public void onLoad() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 11; i++) {
//                    list.add("More Item" + list.size());
//                }
//                adapter.notifyDataSetChanged();
//                swipeRefreshLayout.setLoading(false);
//            }
//        }, 2000);


        Toast.makeText(MainActivity.this, "loading", Toast.LENGTH_SHORT).show();

        swipeRefreshLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 11; i++) {
                    list.add("More Item" + list.size());
                }
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setLoading(false);
            }
        }, 1500);


    }

}
