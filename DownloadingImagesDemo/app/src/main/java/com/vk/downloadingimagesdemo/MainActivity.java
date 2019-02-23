package com.vk.downloadingimagesdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

        //AsyncTask provides the functionality to run the code in a different threat to the main thread.
        //it takes 3 variables:
        //1.String = the type of variable that we're gong to send to this class (DownloadTask), here we're sending the URL.
        //2.Void = is the name of the method that we may or may not use to show the progress of the task but we're not using suc method so we used void.
        //3.Bitmap = this third variable type is the return type by the DownloadTask i.e, an image so bitmap is used.
    public class ImageDownloader extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                    //this variable is basically a string but it holds only the right format of the URLs.
                URL url = new URL(strings[0]);
                    //here HttpURLConnection here is like a browser. so we're kind of opening a browser here and use it to fetch the content of the url variable.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    //now here we can't download a image one by one just like we did with web content, we've to download it in one go then convert the data we get into bitmap.

                    //to connect our connection
                httpURLConnection.connect();
                    //InputStream is a stream to hold the input of data adn getInputStream() is used to get that data.
                    //this will download the whole stream in one go.
                InputStream inputStream = httpURLConnection.getInputStream();
                    //now to convert the stream into bitmap and returning.
                    //BitmapFactory has a bunch of commands used to manipulate and encode and decode images.
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                //IOException is input/output exception in this case, it could be: if the user has not turned on his internet or we have'nt asked the user for internet permissions.
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    public void downloadImage (View view) throws ExecutionException, InterruptedException {
       // Log.i("Button Tapped","OK");
        ImageDownloader imageDownloader = new ImageDownloader();
        Bitmap bitmap;
            //passing the image URL.
            //using execute() method to execute the ImageDownloader class and get() method to get the return data by that class.
        bitmap = imageDownloader.execute("https://www.dhresource.com/0x0s/f2-albu-g6-M01-6E-5D-rBVaR1s50YSAEubjAAKmSVIjsBo655.jpg/26cm-dragon-ball-z-super-saiyan-son-goku.jpg").get();
            //this is how to set the imageView to our bitmap.
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
    }
}
