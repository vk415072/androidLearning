package com.vk.taptochangeimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public void catButtonFunction(View view){

        //this is only for logs
        Log.i("Test:", "New cat button pressed");

        //here a variable named "image" is used of type "ImageView" to store a View with an ID i.e catId
        //also it is converted into ImageView type.
        ImageView image = (ImageView) findViewById(R.id.catId);

        //here we have replace the previous stored image in "image" variable with the cat2 image stored in the drawable folder.
        image.setImageResource(R.drawable.cat2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
