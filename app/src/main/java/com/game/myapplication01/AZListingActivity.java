package com.game.myapplication01;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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
        setContentView(R.layout.activity_list);
        generateButtons();
    }
    private void generateButtons() {

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

}
