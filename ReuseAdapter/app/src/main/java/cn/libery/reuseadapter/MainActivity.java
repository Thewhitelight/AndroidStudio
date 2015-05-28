package cn.libery.reuseadapter;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ListView listView;
    private ReuseAdapter adapter;
    private List<Bean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        intData();
        adapter = new ReuseAdapter<Bean>(getApplicationContext(), list, R.layout.lv_item) {
            @Override
            public void convertView(ReuseViewHolder viewHolder, final Bean item) {
                viewHolder.setText(R.id.tv_title, item.getTitle());
                viewHolder.setText(R.id.tv_man, item.getMan());
                viewHolder.getView(R.id.tv_title).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "" + item.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void intData() {
        Bean bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android Adapter");
        bean.setMan("Libery");
        list.add(bean);
        bean = new Bean();
        bean.setTitle("Android");
        bean.setMan("Libery");
        list.add(bean);
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
