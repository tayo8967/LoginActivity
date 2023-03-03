package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity3 extends AppCompatActivity {

    VideoView videoView;
    Button logOutBtn, stopResumeBtn;
    volatile boolean isPlaying = true;
    Handler mainHandler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        videoView = findViewById(R.id.video_view);
        logOutBtn = findViewById(R.id.logOutBtn);
        stopResumeBtn = findViewById(R.id.stopResumeBtn);
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

        stopResumeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StopResume stopResume = new StopResume();
                new Thread(stopResume).start();
            }
        });
    }

    class StopResume implements Runnable{
        @Override
        public void run() {
            if(isPlaying){
                videoView.pause();
                isPlaying = false;
                mainHandler.post(new Runnable(){
                    @Override
                    public void run(){
                        stopResumeBtn.setText("Resume");
                    }
                });
            }
            else{
                videoView.start();
                isPlaying = true;
                mainHandler.post(new Runnable(){
                   @Override
                   public void run(){
                       stopResumeBtn.setText("Stop");
                   }
                });
            }
        }
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