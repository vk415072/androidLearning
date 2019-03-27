package com.vk.memorableplacesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> places = new ArrayList<String>();
    static ArrayList<LatLng> locations = new ArrayList<LatLng>();
    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.vk.memorableplacesapp", Context.MODE_PRIVATE);

        ArrayList<String> lat = new ArrayList<String>();
        ArrayList<String> lng = new ArrayList<String>();

        //clearing the lat, lng, places and locations.
        places.clear();
        locations.clear();
        lat.clear();
        lng.clear();

        try {
            //fetching the data.
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<>())));
            lat = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString( "lat", ObjectSerializer.serialize(new ArrayList<>())));
            lng = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lng", ObjectSerializer.serialize(new ArrayList<>())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //storing the lat & lng into the locations.
        if(places.size()>0 && lat.size()>0 && lng.size()>0) {
            if (places.size()==lat.size() && places.size()==lng.size()){
                for(int i=0; i<lat.size(); i++){
                    locations.add(new LatLng(Double.parseDouble(lat.get(i)), Double.parseDouble(lng.get(i))));
                }
            }
        }else{
            places.add("Add a new place...");
            locations.add(new LatLng(0, 0));
        }

        //setting arrayAdapter to make a list
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //using intent to communicate with MapsActivity
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                //passing position(contains the list item which is tapped) to MapsActivity.
                intent.putExtra("placeNumber", position);
                startActivity(intent);
            }
        });
    }
}
