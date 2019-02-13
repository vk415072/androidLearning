package com.vk.listviewapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
            //we can also use like: newArrayList<String>(asList("Manju","Rajveer","Vivek","Shivani"));
        final ArrayList<String> myFamily = new ArrayList<String>();
        myFamily.add("Manju");
        myFamily.add("Rajveer");
        myFamily.add("Vivek");
        myFamily.add("Shivani");
            //now we use an ArrayAdaptor that take the info from the array and put it into thr ListView.
            //hence, here the variable arrayAdaptor is storing the myFamily array also turning it into the "simple_list_item_1" (a simple way to create a list layout)
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, myFamily);
            //but this ArrayAdaptor still don't know about the listView, it only knows about the array so we have to set the ArrayAdaptor to the listView
        listView.setAdapter(arrayAdapter);
            //now to on click the particular item on the list, we use this method:
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                //this method id automatically created as soon as we pass the new "AdapterView.OnItemClickListener()" above.
                //this required the following parameters:
                    //AdapterView: works for all items in the list.
                    //View is for individual item on which we click.
                    //int is the item on which we have clicked.
                    //long is similar to int.
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //so here we've passed the position int so it will check the position pressed on the list and take that position item from the array.
                Log.i("Peron Selected",myFamily.get(position));
                    //or make a toast
                Toast.makeText(getApplicationContext(),"hey "+myFamily.get(position),Toast.LENGTH_LONG).show();
            }
        });

    }
}
