package cn.libery.handlertoast;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;


public class MainActivity extends ActionBarActivity {
    private Button btn;
    private TextView tv_show;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn_toast);
        tv_show = (TextView) findViewById(R.id.tv_show);
        image = (ImageView) findViewById(R.id.image);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainProcessing();
                showId();
                image.setImageResource(getDrawableId("abc_btn_check_material"));
            }
        });
    }

    private int getDrawableId(String imageName) {
        try {
            Field field = R.drawable.class.getField(imageName);
            return Integer.parseInt(field.get(null).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void showId() {
        int color = getResources().getIdentifier("cn.libery.handlertoast:color/abc_search_url_text_normal", null, null);
        tv_show.setText(color + " ");
        tv_show.setTextColor(getResources().getColor(color));
    }

    public Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.post(doUpdate);
        }
    };

    public void mainProcessing() {
        Thread thread = new Thread(null, runnable, "background");
        thread.start();
    }

    private Runnable doUpdate = new Runnable() {
        @Override
        public void run() {
            Context context = getApplicationContext();
            String str = "toast";
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
        }
    };

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
            startActivity(new Intent(this, GradientDrawableActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
