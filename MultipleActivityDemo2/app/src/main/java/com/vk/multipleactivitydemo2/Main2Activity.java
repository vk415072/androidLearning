package com.vk.multipleactivitydemo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
        textView.append(name+"'s page.");
    }
}
