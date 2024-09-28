package com.game.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);  // Ensure this is activity_home.xml

        Button btnStartGame = findViewById(R.id.btnLearnCapital);
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the A2Z game activity
                Intent intent = new Intent(HomeActivity.this, A2ZGameActivity.class);
                startActivity(intent);
            }
        });


        Button btnLearnSmall = findViewById(R.id.btnLearnSmall);
        btnLearnSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Small A to Z game activity
                Intent intent = new Intent(HomeActivity.this, SmallLetterA2ZGameActivity.class);
                startActivity(intent);
            }
        });

    }
}
