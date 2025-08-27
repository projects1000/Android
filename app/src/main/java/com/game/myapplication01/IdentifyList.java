package com.game.myapplication01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class IdentifyList extends AppCompatActivity {

    private TextToSpeech textToSpeech;

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

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int langResult = textToSpeech.setLanguage(Locale.US);
                if (langResult == TextToSpeech.LANG_MISSING_DATA ||
                        langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle language not supported
                }
            }
        });
    }

    private void generateButtons() {
        Button btnAnimals = findViewById(R.id.btnAnimals);
        btnAnimals.setOnClickListener(v -> {
            // Generate voice for button click
            generateVoice("Click the animals");
            Intent intent = new Intent(IdentifyList.this, IdentifyObjectsActivity.class);
            startActivity(intent);
        });

        Button btnBirds = findViewById(R.id.btnBirds);
        btnBirds.setOnClickListener(v -> {
            // Generate voice for button click
            generateVoice("Click the birds");
            Intent intent = new Intent(IdentifyList.this, IdentifyBirdsActivity.class);
            startActivity(intent);
        });

        Button btnFlower = findViewById(R.id.btnFlowers);
        btnFlower.setOnClickListener(v -> {
            // Generate voice for button click
            generateVoice("Click the fruits and flowers");
            Intent intent = new Intent(IdentifyList.this, IdentifyFlowersActivity.class);
            startActivity(intent);
        });

        Button btnVegetables = findViewById(R.id.btnVegetables);
        btnVegetables.setOnClickListener(v -> {
            generateVoice("Click the vegetables");
            Intent intent = new Intent(IdentifyList.this, IdentifyVegetablesActivity.class);
            startActivity(intent);
        });

        Button btnVehicles = findViewById(R.id.btnVehicles);
        btnVehicles.setOnClickListener(v -> {
            generateVoice("Click the vehicles");
            Intent intent = new Intent(IdentifyList.this, IdentifyVehiclesActivity.class);
            startActivity(intent);
        });

        Button btnNonLivingObjects = findViewById(R.id.btnNonLivingObjects);
        btnNonLivingObjects.setOnClickListener(v -> {
            generateVoice("Click the non-living objects");
            Intent intent = new Intent(IdentifyList.this, IdentifyNonlivingObjectsActivity.class);
            startActivity(intent);
        });

        Button btnGames = findViewById(R.id.btnGames);
        btnGames.setOnClickListener(v -> {
            generateVoice("Click the games");
            Intent intent = new Intent(IdentifyList.this, GameSelectionActivity.class);
            startActivity(intent);
        });

        Button btnMusicalInstruments = findViewById(R.id.btnMusicalInstruments);
        btnMusicalInstruments.setOnClickListener(v -> {
            generateVoice("Click the musical instruments");
            Intent intent = new Intent(IdentifyList.this, IdentifyMusicalInstrumentsActivity.class);
            startActivity(intent);
        });

        Button btnCommunity = findViewById(R.id.btnCommunity);
        btnCommunity.setOnClickListener(v -> {
            generateVoice("Click the community");
            Intent intent = new Intent(IdentifyList.this, IdentifyCommunityActivity.class);
            startActivity(intent);
        });
    }

    private void generateVoice(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Code to execute after the delay
            MusicManager.startMusic(this);
        }, 1000); // 1-second delay (you can adjust as needed)
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicManager.pauseMusic(); // Pause music when app goes to background
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        MusicManager.stopMusic(); // Stop the music when the app is destroyed
    }
}
