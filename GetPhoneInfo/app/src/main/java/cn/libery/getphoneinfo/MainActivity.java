package cn.libery.getphoneinfo;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.renderscript.RenderScript;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    private TextView tv_sns, tv_real_time;
    private ScreenReceiver screenReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenReceiver = new ScreenReceiver();
        screenReceiver.registerScreenReceiver(this);
        tv_sns = (TextView) findViewById(R.id.tv_sns);
        tv_real_time = (TextView) findViewById(R.id.tv_real_time);
        tv_sns.setText(getSmsInPhone());
        long systemClock = SystemClock.uptimeMillis();//System.nanoTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = new Date(systemClock);
        String data = format.format(d);
        tv_real_time.setText("开机时长:" + data);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private String getSmsInPhone() {
        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "context://sms/send";
        final String SMS_URI_DRAFT = "content://sms/draft";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            ContentResolver contentResolver = getContentResolver();
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            Uri uri = Uri.parse(SMS_URI_ALL);
            Cursor cursor = contentResolver.query(uri, projection, null, null, "date desc");
            if (cursor.moveToFirst()) {
                String name, phoneNumber, body, date, type;
                int nameColumn = cursor.getColumnIndex("person");
                int phoneNumberColumn = cursor.getColumnIndex("address");
                int bodyColumn = cursor.getColumnIndex("body");
                int dateColumn = cursor.getColumnIndex("date");
                int typeColumn = cursor.getColumnIndex("type");
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameColumn);
                    phoneNumber = cursor.getString(phoneNumberColumn);
                    body = cursor.getString(bodyColumn);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(Long.parseLong(cursor.getString(dateColumn)));
                    date = dateFormat.format(d);
                    int typeId = cursor.getInt(typeColumn);
                    if (typeId == 1) {
                        type = "接收";
                    } else if (typeId == 2) {
                        type = "发送";
                    } else {
                        type = "";
                    }
                    stringBuilder.append("[");
                    stringBuilder.append(name + "*");
                    stringBuilder.append(phoneNumber + "*");
                    stringBuilder.append(body + "*");
                    stringBuilder.append(date + "*");
                    stringBuilder.append(type + "*");
                    stringBuilder.append("]" + "\n");
                }

            } else {
                stringBuilder.append("no SMS");
            }
            stringBuilder.append("OVER");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onDestroy() {
        screenReceiver.unregisterScreenReceiver(this);
        super.onDestroy();
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
        if (id == R.id.action_call_time) {
            startActivity(new Intent(this, CallTimeActivity.class));
            return true;
        } else if (id == R.id.action_alarm) {
            startActivity(new Intent(this, AlarmActivity.class));
            return true;
        } else if (id == R.id.action_light) {
            startActivity(new Intent(this, LightSensorActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
