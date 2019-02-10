package com.vk.audiodemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

      //mPlayer of type MediaPlayer is initialized
    MediaPlayer mPlayer;
      //AudioManager type allows us to interact with the audio like change volume etc.
    AudioManager audioManager;

    public void playButton(View view){
          //to start the audio
        mPlayer.start();
    }

    public void pauseButton(View view){
          //to pause the audio
        mPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          //this is to setup the audioManager variable to interact with the system audio.
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
          //all devices has their diff. max vol. so maxVol will store the max vol. of the system with this method.
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
          //and currentVolume will store the current vol. of the system.
        int currentVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
          //to access the audio at the onCreate method.
        mPlayer = MediaPlayer.create(this, R.raw.friction);

        //to find our seek bar.
        SeekBar volControl = (SeekBar) findViewById(R.id.seekBar);
          //to set the seek bar to the max vol. value of the system.
        volControl.setMax(maxVol);
          //similarly the current vale is set to the current progress with the progress variable.
        volControl.setProgress(currentVol);
          //to catch the event that when the user drags and take the finger off the seek bar.
          //wee add a seek bar change listener which will catch the user's event.
          //this will initialize the three methods to catch the user's events.
        volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                  //now to finally change the vol.
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar musicControl = (SeekBar) findViewById(R.id.seekBar2);
          //similarly as above, but here we are setting the seek bar length to the length of the audio file.
          //as mPlayer contains our audio file and the length() method tells the length of that file.
        musicControl.setMax(mPlayer.getDuration());
          //info given in the above same method
        musicControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                  //to actually seek the audio to the progress.
               // mPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

          //to update the seek bar with the current playing audio location
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                  //to set the current position of the audio file.
                musicControl.setProgress(mPlayer.getCurrentPosition());
            }
        },0,1000);

    }
}
