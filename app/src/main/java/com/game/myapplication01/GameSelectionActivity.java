package com.game.myapplication01;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class GameSelectionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        Button btnGame1 = findViewById(R.id.btnGame1);
        Button btnGame2 = findViewById(R.id.btnGame2);
        Button btnGame3 = findViewById(R.id.btnGame3);
        Button btnGame4 = findViewById(R.id.btnGame4);

        btnGame1.setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, BallSortingGameActivity.class));
        });
        btnGame2.setOnClickListener(v -> Toast.makeText(this, "Game 2 clicked", Toast.LENGTH_SHORT).show());
        btnGame3.setOnClickListener(v -> Toast.makeText(this, "Game 3 clicked", Toast.LENGTH_SHORT).show());
        btnGame4.setOnClickListener(v -> Toast.makeText(this, "Game 4 clicked", Toast.LENGTH_SHORT).show());
    }
}
