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

import java.util.ArrayList;
import java.util.List;

public class BackgroundMusicService extends Service implements MediaPlayer.OnCompletionListener  {

    private static final String CHANNEL_ID = "bg_channel_id";
    private static final String CHANNEL_NAME = "BG_Channel_Name";
    private static final int IMPORTANCE = NotificationManager.IMPORTANCE_DEFAULT;
    private static final int NOTIFICATION_ID = 123456;
    private MediaPlayer mediaPlayer;

    // value for tracking the current track in track list
    private Integer currentMusicNumber = 0;
    final private int minMusicNumber = 0;
    final private int maxMusicNumber = 6;
    List<Integer> musicList = new ArrayList<>();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new BackgroundMusicBinder();
    }

    public class BackgroundMusicBinder extends Binder {
        public BackgroundMusicService getService() {
            return BackgroundMusicService.this;
        }
    }


    public void playSong() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pauseSong() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stopSong() {
        mediaPlayer.stop();
        mediaPlayer = MediaPlayer.create(this, musicList.get(currentMusicNumber));
    }

    public void previousSong() {
        mediaPlayer.stop();
        if (currentMusicNumber > minMusicNumber) {  // cant go below min amount of track
            currentMusicNumber--;
        }
        mediaPlayer = MediaPlayer.create(this, musicList.get(currentMusicNumber));
    }

    public void nextSong() {
        mediaPlayer.stop();
        if (currentMusicNumber < maxMusicNumber) { // cant go beyond max amount of track
            currentMusicNumber++;
        }
        mediaPlayer = MediaPlayer.create(this, musicList.get(currentMusicNumber));
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getCurrentSong() {
        return currentMusicNumber;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // Handle song completion (e.g., play next song)
    }


    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("BG_Service", "Service started.");
        showNotification("BG_Service", "Service started.");

        // add music to track list to choose from
        musicList.add(R.raw.m00);
        musicList.add(R.raw.m01);
        musicList.add(R.raw.m02);
        musicList.add(R.raw.m03);
        musicList.add(R.raw.m04);
        musicList.add(R.raw.m05);
        musicList.add(R.raw.m06);
        mediaPlayer = MediaPlayer.create(this, musicList.get(currentMusicNumber));
        mediaPlayer.setOnCompletionListener(this);

        return START_NOT_STICKY;
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, IMPORTANCE);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
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
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
        showNotification("BG_Service", "Task removed.");
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
