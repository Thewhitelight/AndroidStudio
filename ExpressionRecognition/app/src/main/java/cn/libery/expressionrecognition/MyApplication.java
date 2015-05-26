package cn.libery.expressionrecognition;

import android.app.Application;

import com.isnc.facesdk.SuperID;

/**
 * Created by SZQ on 2015/5/26.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SuperID.initFaceSDK(this);
        SuperID.setDebugMode(true);
    }
}
