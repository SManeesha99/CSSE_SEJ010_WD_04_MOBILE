package com.example.csse_sej010_wd_04_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Timer;
import java.util.TimerTask;


public class Intro extends AppCompatActivity {

    Timer timer;

    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Intro.this,Signin.class);
                startActivity(intent);
                finish();
            }
        },10000);


        lottieAnimationView = findViewById(R.id.lottie);

        lottieAnimationView.animate().setDuration(1000).setStartDelay(10000);

    }
}