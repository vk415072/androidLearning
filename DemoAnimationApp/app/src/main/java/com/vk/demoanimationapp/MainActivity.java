package com.vk.demoanimationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

   /* public void animate(View view){

        ImageView img = (ImageView) findViewById(R.id.imageView);

          //here the img will rotate by 2000dp (- means left).
          //and the duration is in seconds like 2000 = 2 secs
        //img.animate().translationXBy(-2000f).setDuration(2000);
          //here rotate 180 means half rotation or 360 is full rotation.
        //img.animate().rotation(180).setDuration(2000);
          //or we can mix the animations like:
        //img.animate().rotation(360).alpha(1).translationXBy(200).setDuration(2000);
          //or we can scale the img like
        //img.animate().scaleX(0.5f).scaleY(0.5f).setDuration(2000);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = (ImageView) findViewById(R.id.imageView);
          //for initial startup we have to st the X to off the screen:
        img.setX(-1000);
        img.animate().rotation(360).translationXBy(1000).setDuration(2000);
    }
}
