package com.vk.eggtimerapp;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    Boolean timerActive = false;
    CountDownTimer countDownTimer;

    public void toastFunction(String toast){
        Toast.makeText(MainActivity.this,""+toast,Toast.LENGTH_LONG).show();
    }

    public void resetTimer(){
        textView.setText("00:00");
        seekBar.setEnabled(true);
        timerActive = false;
        countDownTimer.cancel();
        seekBar.setProgress(0);
        toastFunction("Reset Done!");
    }

    public void updateTimer(int progress){
            //as progress is in seconds (we've set the max to (30*60) seconds).
            //so we're taking minutes from the seconds stored in the progress, converting it into int.
        int minutes = (int) progress/60;
            //then we want the no. of seconds leftover after those minutes.
            //so on converting the minutes into seconds subtract it from the progress.
        int seconds = progress - (minutes*60);
            //now here is a problem that if the seconds or minutes are zero or a single digit then it'll show a single digit like "10:9" or "0:20".
            //so to get rid of that we"ll first check if that is the case or not and then convert that single digit to a double digit with a zero in the front.
            //but to do that first we've need to convert the seconds adn minutes into a string so that we can modify it accordingly.
        String secondString = Integer.toString(seconds);
            //I've converted the minutes and seconds into a string in two different ways.
        String minuteString = String.valueOf(minutes);
            //now to check the single zero case:
        if(seconds <= 9){
            secondString = "0"+secondString;
        }
        if(minutes <= 9){
            minuteString = "0"+minuteString;
        }
            //we can also use like:
            //textView.setText(Integer.toString(minutes)+":"+Integer.toString(seconds);
        textView.setText(minuteString + ":" + secondString);
    }

    public void textTapFunction(View view){

        if(timerActive == false) {
            timerActive = true;
            seekBar.setEnabled(false);
                //as the value stored in the seekBar is in the seconds so we have multiply it with 1000 to get the milliseconds to be passed in the below parameters.
                countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    //Log.i("seconds left", String.valueOf(millisUntilFinished / 1000));
                        //here we first cast the long i.e, millisUntilFinished into int and also convert into seconds.
                    updateTimer((int)(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish() {
                        //we can't use 'this' in place of 'getApplicationContext' as we're in the counter function.
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
        else{
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);

        textView.setText("00:00");
            //these are in minutes.
        int max = 30;

            //multiplied by 60 as we want the slider to be in seconds.
        seekBar.setMax(max*60);
        toastFunction("Slide and set the timer");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                toastFunction("Now, Touch the time!");
            }
        });
    }
}
