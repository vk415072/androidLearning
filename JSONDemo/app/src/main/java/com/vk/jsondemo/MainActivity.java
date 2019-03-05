package com.vk.jsondemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    //this is the JSON data that we'll use to play with:
    /*
    {
   "coord":{
      "lon":-0.13,
      "lat":51.51
   },
   "weather":[
      {
         "id":300,
         "main":"Drizzle",
         "description":"light intensity drizzle",
         "icon":"09d"
      }
   ],
   "base":"stations",
   "main":{
      "temp":280.32,
      "pressure":1012,
      "humidity":81,
      "temp_min":279.15,
      "temp_max":281.15
   },
   "visibility":10000,
   "wind":{
      "speed":4.1,
      "deg":80
   },
   "clouds":{
      "all":90
   },
   "dt":1485789600,
   "sys":{
      "type":1,
      "id":5091,
      "message":0.0103,
      "country":"GB",
      "sunrise":1485762037,
      "sunset":1485794875
   },
   "id":2643743,
   "name":"London",
   "cod":200
}
     */

        //creating AsyncTask for the weather data to be downloaded in  the background.
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
            //this method is called once the doInBackground method is completed.
            //we can work with the result in onPostExecution rather than passing it to the onCreate method an let it work with it.
            //diff. b/w both is, we can't interact with the UI in the doInBackground (such as updating a label within this method wont work) while we can in onPostExecution.
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
                //s = {"coord":{"lon":-0.13,"lat":51.51},"weather":[{"id":300,"main":"Drizzle","description":"light intensity drizzle","icon":"09d"}],"base":"stations","main":{"temp":280.32,"pressure":1012,"humidity":81,"temp_min":279.15,"temp_max":281.15},"visibility":10000,"wind":{"speed":4.1,"deg":80},"clouds":{"all":90},"dt":1485789600,"sys":{"type":1,"id":5091,"message":0.0103,"country":"GB","sunrise":1485762037,"sunset":1485794875},"id":2643743,"name":"London","cod":200}
                //just logging whole web content.
                //s is the string which contains the return data after doInBackground().
            //Log.i("JSON", s);

                //extracting only the required data with JSON.
            try {
                    //step 1 is to convert the string s into JSON data.
                    //using JSONObject.
                JSONObject jsonObject = new JSONObject(s);
                    //extracting and storing...
                String extractedResult = jsonObject.getString("weather");
                    //extractedResult = [{"id":300,"main":"Drizzle","description":"light intensity drizzle","icon":"09d"}]
                //Log.i("Weather", extractedResult);
                    //converting the extractedArray string into JSONArray.
                JSONArray jsonArray = new JSONArray(extractedResult);
                for(int i=0; i<jsonArray.length(); i++){
                        //need to convert each part of the array into separate JSONObject.
                    JSONObject jsonObjectPart = jsonArray.getJSONObject(i);
                        //now we're only interested in the "main" and "Description" within the weather (extractedResult) string.
                    Log.i("Main", jsonObjectPart.getString("main"));
                    Log.i("Description", jsonObjectPart.getString("description"));
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

            //passing web to the DownloadTask() method.
        DownloadTask downloadTask = new DownloadTask();
        try {
            downloadTask.execute("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
