package com.vk.multipleactivitydemo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static java.util.Arrays.asList;

//HELP CAN BE FIND IN THE LIST VIEW APP & MULTIPLE ACTIVITY DEMO APP.

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        final ArrayList<String> arrayList = new ArrayList<String>(asList("Shehzad", "Ritik", "Vishal", "Marta", "Bhanu", "Priyanka", "Shivani", "Shivam", "Piyush", "Amit", "Ravi", "Divyank", "Vikash"));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("name",arrayAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
