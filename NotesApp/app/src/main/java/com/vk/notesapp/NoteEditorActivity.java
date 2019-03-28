package com.vk.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        //finding editText
        EditText noteEditorTextView = findViewById(R.id.notesEditorText);


        //creating an intent to access the data from another activity.
        Intent intent = getIntent();
        //getting the value of the position of the item in the list.
        //setting the default value to -1 as it can be 0 if there is no notes in the list and can't be -1 at any case.
        position = intent.getIntExtra("noteId", -1);
        //if the value is not -1 then setting that position's data to the editText.
        if(position != -1){
            noteEditorTextView.setText(MainActivity.notes.get(position));
        }else{
            //else the user has clicked on the 'Add note' option from the menu so add a new empty note.
            MainActivity.notes.add("");
            //as a new note has added so the position must be changed to the size of items in the notes (ArrayList) - 1.
            position = MainActivity.notes.size() -1;
            //also updating the arrayAdapter.
            MainActivity.arrayAdapter.notifyDataSetChanged();
        }


        //this method is used if we're modifying the editText and want some work done
        noteEditorTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //changing the CharSequence 's' into string and storing the data into the ArrayList named 'notes' in the mainActivity.
                //'s' contains the editTest data.
                MainActivity.notes.set(position, String.valueOf(s));
                //notifying the arrayAdaptor about changes.
                MainActivity.arrayAdapter.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.vk.notesapp", Context.MODE_PRIVATE);
                //creating a new HashSet to store the notes data.
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                //storing that data to database.
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
