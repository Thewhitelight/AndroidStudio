package cn.libery.expressionrecognition;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.isnc.facesdk.aty.Aty_FaceFeatures;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SZQ on 2015/5/26.
 */
public class GetFaceInfoActivity extends Aty_FaceFeatures {


    List<Map<String, Object>> list;
    private RelativeLayout rl;
    private LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_face_features);
        initFaceEmotion();
        rl = (RelativeLayout) findViewById(R.id.facedatabg);
        ll = (LinearLayout) findViewById(R.id.facedataload);
        rl.setVisibility(View.VISIBLE);
        // 执行人脸检测扫描
        faceDetecion();
    }

    // 刷脸完成，开始请求数据
    @Override
    public void requestFaceData() {
        ll.setVisibility(View.VISIBLE);
        super.requestFaceData();
    }

    // 处理获取到得数据
    @Override
    public void faceDataCallback(String arg0) {
        ll.setVisibility(View.GONE);
        rl.setVisibility(View.GONE);
        getIntentData(arg0);
        setList();
        super.faceDataCallback(arg0);
    }

    // 获取数据失败
    @Override
    public void getFaceDataFail() {
        ll.setVisibility(View.GONE);
        Toast.makeText(this, "获取人脸信息失败", Toast.LENGTH_SHORT).show();
        super.getFaceDataFail();
    }

    private void setList() {
        ListView lv = (ListView) findViewById(R.id.datalist);
        SimpleAdapter adapter = new SimpleAdapter(this, list,
                R.layout.item_facedata, new String[]{"key", "value"},
                new int[]{R.id.key, R.id.value});
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // 获取得到的表情数据
    private void getIntentData(String facedata) {
        JSONObject obj;
        try {
            obj = new JSONObject(facedata);
            list = new ArrayList<>();
            Log.i("Aty_GetFaceFeatures", "GetFaceInfoActivity" + obj.toString());

            double sex = obj.getJSONObject("male").getInt("result");
            Map<String, Object> map = new HashMap<>();
            if (sex == 1.0) {
                map.put("key", "性别");
                map.put("value", "男");
            } else {
                map.put("key", "性别");
                map.put("value", "女");
            }
            list.add(map);

            double age = obj.getDouble("age");
            map = new HashMap<>();
            map.put("key", "年龄");
            map.put("value", (int) age);
            list.add(map);

            double smile = obj.getJSONObject("smiling").getDouble("score");
            map = new HashMap<>();
            map.put("key", "微笑值");
            map.put("value", String.format("%d", (int) (smile * 100)));
            list.add(map);
            int glasses = obj.getJSONObject("eyeglasses").getInt("result");
            map = new HashMap<>();
            map.put("key", "眼镜");
            if (glasses == 1.0) {
                map.put("value", "有戴");
            } else {
                map.put("value", "没戴");
            }
            list.add(map);

            int sunglasses = obj.getJSONObject("eyeglasses").getInt("result");
            map = new HashMap<>();
            map.put("key", "太阳眼镜");
            if (sunglasses == 1.0) {
                map.put("value", "有戴");
            } else {
                map.put("value", "没戴");
            }
            list.add(map);

            double mustache = obj.getJSONObject("mustache").getInt("score");
            map = new HashMap<>();
            map.put("key", "胡须密度");
            map.put("value", mustache);
            list.add(map);

            double angry = obj.getJSONObject("emotions").getDouble("angry");
            map = new HashMap<>();
            map.put("key", "生气");
            map.put("value", angry);
            list.add(map);
            double calm = obj.getJSONObject("emotions").getDouble("calm");
            map = new HashMap<>();
            map.put("key", "平静");
            map.put("value", calm);
            list.add(map);
            double confused = obj.getJSONObject("emotions").getDouble("confused");
            map = new HashMap<>();
            map.put("key", "困惑");
            map.put("value", confused);
            list.add(map);
            double disgust = obj.getJSONObject("emotions").getDouble("disgust");
            map = new HashMap<>();
            map.put("key", "愤怒");
            map.put("value", disgust);
            list.add(map);
            double happy = obj.getJSONObject("emotions").getDouble("happy");
            map = new HashMap<>();
            map.put("key", "快乐");
            map.put("value", happy);
            list.add(map);
            double sad = obj.getJSONObject("emotions").getDouble("sad");
            map = new HashMap<>();
            map.put("key", "悲伤");
            map.put("value", sad);
            list.add(map);
            double surprised = obj.getJSONObject("emotions").getDouble("surprised");
            map = new HashMap<>();
            map.put("key", "惊喜");
            map.put("value", surprised);
            list.add(map);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void btn_back(View v) {
        finish();
    }

}
