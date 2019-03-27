package com.vk.sharedprefrences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //here SharedPreference is a class and here in the getSharedPreferences method we are passing the name of the package and context
        //i.e, MODE_PRIVATE which means that we only want the data to be shared within our app.
        //other feature could be if we have a series of apps then we can use it to be public. so that other apps can share the data of this app.
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.vk.sharedprefrences", Context.MODE_PRIVATE);

//        //putting info into the sharedPreferences.
//        //trying to store a string.
//        //in the parameters, the "username" is the title of our string that stores the strings value.
//        //and the string is "Vivek"
//        sharedPreferences.edit().putString("username", "Vivek").apply();
//
//        //getting info from the sharedPreferences.
//        //asking for string, giving the title string also have to provide the default value as if it cold'nt find any value.
//        String username = sharedPreferences.getString("username", "");
//
//        Log.i("username", username);

        ArrayList<String> friends = new ArrayList<String>();
        friends.add("Ritik");
        friends.add("Shivani");
        friends.add("Shehzad");
        friends.add("Shubham");

        //now to store the arrayList we have to serialize it to a string and to do so,
        //Rob has created a java class called ObjectSerialized.
        //This class can be found at www.androiddevcourse.com/ObjectSerializer.html
        //I've to create a new java class to add the above code.
        //using that class to serialize the string, friends:
        try {
            sharedPreferences.edit().putString("friends", ObjectSerializer.serialize(friends)).apply();

            Log.i("Friends", ObjectSerializer.serialize(friends));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creating another ArrayList to store the friends when we fetch the stored data.
        //we'll use the deserializer of ObjectSerializer class to turn the string back into arrayList.
        ArrayList<String> newFriends = new ArrayList<String>();
        try {
            //getting data through sharedPreferences by deSerializing it.
            //if we pass an empty string as the default value then the serializer would break so we ask the ObjectSerializer class to give a empty string.
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friends",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("New Friends", newFriends.toString());

    }
}
