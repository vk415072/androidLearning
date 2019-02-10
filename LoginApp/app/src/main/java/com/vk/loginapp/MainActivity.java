package com.vk.loginapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void loginFunction(View view){

        EditText username = (EditText) findViewById(R.id.usernameFieldId);
        EditText password = (EditText) findViewById(R.id.passwordFieldId);
        Log.i("Username:", username.getText().toString() );
        Log.i("Password:", password.getText().toString() );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
