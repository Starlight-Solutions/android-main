package com.example.sleep_application.ui.music_player;

import static android.app.NotificationChannel.DEFAULT_CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sleep_application.MainActivity;
import com.example.sleep_application.R;
import com.google.android.material.snackbar.Snackbar;

public class BackgroundMusicService extends Service {

    private static final String CHANNEL_ID = "bg_channel_id";
    private static final String CHANNEL_NAME = "BG_Channel_Name";
    private static final int IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;
    private static final int NOTIFICATION_ID = 123456;
    private MediaPlayer mediaPlayer;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BG_Service", "Service started.");
        showNotification("BG_Service", "Service started.");
        mediaPlayer = MediaPlayer.create(this, R.raw.m06);
        mediaPlayer.start();
        return START_NOT_STICKY;
    }

    private void showNotification(String title, String message) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_menu_slideshow)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
        showNotification("BG_Service", "Task removed.");
        mediaPlayer.release();
    }

}
