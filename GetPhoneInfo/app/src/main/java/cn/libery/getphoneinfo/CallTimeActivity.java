package cn.libery.getphoneinfo;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CallTimeActivity extends ActionBarActivity {
    private TextView tv_callTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_time);
        tv_callTime = (TextView) findViewById(R.id.tv_calltime);
        getCallTimeInPhone();
    }

    private void getCallTimeInPhone() {
        StringBuilder stringBuilder = new StringBuilder();
        Cursor cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " desc");
        if (cursor.moveToFirst()) {
            do {
                // CallsLog calls = new CallsLog();
                //号码
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                //呼叫类型
                String type;
                switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)))) {
                    case CallLog.Calls.INCOMING_TYPE:
                        type = "呼入";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        type = "呼出";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        type = "未接";
                        break;
                    default:
                        type = "挂断";//应该是挂断.根据我手机类型判断出的
                        break;
                }
                SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
                //呼叫时间
                String time = sfd.format(date);
                //联系人
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME));
                //通话时间,单位:s
                String duration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));

                stringBuilder.append(name + "*" + duration + "*" + time + "*" + type + "*" + number + "*" + "\n");

            } while (cursor.moveToNext());

        }
        tv_callTime.setText(stringBuilder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_call_time, menu);
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
