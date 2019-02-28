package com.vk.guessthemarvels;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

    ArrayList<String> marvelName = new ArrayList<String>();
    ArrayList<String> marvelImg = new ArrayList<String>();

    ImageView imageView;
    Button button0;
    Button button1;
    Button button2;
    Button button3;

    int choosedMarvel = 0;
    int correctAnsLocation = 0;
    int incorrectAnsLocation = 0;

    String[] answers = new String[4];


    public void buttonFunction(View view){
        if(view.getTag().toString().equals(Integer.toString(correctAnsLocation))){
            Toast.makeText(getApplicationContext(),"Correct!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Wrong! It was "+marvelName.get(choosedMarvel), Toast.LENGTH_SHORT).show();
        }
        newQuestion();
    }


    public void newQuestion(){

        try {
            Random random = new Random();
            choosedMarvel = random.nextInt(marvelImg.size());

                //setting image.
            ImageDownloader imageDownloader = new ImageDownloader();
            Bitmap bitmap = imageDownloader.execute(marvelImg.get(choosedMarvel)).get();
            imageView.setImageBitmap(bitmap);

                //setting button texts.
            correctAnsLocation = random.nextInt(4);
            for (int i=0; i<4; i++){
                if(i==correctAnsLocation){
                    answers[i] = marvelName.get(choosedMarvel);
                }else{
                    incorrectAnsLocation = random.nextInt(marvelImg.size());
                    while (incorrectAnsLocation == choosedMarvel){
                        incorrectAnsLocation = random.nextInt(marvelImg.size());
                    }
                    answers[i] = marvelName.get(incorrectAnsLocation);
                }
            }
            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);
            button3.setText(answers[3]);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try{
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
    }

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
                while (data != -1){
                    char current = (char) data;
                    string += current;
                    data = inputStreamReader.read();
                }
                return string;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
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

        //ConstraintLayout constraintLayout = findViewById(R.id.constrainLayout);



        DownloadTask downloadTask = new DownloadTask();
        try {
            String resultString = downloadTask.execute("https://comicvine.gamespot.com/profile/noahmaximillion/lists/top-100-marvel-superheroes/48749/").get();
            //Log.i("Marvel URL",string);

            String[] splittedString = null;
            splittedString = resultString.split("js-taboola-module");

                //getting names
            Pattern pattern = Pattern.compile("<h3>(.*?)<");
            Matcher matcher = pattern.matcher(splittedString[0]);
            while(matcher.find()){
                //System.out.println(matcher.group(1));
                marvelName.add(matcher.group(1));
            }

                //getting images
            pattern  = Pattern.compile("<img src=\"(.*?)\"");
            matcher  = pattern.matcher(splittedString[0]);
            while(matcher.find()){
                //System.out.println(matcher.group(1));
                marvelImg.add(matcher.group(1));
            }

            newQuestion();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
