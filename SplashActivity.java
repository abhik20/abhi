package com.example.mahe.numberplate1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class SplashActivity extends AppCompatActivity {

    VideoView videoView;
    ProgressBar progressBar;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView(R.layout.activity_splash);

       // getSupportActionBar().hide();

        videoView = (VideoView) findViewById(R.id.videoView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        txt = (TextView) findViewById(R.id.txt1) ;

        progressBar.setVisibility(progressBar.VISIBLE);


        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.car );

        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                if (isFinishing())
                    return;

                startActivity(new Intent(SplashActivity.this, RoleActivity.class));
                finish();

            }
        });

        videoView.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SplashActivity.this.startActivity(new Intent(SplashActivity.this, RoleActivity.class));
                SplashActivity.this.finish();
            }
        },3000);

    }
}
