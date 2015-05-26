package cn.libery.expressionrecognition;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetFaceActivity extends ActionBarActivity {


    @SuppressWarnings("unused")
    private String emotion;
    double maxValue = 0.00;
    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_face_features);
        setList();
    }

    private void setList() {
        ListView lv = (ListView) findViewById(R.id.datalist);
        SimpleAdapter adapter = new SimpleAdapter(this, getIntentData(),
                R.layout.item_facedata, new String[]{"key", "value"},
                new int[]{R.id.key, R.id.value});
        lv.setAdapter(adapter);
    }

    // 获取得到的表情数据
    private List<Map<String, Object>> getIntentData() {
        String facedata = getIntent().getExtras().getString("facedata");
        Log.i("Aty_AppGetFaceFeatures", facedata + " ");
        JSONObject obj;
        try {
            obj = new JSONObject(facedata);
            list = new ArrayList<Map<String, Object>>();

            double sex = obj.getJSONObject("male").getInt("result");
            Map<String, Object> map = new HashMap<String, Object>();
            if (sex == 1.0) {
                map.put("key", "性别");
                map.put("value", "男");
            } else {
                map.put("key", "性别");
                map.put("value", "女");
            }
            list.add(map);

            double age = obj.getDouble("age");
            map = new HashMap<String, Object>();
            map.put("key", "年龄");
            map.put("value", (int) age);
            list.add(map);

            double smile = obj.getJSONObject("smiling").getDouble("score");
            map = new HashMap<String, Object>();
            map.put("key", "微笑值");
            map.put("value", String.format("%d", (int) (smile * 100)));
            list.add(map);
            int glasses = obj.getJSONObject("eyeglasses").getInt("result");
            map = new HashMap<String, Object>();
            map.put("key", "眼镜");
            if (glasses == 1.0) {
                map.put("value", "有戴");
            } else {
                map.put("value", "没戴");
            }
            list.add(map);

            int sunglasses = obj.getJSONObject("eyeglasses").getInt("result");
            map = new HashMap<String, Object>();
            map.put("key", "太阳眼镜");
            if (sunglasses == 1.0) {
                map.put("value", "有戴");
            } else {
                map.put("value", "没戴");
            }
            list.add(map);

            double mustache = obj.getJSONObject("mustache").getInt("score");
            map = new HashMap<String, Object>();
            map.put("key", "胡须密度");
            map.put("value", mustache);
            list.add(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void btn_back(View v) {
        finish();
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