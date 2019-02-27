package com.vk.guessthecelebrityapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> celebURLs = new ArrayList<String>();
    ArrayList<String> celebNames = new ArrayList<String>();
    int chosenCeleb = 0;
    ImageView imageView;
    String[] answers = new String[4];
    int locationOfCorrectAnswer = 0;
    int incorrectAnswerLocation = 0;
    Button button0;
    Button button1;
    Button button2;
    Button button3;


    public void button(View view){
            //way to check if button tag is equal to locationOfCorrectAnswer.
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(),"Wrong! It was "+celebNames.get(chosenCeleb),Toast.LENGTH_SHORT).show();
        }
        newQuestion();
    }

        //AsyncTask class to download web content
        //full details in the Downloading Content App.
            //note: this is now the most efficient way to downloading the stuff as it is very slow, there are more libraries that can do this sort of work but we have ot learn this.
    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String string = null;
            URL url;
            HttpURLConnection httpURLConnection = null;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while(data != -1){
                    char current = (char) data;
                    string += current;
                    data = inputStreamReader.read();
                }
                return string;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
        }
    }
        //creating another AsyncTask class to handle the image URL and provide bitmap images.
            //more details are in Downloading Image Demo App.
    public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... strings) {
                URL url = null;
                try {
                    url = new URL(strings[0]);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        public void newQuestion(){
                //creating  random for celebrities.
            Random random = new Random();
                //we need to generate the randoms between 0 and one less the no. of celebURLs.
            chosenCeleb = random.nextInt(celebURLs.size());

            ImageDownloader imageDownloader = new ImageDownloader();
                //executing the class and passing the celebURLs (only the chosenCeleb).
            Bitmap bitmap = null;
            try {
                    //taking the image bitmap from ImageDownloader class and storing into another bitmap variable.
                bitmap = imageDownloader.execute(celebURLs.get(chosenCeleb)).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
                //updating the imageView to the received bitmap.
            imageView.setImageBitmap(bitmap);

                //method to set the names to the buttons with a correct answer and 3 random wrong options.
                //generating and storing random no. between 0-3.
            locationOfCorrectAnswer = random.nextInt(4);
            for(int i=0; i<4; i++){
                if(i==locationOfCorrectAnswer){
                    answers[i] = celebNames.get(chosenCeleb);

                }else {
                    incorrectAnswerLocation = random.nextInt(celebURLs.size());
                    answers[i] = celebNames.get(incorrectAnswerLocation);
                        //to protect getting a correct answer in the wrong answer options.
                    while (incorrectAnswerLocation == chosenCeleb){
                        answers[i] = celebNames.get(incorrectAnswerLocation);
                    }
                }
            }
            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);

        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);

        DownloadTask downloadTask = new DownloadTask();
        String string = null;
        try {
                //for our app we're taking data of celebrities from posh24.se
            string = downloadTask.execute("http://www.posh24.se/kandisar").get();

                //to log the html content got from DoInBackground class.
            //Log.i("Contents of URL",string);

                //METHOD TO SPLIT THE WHOLE HTML
                //we're splitting the html and using the upper part of the split because there are many other images that we don't want.
                //this will split the HTML in two parts and the division point will be "<div class="listedArticles">" as it is the unique string from where we can split between required data and extra data of images.
                    //note that the android studio has automatically skipped the " within the string below.
            String[] strings = string.split("<div class=\"listedArticles\">");

                //NOW WE'VE TO PERFORM SOME "REGEX"
                    //regex (regular expression) is a way to manipulation strings.
                //this method take the whole split html from one string to another string and store it in our variable say an array.
                //creating a Pattern and compiling it from a piece of regex (finding a content between two strings).
                    //in this case we're taking a strings from "img src=\" to "\"" which contains celebrities' image links.
            Pattern pattern = Pattern.compile("img src=\"(.*?)\"");
            Matcher matcher = pattern.matcher(strings[0]);
            while (matcher.find()){
                    //to print the celebrities' image links.
                //System.out.println(matcher.group(1));
                    //adding the image URL to the celebURLs array.
                celebURLs.add(matcher.group(1));
            }
                //similarly doing for to get celebrities' names.
            pattern = Pattern.compile("alt=\"(.*?)\"");
            matcher = pattern.matcher(strings[0]);
            while (matcher.find()){
                    //to print he celebrities' names.
                //System.out.println(matcher.group(1));
                    //adding the names to celebNames array.
                celebNames.add(matcher.group(1));
            }

            newQuestion();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
