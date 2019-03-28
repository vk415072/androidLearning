package com.vk.alertdialoguedemoapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //we use AlertDialog to build a alert dialog.
        //we're using a large srting so we have divided it into several bullet points.
        new AlertDialog.Builder(this)
                //setting the triangular alert icon.
                .setIcon(android.R.drawable.ic_dialog_alert)
                //setting title.
                .setTitle("Are you sure?")
                //setting detailed message.
                .setMessage("Do you definitely want to do this?")
                //setting positive button and onClick listener.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Its done!", Toast.LENGTH_SHORT).show();
                    }
                })
                //setting negative button.
                .setNegativeButton("No", null)
                //showing the dialog.
                .show();
    }
}