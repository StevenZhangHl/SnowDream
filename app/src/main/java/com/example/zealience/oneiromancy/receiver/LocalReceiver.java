package com.example.zealience.oneiromancy.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.KeyConstant;
import com.example.zealience.oneiromancy.ui.activity.MainActivity;
import com.example.zealience.oneiromancy.ui.activity.user.UserInfolActivity;
import com.steven.base.util.AppUtil;

/**
 * @user steven
 * @createDate 2019/4/22 14:07
 * @description 自定义
 */
public class LocalReceiver extends BroadcastReceiver {
    private final String channelId = "snow";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals("com.snow.user.info.update")) {
                createNotificaition(context, intent);
                return;
            }
            if (intent.getAction().equals("notification_open")) {
                jumpPage(context, intent);
            }
        }
    }

    private void createNotificaition(Context context, Intent intent) {
        String title = context.getResources().getString(R.string.app_name);
        String content = "";
        if (intent.hasExtra(KeyConstant.RECEIVER_TITLE_KEY)) {
            title = intent.getStringExtra(KeyConstant.RECEIVER_TITLE_KEY);
        }
        if (intent.hasExtra(KeyConstant.RECEIVER_CONTENT_KEY)) {
            content = intent.getStringExtra(KeyConstant.RECEIVER_CONTENT_KEY);
        }
        intent.setAction("notification_open");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, context.getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
            notification = new NotificationCompat.Builder(context)
                    .setAutoCancel(true)
                    .setChannelId(channelId)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .build();
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis());
            notification = builder.build();
        }
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        notificationManager.notify(1, notification);
    }

    /**
     * 根据页码返回不同的intent
     *
     * @param context
     * @param intent
     * @return
     */
    private void jumpPage(Context context, Intent intent) {
        int pageCode = intent.getIntExtra(KeyConstant.PAGE_CODE_KEY, -1);
        if (AppUtil.isAppIsInBackground(context)) {
            intent.setClass(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        switch (pageCode) {
            case 1:
                intent = new Intent(context, UserInfolActivity.class);
                break;
            case 2:
                break;
        }
        context.startActivity(intent);
    }
}
