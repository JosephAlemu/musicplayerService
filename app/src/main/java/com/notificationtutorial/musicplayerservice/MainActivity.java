package com.notificationtutorial.musicplayerservice;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnPrevious;
    Button  btnPlay;
    Button btnNext;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUp();


    seekBar.setMax(mediaPlayer.getDuration());


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    mediaPlayer.seekTo(i);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }



    public void setUp() {

          mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.rockabye);


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
                if(mediaPlayer.isPlaying()){
                    CustomNotification.customBigNotification(this);
                    pauseMusic();

                }else {
                    playMusic();
                }
                break;
            case R.id.btnPrevious:
                break;
            case R.id.btnNext:
                break;


        }

    }
    public void pauseMusic() {
        if(mediaPlayer != null){

            mediaPlayer.pause();
            btnPlay.setBackgroundResource(R.drawable.play);
        }


    }
    public void playMusic() {

        if(mediaPlayer != null){

            mediaPlayer.start();
            btnPlay.setBackgroundResource(R.drawable.pause);
        }
    }



}
