package cn.libery.alarmdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_time;
    private Button btn_ok;
    private TextView tv_time2;
    private Button btn_ok2;
    private Calendar calendar = Calendar.getInstance();
    private Calendar calendar2 = Calendar.getInstance();
    int mHour;
    int mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_time = (TextView) findViewById(R.id.tv_time);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        tv_time2 = (TextView) findViewById(R.id.tv_time2);
        btn_ok2 = (Button) findViewById(R.id.btn_ok2);
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar2.setTimeInMillis(System.currentTimeMillis());
        mHour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);
        tv_time.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        tv_time2.setOnClickListener(this);
        btn_ok2.setOnClickListener(this);
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

    private String format(int x) {
        String s = "" + x;
        if (s.length() == 1) s = "0" + s;
        return s;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time:
                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                calendar.setTimeInMillis(System.currentTimeMillis());
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);
                                String tmpS = format(hourOfDay) + "：" + format(minute);
                                tv_time.setText(tmpS);
                            }
                        }, mHour, mMinute, true).show();
                break;
            case R.id.btn_ok:
                Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                AlarmManager am;
                am = (AlarmManager) getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                btn_ok.setEnabled(false);
                break;
            case R.id.tv_time2:
                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                calendar2.setTimeInMillis(System.currentTimeMillis());
                                calendar2.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar2.set(Calendar.MINUTE, minute);
                                calendar2.set(Calendar.SECOND, 0);
                                calendar2.set(Calendar.MILLISECOND, 0);
                                String tmpS = format(hourOfDay) + ":" + format(minute);
                                tv_time2.setText(tmpS);
                            }
                        }, mHour, mMinute, true).show();
                break;
            case R.id.btn_ok2:
                Intent intent2 = new Intent(MainActivity.this, AlarmReceiver.class);
                PendingIntent sender2 = PendingIntent.getBroadcast(MainActivity.this, 1, intent2, 0);
                AlarmManager am2;
                am2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                am2.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), sender2);
                btn_ok2.setEnabled(false);
                break;
        }
    }
}
