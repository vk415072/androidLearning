package com.vk.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView;

    public void buttonFunction(View view){
        Log.i("TEXT", editText.getText().toString());
        DownloadTask downloadTask = new DownloadTask();
        try {
            downloadTask.execute("https://openweathermap.org/data/2.5/weather?q="+editText.getText().toString()+"&appid=b6907d289e10d714a6e88b30761fae22").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String extractedResult = jsonObject.getString("weather");
                Log.i("City",s);
                JSONArray jsonArray = new JSONArray(extractedResult);
                for(int i=0; i<jsonArray.length(); i++){
                    JSONObject jasonObjectPart = jsonArray.getJSONObject(i);
                    Log.i("Main", jsonObject.getString("main"));
                    Log.i("Description", jsonObject.getString("description"));
                    //textView.setText(jsonObject.getString("main"));
                    //textView.append(jsonObject.getString("description"));
                    //textView.setVisibility(View.VISIBLE);
                    //Log.i("TEXTVIEW", textView.getText().toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
