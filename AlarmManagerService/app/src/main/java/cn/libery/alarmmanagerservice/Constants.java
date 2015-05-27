package cn.libery.alarmmanagerservice;

/**
 * Created by SZQ on 2015/5/27.
 */
public class Constants {
    public static final int TIME = 10 * 1000;//10秒后发送消息
    public static final int RECEIVER_TIME = 1 * 60 * 1000;//开机1分钟后发送广播
    public static final int RECEIVER_SERVICE_COUNT = 50;
    public static final String SERVICE = "cn.libery.alarmmanagerservice.AlarmService";
    public static final String RECEIVER_ACTION = "cn.libery.alarmmanagerservice.AlarmReceiver";
}
