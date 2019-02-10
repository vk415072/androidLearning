package com.vk.videodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          //as simple we've created a videoView variable of type VideoView and stored a videoView.
        VideoView videoView = (VideoView) findViewById(R.id.videoView);

          //now we've set the video path with this technique:
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.vid2);

          //this is the way to add media controls to the video.
        MediaController mediaController = new MediaController(this);

          //to attach that media controller to the video view:
        mediaController.setAnchorView(videoView);
          //this is for visa versa.
        videoView.setMediaController(mediaController);

          //to start the video
        videoView.start();
    }
}
