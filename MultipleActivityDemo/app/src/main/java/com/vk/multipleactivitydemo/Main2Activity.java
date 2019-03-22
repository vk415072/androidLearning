package com.vk.multipleactivitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    public void tapToBack(View view){
        //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //startActivity(intent);

        // but with these lines of codes we are creating more and more intents so to actually go to the main activity we can use:
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //to access the passed info from another activity
        //getting and storing the passed intent in an intent
        Intent intent = getIntent();
        //getting intExtra named 'age' and storing in an int.
        int age = intent.getIntExtra("age", 0);

        Toast.makeText(this,Integer.toString(age), Toast.LENGTH_LONG).show();
    }
}
