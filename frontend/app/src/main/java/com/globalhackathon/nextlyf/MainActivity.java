package com.globalhackathon.nextlyf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        View splashImage = findViewById(R.id.splash_logo);
        View splashText = findViewById(R.id.splash_text);

        // Create scale animations for image and text
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(splashImage, "scaleX", 1f, 1.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(splashImage, "scaleY", 1f, 1.5f);
        ObjectAnimator textScaleX = ObjectAnimator.ofFloat(splashText, "scaleX", 1f, 1.2f);
        ObjectAnimator textScaleY = ObjectAnimator.ofFloat(splashText, "scaleY", 1f, 1.0f);

        // Set duration and interpolator
        scaleX.setDuration(3000);
        scaleY.setDuration(3000);
        textScaleX.setDuration(3000);
        textScaleY.setDuration(3000);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
        textScaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        textScaleY.setInterpolator(new AccelerateDecelerateInterpolator());

        // Start animations
        scaleX.start();
        scaleY.start();
        textScaleX.start();
        textScaleY.start();

        // Listener to transition to the next activity after animation ends
        scaleY.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // Transition to the next activity
//                startActivity(new Intent(MainActivity.this, OnboardingScreen.class));
//                finish();
                int SPLASH_DISPLAY_LENGTH = 3000;

                new Handler().postDelayed(new Runnable() {
                 @Override
                 public void run() {
                        Intent mainIntent = new Intent(MainActivity.this, OnboardingScreen.class);
                        MainActivity.this.startActivity(mainIntent);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        MainActivity.this.finish();
                 }
                }, SPLASH_DISPLAY_LENGTH);
            }
        });

//        int SPLASH_DISPLAY_LENGTH = 3000; // Splash screen delay time in milliseconds
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent mainIntent = new Intent(MainActivity.this, OnboardingScreen.class);
//                MainActivity.this.startActivity(mainIntent);
//                MainActivity.this.finish();
//            }
//        }, SPLASH_DISPLAY_LENGTH);
    }
}