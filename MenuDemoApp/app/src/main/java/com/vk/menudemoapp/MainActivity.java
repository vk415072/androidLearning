package com.vk.menudemoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    //we have to create this method which will run when the option menu is created.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //here we want to link the options menu in the main_menu.xml file.
        MenuInflater menuInflater = getMenuInflater();
        //inflating the menu and accessing our menu.
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //this is to be created when we tap on the options item.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        //running switch statement to take action on clicking the options.
        switch (item.getItemId()){
            case R.id.settings:
                Log.i("Menu item selected", "Settings");
                return true;
            case R.id.help:
                Log.i("Menu item selected", "Help");
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
