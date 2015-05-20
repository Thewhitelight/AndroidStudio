package cn.libery.handlerthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class ThreadActivity extends ActionBarActivity {
    private TextView tv_thread;
    private Thread thread;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        tv_thread = (TextView) findViewById(R.id.tv_thread);
        runnable = new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.obj = "Hello Thread!";
                handler.sendMessage(message);
            }
        };
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String content = (String) msg.obj;
                tv_thread.setText(content);
                super.handleMessage(msg);
            }
        };


        tv_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread == null) {
                    thread = new Thread(runnable);
                    thread.start();
                    Log.i("ThreadActivity", "线程已启动");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thread, menu);
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
