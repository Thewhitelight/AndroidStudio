package cn.libery.activeandroiddemo;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by Libery on 2015/7/20.
 * Email:libery.szq@qq.com
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
