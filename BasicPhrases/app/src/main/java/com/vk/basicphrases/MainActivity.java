package com.vk.basicphrases;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public void playPhrase(View view){
            //here buttonPressed stores the view that we are tapping on
        Button buttonPressed = (Button) view;
            //for logs, we are showing the tags of each button we'll tap.
        Log.i("Button Pressed",buttonPressed.getTag().toString());
            //we have created a MediaPlayer type variable
            //we could use this but, MediaPlayer.create(this,R.raw.<here a direct file in the raw is required, we can't use like .getTag() here>);
            //so we use this:
            //here we've passed the tag accessed bu the buttonPressed variable converting into a string.
            //then also we have to define data type i.e, raw.
            //and finally the packageName() method.
        MediaPlayer mediaPlayer = MediaPlayer.create(this,getResources().getIdentifier(buttonPressed.getTag().toString(),"raw", getPackageName()));
        mediaPlayer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
