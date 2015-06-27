package cn.libery.snackbar;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private TextView tv;
    private PopupWindow popupWindow;
    private View view, v;
    private TextView tv_sb;
    private Button btn_sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = getLayoutInflater().inflate(R.layout.activity_main, null);
        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar("测试", true);
            }
        });

    }

    /**
     * 按钮点击事件，只需在xml中设置button的onClick="onClicked"则可以直接获取对象
     *
     * @param v
     */
    public void onClicked(View v) {
        if (R.id.btn == v.getId()) {
            Toast.makeText(getApplicationContext(), "已点击", Toast.LENGTH_LONG).show();
        }
    }

    public void showSnackbar(String text, boolean has) {
        view = getLayoutInflater().inflate(R.layout.snackbar, null);
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true);

        tv_sb = (TextView) view.findViewById(R.id.tv_toast);
        btn_sb = (Button) view.findViewById(R.id.btn_undo);
        tv_sb.setText(text);
        if (has) {
            btn_sb.setVisibility(View.VISIBLE);
            btn_sb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "已点击", Toast.LENGTH_LONG).show();
                    popupWindow.dismiss();
                }
            });
        }
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER, 0, 100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        return super.onTouchEvent(event);
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
