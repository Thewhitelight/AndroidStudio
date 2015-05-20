package cn.libery.handlerthread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainHandlerThreadActivity extends ActionBarActivity {
    private TextView tv;
    private Handler handler;
    private HandlerThread handlerThread;
    private final static String TAG = "HandlerThread";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        Log.i(TAG, handlerThread.currentThread().toString());
        handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Log.i(TAG, handlerThread.currentThread().toString() + "handlerMessage");
                tv.setText(msg.toString());
                super.handleMessage(msg);
            }
        };
        Message msg = new Message();
        msg.arg1 = 1111;
        handler.sendMessage(msg);

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
            startActivity(new Intent(this, ThreadActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
