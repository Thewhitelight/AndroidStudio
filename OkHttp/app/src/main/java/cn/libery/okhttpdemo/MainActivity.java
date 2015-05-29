package cn.libery.okhttpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


public class MainActivity extends ActionBarActivity {
    private TextView tv;
    private OkHttpClient okHttpClient;
    private String url = "https://raw.githubusercontent.com/square/okhttp/master/samples/guide/src/main/java/com/squareup/okhttp/guide/GetExample.java";//"http://publicobject.com/helloworld.txt";
    private static String str;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        okHttpClient = new OkHttpClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    str = getResponse(url);
                    Message msg = new Message();
                    msg.obj = str;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String str = (String) msg.obj;
                tv.setText(str);
            }
        };
    }


    private String getResponse(String url) throws Exception {
        Request request = new Request.Builder().url(url).build();
        Response response = null;

        response = okHttpClient.newCall(request).execute();

        if (response.isSuccessful()) {

            return response.body().string();

        } else {
            return ("Unexpected code " + response);
        }
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
