package com.vk.notesapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    //arrayList to store notes.
    static ArrayList<String> notes = new ArrayList<String>();
    //arrayAdapter to handle the listView.
    static ArrayAdapter arrayAdapter;
    //sharedPreferences to store the data.
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finding listView
        ListView listView = findViewById(R.id.listView);
        //initiating sharedPreferences.
        sharedPreferences = getApplicationContext().getSharedPreferences("com.vk.notesapp", Context.MODE_PRIVATE);

        //fetching data stored in the app and storing it in a HashSet
        //also setting default value is null.
        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        //if the app doesn't have any data then add an example note to the listView.
        if(set == null){
            notes.add("Example Notes");
        }else {
            //else create a new ArrayList and pass the stored data i.e, now stored in the "set" as a hashSet.
            notes = new ArrayList<>(set);
        }

        //creating a list using arrayAdapter.
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter);

        //creating on list item click listener and moving to NoteEditorActivity.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //creating intent to contact NoteEditorActivity.
                Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
                //sending the position as a note ID to the NoteEditorActivity.
                intent.putExtra("noteId", position);
                //starting that activity with out intent.
                startActivity(intent);

            }
        });

        //DELETING AN ITEM ON LONG PRESS CODE:
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //int position contains the item's position that is long pressed.
                //creating an alert dialog.
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete a note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //deleting the note of that position.
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                //also deleting the data from the database:
                                sharedPreferences = getApplicationContext().getSharedPreferences("com.vk.notesapp", Context.MODE_PRIVATE);
                                //creating a new HashSet to store the notes data.
                                HashSet<String> set = new HashSet<>(MainActivity.notes);
                                //storing that data to database.
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });
    }

    // OPTIONS MEU CODE:

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menuInflater to link the add_note_menu.xml
        MenuInflater menuInflater = getMenuInflater();
        //inflating the menu.
        menuInflater.inflate(R.menu.add_note_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        //if the IDs of menu items in the add_note_menu.xml file and item (MenuItem) variable then,
        //create an intent to contact NoteEditorActivity.
        if(item.getItemId() == R.id.add_note){
            Intent intent = new Intent(getApplicationContext(), NoteEditorActivity.class);
            startActivity(intent);

            return true;
        }else {
            return false;
        }
    }
}
