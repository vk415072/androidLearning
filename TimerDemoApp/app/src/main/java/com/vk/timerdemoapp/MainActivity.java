package com.vk.timerdemoapp;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // this is a way is used to run certain bunch of code every interval of time
        /*
            //handler is used to run chunks of code in a delayed way like every sec. or every min.
        final Handler handler = new Handler();
            //one of that chunk of code is Runnable.
        Runnable runnable = new Runnable() {
            @Override
                //this is automatically created method by Runnable:
            public void run() {
                //insert code to run every particular interval of time
                Log.i("Runnable has run","a sec has passed...");
                    //we're delaying the run method with postDelayed by handler
                    //this means the run() method
                handler.postDelayed(this, 1000);
            }
        };
            //we've to initialized the run() at the first place so..
            //this will execute the runnable right from the start.
        handler.post(runnable);
        */


            // this way is used as a countdown to a particular event
            // a countdown timer destroys itself after its done counting while the handler method remains in existence.
            // here there is no need to store the new CounterDownTimer in a CountDownTimer variable, I just write to make it familiar to myself.
            // CountDownTimer requires two parameters (numbers):
                // 1. no. of milli sec. until the counter has run out.
                // 2. The frequency with which we want the counter take down or the no. of ticks.
        CountDownTimer countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Countdown is counting down (every sec)
                Log.i("Seconds Left", String.valueOf(millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                //countdown timer finished after 10 sec.
                Log.i("Done", "Countdown Timer Finished!");
            }
        }.start();
    }
}
