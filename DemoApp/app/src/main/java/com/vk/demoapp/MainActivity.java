package com.vk.demoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void clickFunction(View view){

        // to make a toast, here 3 fields are required i.e,
        // 1. where to show (here we want to show it on main activity so we use MainActivity.this)
        // 2. the text to show.
        // 3. the length of the text.
        // then we use the show() function.
        Toast.makeText(MainActivity.this, "Hi there!", Toast.LENGTH_LONG).show();

        // here we've created a variable "myTextField" of type "EditText" which collect the data from the ID i.e "myTextField" and convert the view ID into EditText type and stores into the variable.
        EditText myTextField = (EditText) findViewById(R.id.myTextField);

        // here the Log.i function is used to show the info to the logcat terminal which required two fields i.e Tag & the data to show (here the data is taken from the myTextField variable created above.
        Log.i("Info",myTextField.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
