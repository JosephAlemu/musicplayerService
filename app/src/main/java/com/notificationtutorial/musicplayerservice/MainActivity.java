package com.notificationtutorial.musicplayerservice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnPrevious;
    Button  btnPlay;
    Button btnNext;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    Context context;
    static boolean isplaying = false;

    public static final String START_ACTION = "start";
    public static final String STOP_ACTION = "stop";


    NotificationManager notificationManager;
    private static final String id = "45612";
    public static final String NOTIFICATION_CHANNEL_ID = "4565";
    NotificationCompat.Builder notificationBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         context = this;

//        mediaPlayer = new MediaPlayer();
//        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.rockabye);


        btnPrevious = (Button)findViewById(R.id.btnPrevious);
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnNext = (Button)findViewById(R.id.btnNext);
        seekBar = (SeekBar)findViewById(R.id.seekBar);


        btnPlay.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        btnNext.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnPlay:
                if (isplaying){
                    isplaying = false;
                    btnPauseServiceOnClick();

                }else{
                    isplaying = true;
                    btnStartServiceOnClick();

                }
                break;
            case R.id.btnNext:
                break;


        }

    }

    private void btnStartServiceOnClick() {
        Context context = getApplicationContext();
        btnPlay.setBackgroundResource(R.drawable.pause);
        Intent intent = MediaPlayerService.getStartIntent(context,!isplaying);

        startService(intent);

    }

    private void btnPauseServiceOnClick() {
        Context context = getApplicationContext();
        btnPlay.setBackgroundResource(R.drawable.play);
        Intent intent = MediaPlayerService.getPauseIntent(context,!isplaying);
        startService(intent);

    }


    public void notificationMethod() {


            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 123, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

            notificationBuilder.setSmallIcon(R.drawable.play) // icon
                    .setBadgeIconType(R.drawable.album) //  icon
                    .setChannelId(id)
                    .setContentTitle("Notification Title ")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setNumber(1)
                    .setColor(255)
                    .setContentText("Hello there you just clicked notification, peace")
                    .setWhen(System.currentTimeMillis());
            notificationManager.notify(1, notificationBuilder.build());



    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaPlayerService mediaPlayerService = new MediaPlayerService();
        mediaPlayerService.customBigNotification(getApplicationContext());
    }
}
