package com.notificationtutorial.musicplayerservice;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import android.app.Notification;
import android.app.PendingIntent;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.List;

public class MediaPlayerService extends Service {

    public String CHANNEL_ID = "4565";
    public List<Music> playlist;
    public Music currnetsong;
    private static int postion = 0;
    public Notification status;
    private final String LOG_TAG = "NotificationService";
    public MediaPlayer mediaPlayer;
    public int resumePosition;

    RemoteViews views ;
    RemoteViews bigViews;



    public MediaPlayerService() {

        playlist = Music.PlayList();
        this.mediaPlayer = new MediaPlayer();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*---------------------------------------------------------------------
      |  Method:  onStartCommand
      |
      |  Purpose:  programmm entry point for service
      |  Parameters:
      |
      |  Returns:  Void
      *-------------------------------------------------------------------*/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION))
        {



            new Thread(new Runnable() {
                @Override
                public void run() {
                    showNotification();
                    // Toast.makeText("Runnable", "Service Started", Toast.LENGTH_SHORT).show();
                }
            }).start();

//            views.setImageViewResource(R.id.status_bar_play,
//                    R.drawable.apollo_holo_dark_pause);
//            views.setImageViewResource(R.id.status_bar_play, R.id.st);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    showNotification();
                    playMedia();
                    // Toast.makeText("Runnable", "Service Started", Toast.LENGTH_SHORT).show();
                }
            }).start();
        }
        else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION))
        {
            --postion;
            if (postion < 0 ) {postion = playlist.size()-1;}

            stopMedia();
            playMedia();

            Toast.makeText(this, "Clicked Previous", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Previous");

        }
        else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION))
        {
            Toast.makeText(this, "Clicked Play", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Play");

        }
        else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION))
        {

            ++postion;
            if (postion > playlist.size()-1 ) {postion = 0;}

            stopMedia();
            playMedia();
            Toast.makeText(this, "Clicked Next", Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Next");
        }
        else if (intent.getAction().equals(Constants.ACTION.STOPFOREGROUND_ACTION))
        {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

        /*---------------------------------------------------------------------
         |  Method:  showNotification
         |
         |  Purpose:  notification launch
         |  Parameters:
         |
         |  Returns:  Void
         *-------------------------------------------------------------------*/

    private void showNotification()
    {        int NOTIFY_ID = 2;

        NotificationManager notificationManager = createChannel();

        // Using RemoteViews to bind custom layouts into Notification
        views = new RemoteViews(getPackageName(), R.layout.custom_small_notification);
        bigViews = new RemoteViews(getPackageName(), R.layout.custom_notification);

        setPendingIntent_view(views, bigViews);

        // showing default album image

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "ID");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);

        builder.setSmallIcon(R.drawable.music)
                .setCustomContentView(views)
                //   .setContentIntent(pendingIntent)
                .setCustomBigContentView(bigViews)

                .setAutoCancel(true)
                .setChannelId(CHANNEL_ID)
                .setContentTitle("Music Player")
                .setContentText("Control Audio")
                . setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();

        notificationManager.notify(NOTIFY_ID, notification);

    }

    /*---------------------------------------------------------------------
      |  Method:  getIntent
      |
      |  Purpose:  generate intent  and setintet action
      |  Parameters:
      |
      |  Returns:  Intent
      *-------------------------------------------------------------------*/
    private Intent getIntent(String action) {


        Intent intent = new Intent(this, MediaPlayerService.class);

        switch (action){

            case  Constants.ACTION.MAIN_ACTION:
                intent.setAction(Constants.ACTION.MAIN_ACTION);

                break;
            case  Constants.ACTION.INIT_ACTION:
                intent.setAction(Constants.ACTION.INIT_ACTION);
                break;
            case  Constants.ACTION.PREV_ACTION:
                intent.setAction(Constants.ACTION.PREV_ACTION);
                break;
            case   Constants.ACTION.PLAY_ACTION:
                intent.setAction(Constants.ACTION.PLAY_ACTION);
                break;
            case  Constants.ACTION.NEXT_ACTION :
                intent.setAction(Constants.ACTION.NEXT_ACTION);
                break;
        }

        return intent;
    }

    /*---------------------------------------------------------------------
      |  Method:  createChannel
      |
      |  Purpose:  creat channel for notification
      |  Parameters:
      |
      |  Returns:  NotificationManager
      *-------------------------------------------------------------------*/
    private NotificationManager createChannel()  {

        NotificationManager notificationManager;

        /// creatting channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, "mychannel",  NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.

            notificationManager.createNotificationChannel(mChannel);

        } else {

            notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        }

        return notificationManager;
    }

    /*---------------------------------------------------------------------
     |  Method:  playMedia
     |
     |  Purpose:  call getsong method and play music
     |  Parameters:
     |
     |  Returns:  void
     *-------------------------------------------------------------------*/
    private void playMedia() {
        /// single instance of music object


        getSong();
        mediaPlayer.start();

        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            public boolean onError(MediaPlayer mp, int what, int extra) {

                return true;
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            public void onCompletion(MediaPlayer mp) {

                mediaPlayer.stop();
                mediaPlayer.reset();
                ++postion;
                if(postion> playlist.size()-1){   postion=0;}

                playMedia();
            }
        });
    }


 /*---------------------------------------------------------------------
      |  Method:  getSong()
      |
      |  Purpose:  get song from assent folder
      |  Parameters:
      |
      |  Returns:  void
      *-------------------------------------------------------------------*/

    private void getSong() {

        currnetsong = playlist.get(postion);

        try{
            mediaPlayer.setDataSource(getResources().openRawResourceFd(currnetsong.getSongId()));
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*---------------------------------------------------------------------
      |  Method:  stopMedia
      |
      |  Purpose: stop music if it is playing
      |  Parameters:
      |
      |  Returns:  void
      *-------------------------------------------------------------------*/
    private void stopMedia() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

    }

    /*---------------------------------------------------------------------
     |  Method:  pauseMedia
     |
     |  Purpose: pause music if it is playing
     |  Parameters:
     |
     |  Returns:  void
     *-------------------------------------------------------------------*/
    private void pauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            resumePosition = mediaPlayer.getCurrentPosition();
        } else if(!mediaPlayer.isPlaying() && resumePosition != 0){

            resumeMedia();
        }


    }
    /*---------------------------------------------------------------------
        |  Method:  resumeMedia
        |
        |  Purpose: resume music if it is pause
        |  Parameters:
        |
        |  Returns:  void
        *-------------------------------------------------------------------*/
    private void resumeMedia() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }else {

        }
    }

       /*---------------------------------------------------------------------
        |  Method:  setPendingIntent_view
        |
        |  Purpose:  setting up click listener
        |  Parameters:
        |
        |  Returns:  void
        *-------------------------------------------------------------------*/

    private void setPendingIntent_view(RemoteViews views,RemoteViews bigViews) {


//        views.setViewVisibility(R.id.status_bar_icon, View.VISIBLE);
//        views.setViewVisibility(R.id.status_bar_album_art, View.GONE);
//        bigViews.setImageViewBitmap(R.id.status_bar_album_art,  Constants.getDefaultAlbumArt(this));



        Intent previousIntent = getIntent(Constants.ACTION.PREV_ACTION);
        PendingIntent ppreviousIntent = PendingIntent.getService(this, 0, previousIntent, 0);

        Intent playIntent = getIntent(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(this, 0,  playIntent, 0);

        Intent nextIntent = getIntent(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(this, 0,  nextIntent, 0);

        Intent closeIntent =getIntent(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pcloseIntent = PendingIntent.getService(this, 0, closeIntent, 0);




        views.setOnClickPendingIntent(R.id.btnPlay, pplayIntent);
        bigViews.setOnClickPendingIntent(R.id.btnPlay, pplayIntent);

        views.setOnClickPendingIntent(R.id.btnNext, pnextIntent);
        bigViews.setOnClickPendingIntent(R.id.btnNext, pnextIntent);

        views.setOnClickPendingIntent(R.id.btnPrevious, ppreviousIntent);
        bigViews.setOnClickPendingIntent(R.id.btnPrevious, ppreviousIntent);

        views.setOnClickPendingIntent(R.id.btnDelete, pcloseIntent);
        bigViews.setOnClickPendingIntent(R.id.btnDelete, pcloseIntent);
        

        views.setTextViewText(R.id.status_bar_track_name, "Music Title");
        bigViews.setTextViewText(R.id.status_bar_track_name, "Music Title");

        views.setTextViewText(R.id.status_bar_artist_name, "Artist Name");
        bigViews.setTextViewText(R.id.status_bar_artist_name, "Artist Name");

         bigViews.setTextViewText(R.id.status_bar_album_name, "Album Name");
    }

}