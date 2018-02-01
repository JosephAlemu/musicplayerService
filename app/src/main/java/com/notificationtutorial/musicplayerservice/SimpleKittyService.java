package com.notificationtutorial.musicplayerservice;

/**
 * Created by Admin on 2/1/2018.
 */

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by joseph alemu
 */
public class SimpleKittyService extends Service {


    private static boolean izPlaying;
    MediaPlayer mediaPlayer;
    int resumePosition;

    public static final String NOTIFY_PREVIOUS = "previous";
    public static final String NOTIFY_DELETE = "delete";
    public static final String NOTIFY_PAUSE = "pause";
    public static final String NOTIFY_PLAY = "play";
    public static final String NOTIFY_NEXT = "next";



    private static  int count =  0;




    public static Intent getPauseIntent(Context context,boolean isPlaying) {

        Intent intent = new Intent(context, SimpleKittyService.class);
        intent.setAction(SimpleKittyService.NOTIFY_PAUSE);
        izPlaying = isPlaying;
        return intent;
    }

    public static Intent getStartIntent(Context context,boolean isPlaying) {

        Intent intent = new Intent(context, SimpleKittyService.class);
        intent.setAction(SimpleKittyService.NOTIFY_PLAY);
        izPlaying = isPlaying;
        return intent;
    }

    public static Intent getDeleteIntent(Context context,boolean isPlaying) {
        izPlaying = isPlaying;
        Intent intent = new Intent(context, SimpleKittyService.class);
        intent.setAction(SimpleKittyService.NOTIFY_DELETE);
        return intent;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String action = intent.getAction();

        if(NOTIFY_PLAY.equalsIgnoreCase(action))
            playMedia();

        else if (NOTIFY_PAUSE.equalsIgnoreCase(action))
            pauseMedia();

        else if (NOTIFY_DELETE.equalsIgnoreCase(action))
            handleStop();

        return START_NOT_STICKY;
    }

    private void handleStart() {

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.rockabye);

        displayStatusMessage("Service starting");
    }

    private void handleStop() {

        stopMedia();
        stopForeground(true);
        stopSelf();
        displayStatusMessage("Service stopping");
    }

    private void handlePause() {


        displayStatusMessage("Service stopping");
    }

    private void displayStatusMessage(String message) {
        LogHelper.Log(message);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }



    public void customBigNotification(Context context, boolean isPlaying) {

        izPlaying = isPlaying;
        int NOTIFY_ID= 2;
        String CHANNEL_ID = "4565";
        NotificationManager notificationManager;

        /// creatting channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            // The user-visible name of the channel.
            CharSequence name = "music_channel";
            // The user-visible description of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            // Configure the notification channel.

            notificationManager.createNotificationChannel(mChannel);


        } else {

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        }

        //  Context context = getActivity();
        RemoteViews notificationViews = new RemoteViews(context.getPackageName(), R.layout.custom_notification);


        ///  stop intent setup
        Intent intentStop = new Intent(context, SimpleKittyService.class);
        intentStop.setAction(MainActivity.STOP_ACTION);

        PendingIntent stopPendingIntent =
                PendingIntent.getService(context, 0, intentStop, 0);
        notificationViews.setOnClickPendingIntent(R.id.btnPlay, stopPendingIntent);



        ///  start intent setup
        Intent intentStart = new Intent(context, SimpleKittyService.class);
        intentStart.setAction(MainActivity.START_ACTION);


        PendingIntent startPendingIntent =
                PendingIntent.getService(context, 0, intentStart, 0);
        notificationViews.setOnClickPendingIntent(R.id.btnPlay, startPendingIntent);


       //Intent intent1 = new Intent(context, MainActivity.class);
      //    PendingIntent pendingIntent = PendingIntent.getActivity(context, 123, intent1, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder  builder = new NotificationCompat.Builder(context, "ID");

        Intent pause = getPauseIntent(context,isPlaying);


        builder.setSmallIcon(R.drawable.play)
                 .setContent(notificationViews)
              //   .setContentIntent(pendingIntent)
                 .setAutoCancel(true)
                 .setChannelId(CHANNEL_ID)
                 .setContentTitle("Music Player")
                 .setContentText("Control Audio");
        setListeners(notificationViews,context);
        Notification notification = builder.build();


        notificationManager.notify(NOTIFY_ID, notification);


    }

    private  void setListeners(RemoteViews view, Context context) {
        Intent previous =   getPauseIntent(context,izPlaying);
        Intent delete =   getDeleteIntent(context,izPlaying);
        Intent pause = getPauseIntent(context,izPlaying);
        Intent next = getPauseIntent(context,izPlaying);
        Intent play = getStartIntent(context,izPlaying);




        PendingIntent pDelete = PendingIntent.getService(context, 0, delete, 0);
        view.setOnClickPendingIntent(R.id.btnDelete, pDelete);




        PendingIntent pNext = PendingIntent.getService(context, 0, next, 0);
        view.setOnClickPendingIntent(R.id.btnNext, pNext);



            PendingIntent pPause = PendingIntent.getService(context, 0, pause, 0);
            view.setOnClickPendingIntent(R.id.btnPlay, pPause);



            PendingIntent pPlay = PendingIntent.getService(context, 0, play, 0);
            view.setOnClickPendingIntent(R.id.btnPlay, pPlay);




    }
    private void playMedia() {
     /// single instance of music object
      if (count == 0){
           handleStart();
          count++;
      }

        if (!izPlaying&&resumePosition!=0) {
            resumeMedia();

        }
        else if (!izPlaying&&resumePosition==0) {

            mediaPlayer.start();
        }
    }

    private void stopMedia( ) {
        if (mediaPlayer == null) return;
        if (izPlaying) {
            mediaPlayer.stop();
        }
    }

    private void pauseMedia( ) {
        if (izPlaying) {
            mediaPlayer.pause();
            resumePosition = mediaPlayer.getCurrentPosition();
        }


    }
    private void resumeMedia() {
        if (!izPlaying) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }
    }


}
