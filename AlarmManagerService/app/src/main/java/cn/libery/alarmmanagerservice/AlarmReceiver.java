package cn.libery.alarmmanagerservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

public class AlarmReceiver extends BroadcastReceiver {
    private Context mContext;

    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        this.mContext = context;
        Handler handler = new Handler(Looper.getMainLooper());
        // after reboot the device,about 2 minutes later,upload the POI info to server
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ServiceUtil.isServiceRunning(mContext,
                        Constants.SERVICE)) {
                    ServiceUtil.invokeAlarmService(mContext);
                }
            }
        }, Constants.RECEIVER_TIME);
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}
