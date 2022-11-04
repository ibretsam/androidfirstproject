package com.example.androidfirstproject.Views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidfirstproject.R;

public class MainActivity extends AppCompatActivity {
TextView wel, learning;
private static int Splash_time = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ánh xạ
        wel = (TextView) findViewById(R.id.textview1);
        learning = (TextView) findViewById(R.id.textview2);
//        ImageView ivLogo = (ImageView) findViewById(R.id.ivLogo);
//        Glide.with(this).load(R.mipmap.center).into(ivLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashintent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(splashintent);
                finish();
            }
        },Splash_time);
        Animation myanimation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.animation2);
        wel.startAnimation(myanimation);

        Animation myanimation2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.animation1);
        learning.startAnimation(myanimation2);
    }
}