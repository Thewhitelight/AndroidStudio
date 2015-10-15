package cn.libery.swiperefreshlayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.libery.swiperefreshlayout.SwipeLayout.OnLoadListener;


public class MainActivity extends ActionBarActivity implements OnRefreshListener, OnLoadListener {
    private SwipeLayout swipeRefreshLayout;
    private ListView listView;
    private String[] imgUrls;
    private FrescoAdapter adapter;
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
        Fresco.initialize(this);
        imgUrls = ImgUrls.imgUrls;
        swipeRefreshLayout = (SwipeLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout.setFooterView(this, listView, R.layout.listview_footer);
        adapter = new FrescoAdapter(this, imgUrls);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnLoadListener(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("position", position);
                intent.setClass(getApplicationContext(), ViewPagerActivity.class);
                startActivity(intent);
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

    @Override
    public void onRefresh() {
        ImgUrls.imgUrls[0] = "http://imgsrc.baidu.com/forum/w%3D580%3B/sign=7c239eaf84d6277fe912323018031f30/21a4462309f7905263004d2a0ff3d7ca7bcbd583.jpg";
        handler.sendEmptyMessageDelayed(0, 4000);
    }

    @Override
    public void onLoad() {

        Toast.makeText(MainActivity.this, "loading", Toast.LENGTH_SHORT).show();

        swipeRefreshLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                ImgUrls.imgUrls[ImgUrls.imgUrls.length - 1] = "http://imgsrc.baidu.com/forum/w%3D580%3B/sign=be4c642c7ecb0a4685228b315b58f424/cb8065380cd79123256212a2a8345982b3b78062.jpg";
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setLoading(false);
            }
        }, 1500);


    }

}
