package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity3 extends AppCompatActivity {

    VideoView videoView;
    Button logOutBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        videoView = findViewById(R.id.video_view);
        logOutBtn = findViewById(R.id.logOutBtn);
        String videoUri = "android.resource://" + getPackageName() + "/" + R.raw.video;

        VideoPlayback videoPlayback = new VideoPlayback(videoUri);
        new Thread(videoPlayback).start();

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class VideoPlayback implements Runnable{
        String videoUri;

        VideoPlayback(String videoUri){
            this.videoUri = videoUri;
        }

        @Override
        public void run(){
            Uri uri = Uri.parse(videoUri);
            videoView.setVideoURI(uri);
            videoView.start();
        }
    }
}