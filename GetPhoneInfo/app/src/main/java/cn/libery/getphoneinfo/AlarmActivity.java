package cn.libery.getphoneinfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class AlarmActivity extends ActionBarActivity {
    private TextView tv_alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        tv_alarm = (TextView) findViewById(R.id.tv_alarm);
        getAlarmInPhone();
    }

    private void getAlarmInPhone() {
        //获取下次闹钟时间
        String alarm = Settings.System.getString(this.getContentResolver(),
                Settings.System.NEXT_ALARM_FORMATTED);
        tv_alarm.setText(alarm + "\n" + getRunningServicesInfo(this));
    }


    public static String getRunningServicesInfo(Context context) {
        StringBuffer serviceInfo = new StringBuffer();
        final ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(100);
        Iterator<ActivityManager.RunningServiceInfo> l = services.iterator();
        while (l.hasNext()) {
            ActivityManager.RunningServiceInfo si = l.next();
            serviceInfo.append("pid: ").append(si.pid);
            serviceInfo.append("\nprocess: ").append(si.process);
            serviceInfo.append("\nservice: ").append(si.service);
            serviceInfo.append("\ncrashCount: ").append(si.crashCount);
            serviceInfo.append("\nclientCount: ").append(si.clientCount);
            serviceInfo.append("\nactiveSince: ").append(formatData(si.activeSince));
            serviceInfo.append("\nlastActivityTime: ").append(formatData(si.lastActivityTime));
            serviceInfo.append("\n\n");
        }
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        for (int j = 0; j < list.size(); j++) {
            serviceInfo.append("\nprocessName:" + j+list.get(j).processName);
        }
        return serviceInfo.toString();
    }

    public static String formatData(long data) {
        Date d = new Date(data);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(d);
        return str;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alarm, menu);
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
