package com.vk.timestableapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        //it is initialised ono the top as it's being used by two methods below:
    ListView listView;
        //this method is called by the seek bar listener in the onCreate method.
    public void generateTable(int progress){
                //an array is initialized to store the table content.
        final ArrayList<String> tableArray =  new ArrayList<String>();
            //using while loop to put table elements in the array
        int x = 1;
        while(x<=10){
                //also converting the int x and progress into String
            tableArray.add(Integer.toString(x*progress));
            x++;
        }
            //creating an ArrayAdaptor to store the table.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tableArray);
            //showing the table stored in the arrayAdaptor.
        listView.setAdapter(arrayAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            //finding the seekBar and listView
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        listView = (ListView) findViewById(R.id.listView);

        int max = 20;
        int min = 2;

        seekBar.setMax(max);
        seekBar.setMin(min);
        generateTable(min);
        seekBar.setProgress(2);
            //creating a seekBar listener method.
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               generateTable(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
