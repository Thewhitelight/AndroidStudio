package libery.cn.excursionsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {
    private TextView tv;
    private Button btn;
    private static String json;
    private final static String URL = "http://10.64.130.129:10240/Attraction/AttractionList?flag=0";
    public static String[] add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv_json);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStr();
            }
        });

    }

    private void getStr() {
        // TODO Auto-generated method stub
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(
                URL,
                new Listener<String>() {

                    @Override
                    public void onResponse(String arg0) {
                        // TODO Auto-generated method stub
                        json = arg0;
                        try {
                            parsing();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                tv.setText(arg0.toString());
            }
        });
        rq.add(sr);
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
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, DrawableActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void parsing() throws JSONException {
        JSONArray ja = new JSONArray(json);
        int n = ja.length();
        Map<String, Object> map;
        List<Map<String, Object>> list;
        list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            map = new HashMap<>();
            map.put("Id", ja.getJSONObject(i).getString("Id"));
            map.put("Name", ja.getJSONObject(i).getString("Name"));
            map.put("Describe", ja.getJSONObject(i).getString("Describe"));
            map.put("SsImage", ja.getJSONObject(i).getString("SsImage"));
            map.put("Address", ja.getJSONObject(i).getString("Address"));
            list.add(map);
        }

        int i;
        add = new String[20];

        for (i = 0; i < n; i++) {

            tv.append((String) list.get(i).get("Id"));
            tv.append((String) list.get(i).get("Name"));
            tv.append((String) list.get(i).get("Describe"));
            tv.append((String) list.get(i).get("SsImage"));
            tv.append((String) list.get(i).get("Address"));
            add[i] = list.get(i).get("SsImage").toString();
            System.out.println(add[i]);
        }

    }
}
