package com.vk.toastastext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void showToastButtonFunction(View view){
        //  here we've created a variable "toastText" of type "EditText" which collect the data from the ID i.e "toastTextId" and convert the view ID into EditText type and stores into the variable.
        EditText toastText = (EditText) findViewById(R.id.toastTextId);

        // to make a toast, here 3 fields are required i.e,
        // 1. where to show (here we want to show it on main activity so we use MainActivity.this)
        // 2. the text to show (Here we've use the text stored in the variable "toastText" and converted it into the string using a toString function). Also if we want to show our own text after the user's input then we can use + "Text" afterwords.
        // 3. the length of the text.
        // then we use the show() function.
        Toast.makeText(MainActivity.this, toastText.getText().toString() + "Hey There!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
