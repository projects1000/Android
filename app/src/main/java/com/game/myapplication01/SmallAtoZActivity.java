package com.game.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SmallAtoZActivity extends AppCompatActivity {
    private String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_a_to_z);

        TextView letterText = findViewById(R.id.letterText);
        Button nextButton = findViewById(R.id.nextButton);

        letterText.setText(letters[currentIndex]);
        MusicManager.pauseMusic();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex < letters.length) {
                    letterText.setText(letters[currentIndex]);
                } else {
                    // Finish the game or go to the completion activity
                    Intent intent = new Intent(SmallAtoZActivity.this, CompletionActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
