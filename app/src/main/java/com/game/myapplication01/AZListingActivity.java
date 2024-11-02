package com.game.myapplication01;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AZListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set full-screen mode
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_list);
        generateButtons();
        MusicManager.startMusic(this);
    }
    private void generateButtons() {

        Button identifyObjectsLetterButton = findViewById(R.id.btnIdentifyObjects);
        identifyObjectsLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AZListingActivity.this, IdentifyList.class);
                startActivity(intent);
            }
        });

        Button oneto30Button = findViewById(R.id.btnLearn1to30);
        oneto30Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AZListingActivity.this, OneTo30Activity.class);
                startActivity(intent);
            }
        });

        Button capitalLetterButton = findViewById(R.id.btnLearnCapital);
        capitalLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AZListingActivity.this, A2ZGameActivity.class);
                startActivity(intent);
            }
        });

        Button smallLetterButton = findViewById(R.id.btnLearnSmall);
        smallLetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AZListingActivity.this, SmallLetterA2ZGameActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
//        if (isFinishing()) { // Check if the activity is closing
//            MusicManager.pauseMusic();
//        }
        MusicManager.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!MusicManager.isPlaying()) {  // Only start if music is not already playing
                MusicManager.startMusic(this);
            }
        }, 1000); // Adjust delay as needed, or remove if not necessary
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicManager.stopMusic(); // Stop the music only when the app closes completely
    }
}
