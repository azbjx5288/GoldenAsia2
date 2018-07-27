package com.goldenasia.lottery.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import com.goldenasia.lottery.R;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 为了适配小米手机中桌面角标而添加的Service
 */
public class BadgeIntentForXiaomiService extends IntentService {

    private int notificationId = 0;

    public BadgeIntentForXiaomiService() {
        super("BadgeIntentForXiaomiService");
    }

    private NotificationManager mNotificationManager;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int badgeCount = intent.getIntExtra("badgeCount", -1);
            if(badgeCount==-1){
                return;
            }

            if(mNotificationManager!=null) {
                mNotificationManager.cancel(notificationId);
                notificationId++;

                Notification.Builder builder = new Notification.Builder(getApplicationContext())
                        .setContentTitle("收件箱有新消息")
                        .setContentText("收件箱有" + badgeCount + "条新消息，注意查收!!!!")
                        .setSmallIcon(R.drawable.ic_launcher);
                Notification notification = builder.build();
                ShortcutBadger.applyNotification(getApplicationContext(), notification, badgeCount);
                if (badgeCount != 0) {
                    mNotificationManager.notify(notificationId, notification);
                }
            }
        }
    }
}
