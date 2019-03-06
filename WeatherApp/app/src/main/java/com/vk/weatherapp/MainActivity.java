package com.vk.weatherapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    public void buttonFunction(View view){
            //If the city name is of two words then the space b/w then could create an error, to handle this:
            //we'll encode the string into URL type. Here "UTF-8" is the user format.
            //fro eg: we input "San Francisco", It'll convert it into "San%20Francisco".
        try {
            String encodedCityName = URLEncoder.encode(editText.getText().toString(), "UTF-8");
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute("https://openweathermap.org/data/2.5/weather?q="+encodedCityName+"&appid=b6907d289e10d714a6e88b30761fae22");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
        }


            //to close the keyboard as soon as the button is pressed:
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            //0 is here to specify that we are not specifying more parameters as this requires more then 2 parameters.
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();
                }
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
            }
            return null;
        }

            //complete guid of OnPostExecute() can be found in the JSON Demo App.
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String message = "";
            try {
                JSONObject jsonObject = new JSONObject(s);
                String extractedResult = jsonObject.getString("weather");
                Log.i("City",extractedResult);
                JSONArray jsonArray = new JSONArray(extractedResult);
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObjectPart = jsonArray.getJSONObject(i);

                    String main = jsonObjectPart.getString("main");
                    String description = jsonObjectPart.getString("description");
                    if(main != "" && description != ""){
                            //combining both strings to one.
                        message = "Weather: " +main+ "\r\nDescription: "+description;
                    }
                }
                if(message != ""){
                    textView.setText(message);
                    textView.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);
    }
}
