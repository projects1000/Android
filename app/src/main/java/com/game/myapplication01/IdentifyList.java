package com.game.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;

public class IdentifyList extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idenify_list);
        generateButtons();
        MusicManager.startMusic(this);
    }

    private void generateButtons() {
        Button btnAnimals = findViewById(R.id.btnAnimals);
        btnAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set full-screen mode
                // Play animal sound and navigate to IdentifyObjectsActivity
                playSound(R.raw.clickanimal);
                Intent intent = new Intent(IdentifyList.this, IdentifyObjectsActivity.class);
                startActivity(intent);
            }
        });

        Button btnBirds = findViewById(R.id.btnBirds);
        btnBirds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play bird sound and navigate to IdentifyBirdsActivity
                playSound(R.raw.clickbirds);
                Intent intent = new Intent(IdentifyList.this, IdentifyBirdsActivity.class);
                startActivity(intent);
            }
        });

        Button btnFlower = findViewById(R.id.btnFlowers);
        btnFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.clickfruits);
                Intent intent = new Intent(IdentifyList.this, IdentifyFlowersActivity.class);
                startActivity(intent);
            }
        });

        Button btnVegetables = findViewById(R.id.btnVegetables);
        btnVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.clickfruits);
                Intent intent = new Intent(IdentifyList.this, IdentifyVegetablesActivity.class);
                startActivity(intent);
            }
        });

//        Button btnGames = findViewById(R.id.btnGames);
//        btnGames.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(IdentifyList.this, IdentifyGamesActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void playSound(int soundResourceId) {
        // Release any previous MediaPlayer instance
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        // Initialize the MediaPlayer with the selected sound
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();

        // Release resources when playback is complete
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Code to execute after the delay
            MusicManager.startMusic(this);
        }, 1000); // 500 ms delay (0.5 seconds)
    }


    @Override
    protected void onPause() {
        super.onPause();
        // Pause the music when the activity is paused
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.pause();
//        }
        MusicManager.pauseMusic(); // Pause music when app goes to background
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//        }
        MusicManager.stopMusic(); // Stop the music only when the app closes completely
    }
}
