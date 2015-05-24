package cn.libery.getphoneinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {
    private boolean isRegister;
    private final static String TAG = "ScreenReceiver";
    public String screenState;

    public ScreenReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        String action = intent.getAction();
        if (action == Intent.ACTION_SCREEN_ON) {
            screenState = "屏幕解锁";
            Log.i(TAG, screenState);
        }
        if (action == Intent.ACTION_SCREEN_OFF) {
            screenState = "屏幕加锁";
            Log.i(TAG, screenState);
        }
        //  throw new UnsupportedOperationException("Not yet implemented");
    }

    public void registerScreenReceiver(Context context) {
        if (!isRegister) {
            isRegister = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            context.registerReceiver(ScreenReceiver.this, intentFilter);
        }
    }

    public void unregisterScreenReceiver(Context context) {
        if (isRegister) {
            isRegister = false;
            context.unregisterReceiver(ScreenReceiver.this);
        }
    }

    ;
}
