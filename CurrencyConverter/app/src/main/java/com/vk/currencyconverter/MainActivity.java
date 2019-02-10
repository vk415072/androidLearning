package com.vk.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void convertButtonFunction(View view){

        // here we've created a variable "inputText" of type "EditText" which collect the data from the ID i.e "editTextCurrencyID" and convert the view ID into EditText type and stores into the variable.
        EditText inputText = (EditText) findViewById(R.id.editTextCurrencyID);

        // To convert the String into Double we use Double.parseDouble() function
        //and in the parameters of the parse function we've input the path of the EditText type variable i.e, "inputText"
        //also converted that variable data into string using .toString() method.
        Double inputNum = Double.parseDouble(inputText.getText().toString());

        //this is the multiplication of the input data by 80 (1 EUR = 80 INR).
        inputNum = inputNum * 80;

        // to make a toast, here 3 fields are required i.e,
        // 1. where to show (here we want to show it on main activity so we use MainActivity.this)
        // 2. the text to show i.e, here i want ot show the double inputNum (also has to convert back to string using .toString())
        // 3. the length of the text.
        // then we use the show() function.
        Toast.makeText(MainActivity.this, "INR  " + inputNum.toString(),Toast.LENGTH_LONG).show();

        // here the Log.i function is used to show the info to the logcat terminal which required two fields
        // i.e Tag & the data to show
        // here the data is taken from the inputText variable created above.
        //and in second Log command we take the data after the conversion.
        Log.i("Test","User input: "+ inputText.getText().toString());
        Log.i("Test","Converted Output: " + inputNum.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
