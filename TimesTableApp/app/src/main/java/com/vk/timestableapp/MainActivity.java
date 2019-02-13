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

    ListView listView;

    public void generateTable(int progress){
        final ArrayList<String> tableArray =  new ArrayList<String>();
        int x = 1;
        while(x<=10){
            tableArray.add(Integer.toString(x*progress));
            x++;
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tableArray);
        listView.setAdapter(arrayAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        listView = (ListView) findViewById(R.id.listView);
        int max = 20;
        int min = 2;
        seekBar.setMax(max);
        seekBar.setMin(min);
        generateTable(min);
        seekBar.setProgress(2);
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
