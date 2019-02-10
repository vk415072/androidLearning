package com.vk.demoapp2;

import android.os.UserManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    boolean imgShowing = true;

    public void fade(View view){

         //here we are taking "ImageView" class and creating its object i.e, "img"
         //and providing an imageView ID.
        ImageView img = (ImageView) findViewById(R.id.imageView);
        ImageView img2 = (ImageView) findViewById(R.id.imageView2);

        if(imgShowing) {
             //here if img is showing then we have to update to false so that on next tap, the img is not shown.
            imgShowing = false;
             //here animate() is used to add animation.
             //alpha() is used for transparency(0 means invisible & 1 means visible)
             //we also mention 'f' to declare the input digits in the alpha() method as float.
             //then set duration is for the time to perform the animation (2000 = 2 sec.)
            img.animate().alpha(0f).setDuration(2000);
            img2.animate().alpha(1f).setDuration(2000);
        }else{

            imgShowing = true;
            img.animate().alpha(1f).setDuration(2000);
            img2.animate().alpha(0f).setDuration(2000);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
