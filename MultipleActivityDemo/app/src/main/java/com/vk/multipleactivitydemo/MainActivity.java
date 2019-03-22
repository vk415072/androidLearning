package com.vk.multipleactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public void tapToGo(View view){
        //Intent generally changes the target of our code.
        //used to jump to a certain activity or even a certain app.
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);

        // intents can be useful to pass some info to another activity
        // first name of intent and then the value.
        intent.putExtra("age", 28);

        //actually starting the intent.
        startActivity(intent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
