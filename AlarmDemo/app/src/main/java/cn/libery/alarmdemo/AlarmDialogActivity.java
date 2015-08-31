package cn.libery.alarmdemo;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Libery on 2015/8/31.
 * Email:libery.szq@qq.com
 */
public class AlarmDialogActivity extends AppCompatActivity {
    MediaPlayer alarmPlayer;
    //Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_alarm);
        //  btn = (Button) findViewById(R.id.btn_finish);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        alarmPlayer = MediaPlayer.create(this, getSystemDefaultAlarmUri());
        alarmPlayer.setLooping(true);
        alarmPlayer.start();
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                alarmPlayer.stop();
//            }
//        });

        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("提示闹钟")
                .setMessage("今天记得带伞")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                                alarmPlayer.stop();
                            }
                        })
                .show();
    }

    private Uri getSystemDefaultAlarmUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_ALARM);
    }

}

