package cn.libery.okhttpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private TextView tv;
    private OkHttpClient okHttpClient;
    private String url = "http://www.baidu.com";//"https://raw.githubusercontent.com/square/okhttp/master/samples/guide/src/main/java/com/squareup/okhttp/guide/GetExample.java";//"http://publicobject.com/helloworld.txt";
    private Handler handler;
    private String str;

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
        /**
         * 异步Get调用
         */
      /*  try {
            getResponseGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 同步Get
     *
     * @param url
     * @return
     * @throws Exception
     */
    private String getResponse(String url) throws Exception {
        Request request = new Request.Builder().url(url).build();
        Response response;

        response = okHttpClient.newCall(request).execute();

        if (response.isSuccessful()) {

            return response.body().string();

        } else {
            return ("Unexpected code " + response);
        }
    }

    /**
     * 异步Get
     *
     * @param url
     * @return
     * @throws Exception
     */
    private void getResponseGet(String url) throws Exception {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i(request.toString(), e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                final String s = response.body().string();
                tv.post(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(s);
                    }
                });
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
            startActivity(new Intent(this, IpActivity.class));
            return true;
        }
        if (id == R.id.action_upload) {
            startActivity(new Intent(this, UploadActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
