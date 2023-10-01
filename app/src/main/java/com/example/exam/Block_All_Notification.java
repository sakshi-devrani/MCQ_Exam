package com.example.exam;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
public class Block_All_Notification extends NotificationListenerService {
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        // Implement what you want here
        // Inform the notification manager about dismissal of all notifications.
        Log.d("Msg", "Notification arrived");
        Block_All_Notification.this.cancelAllNotifications();
    }
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        // Implement what you want here
        Log.d("Msg", "Notification Removed");
    }

}
