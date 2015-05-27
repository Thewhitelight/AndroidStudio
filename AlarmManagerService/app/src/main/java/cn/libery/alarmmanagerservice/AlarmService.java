package cn.libery.alarmmanagerservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {
    private static final String TAG = "AlarmService";

    public AlarmService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "---onCreate---");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "---onStartCommand---");
        uploadService();
        return super.onStartCommand(intent, flags, startId);
    }

    private void uploadService() {
        Log.i(TAG, "---uploadService---");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
