package cn.libery.okhttpdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class IpActivity extends ActionBarActivity implements View.OnClickListener {
    private EditText edit_ip;
    private Button btn_ok, btn_ok_post;
    private TextView tv;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.HTTP_FAIL) {
                Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
            if (msg.what == Constant.HTTP_SUCCESS) {
                String ip = msg.obj.toString();
                ip = ParseJson.ParseJson(ip);
                tv.setText(ip);
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);
        initView();
    }

    private void initView() {
        edit_ip = (EditText) findViewById(R.id.edit_ip);
        btn_ok = (Button) findViewById(R.id.ok);
        tv = (TextView) findViewById(R.id.tv_response);
        btn_ok_post = (Button) findViewById(R.id.ok_post);
        btn_ok_post.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ip, menu);
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
    public void onClick(View v) {
        if (v == btn_ok) {
            if (edit_ip.getText().toString().trim().equals("")) {
                Toast.makeText(getApplicationContext(), "请输入IP", Toast.LENGTH_SHORT).show();
            }
            String ip = edit_ip.getText().toString().trim();
            final Map<String, Object> map = new HashMap<>();
            map.put("ip", ip);
            Http http = new Http().getInstance();
            http.getHttp(map, "service/getIpInfo.php", new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Message msg = new Message();
                    msg.what = Constant.HTTP_FAIL;
                    msg.obj = e;
                    handler.sendMessage(msg);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Message msg = new Message();
                    msg.what = Constant.HTTP_SUCCESS;
                    msg.obj = response.body().string();
                    handler.sendMessage(msg);
                }
            });
        }
        if (v == btn_ok_post) {
            if (edit_ip.getText().toString().trim().equals("")) {
                Toast.makeText(getApplicationContext(), "请输入IP", Toast.LENGTH_SHORT).show();
            }
            String ip = edit_ip.getText().toString().trim();
            final Map<String, Object> map = new HashMap<>();
            map.put("ip", ip);
            Http http = new Http().getInstance();
            http.postHttp(map, "service/getIpInfo.php", new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Message msg = new Message();
                    msg.what = Constant.HTTP_FAIL;
                    msg.obj = e;
                    handler.sendMessage(msg);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Message msg = new Message();
                    msg.what = Constant.HTTP_SUCCESS;
                    msg.obj = response.body().string();
                    handler.sendMessage(msg);
                }
            });
        }
    }
}
