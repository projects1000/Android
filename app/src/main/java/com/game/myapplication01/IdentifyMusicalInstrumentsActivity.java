package com.game.myapplication01;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class IdentifyMusicalInstrumentsActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private MediaPlayer mediaPlayer;
    private boolean isSoundPlaying = false;
    private boolean isDialogOpen = false; // Flag to track if the dialog is open

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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        if (screenWidth <= 720) { // Small screens
            setContentView(R.layout.musicalinstrument_small);
        } else { // Large screens
            setContentView(R.layout.musicalinstrument_large);
        }

        // Initialize Text-to-Speech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int languageResult = textToSpeech.setLanguage(Locale.US);
                if (languageResult == TextToSpeech.LANG_MISSING_DATA ||
                        languageResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle language not supported
                }
            }
        });

        // Set up image click handlers
        setupImageClickListeners();
    }

    private void setupImageClickListeners() {
        // Image IDs from the XML layout
        int[] imageIds = {
                R.id.guitarImage, R.id.violinImage, R.id.djembeImage, R.id.harpImage,
                R.id.maracasImage, R.id.saxophoneImage, R.id.glockenspielImage, R.id.bugleImage,
                R.id.fluteImage, R.id.harmonicaImage, R.id.harmoniumImage, R.id.drumImage,
                R.id.tablaImage, R.id.congasImage, R.id.accordionImage, R.id.bassoonImage,
                R.id.pianoImage, R.id.keytarImage, R.id.serpentImage, R.id.bagpipesImage,
                R.id.oboeImage
        };

        // Titles corresponding to each image
        String[] titles = {
                "Guitar", "Violin", "Djembe", "Harp", "Maracas", "Saxophone", "Glockenspiel", "Bugle",
                "Flute", "Harmonica", "Harmonium", "Drum", "Tabla", "Congas", "Accordion", "Bassoon",
                "Piano", "Keytar", "Serpent", "Bagpipes", "Oboe"
        };

        // Set listeners for each image
        for (int i = 0; i < imageIds.length; i++) {
            int index = i; // Capture index for lambda
            ImageView imageView = findViewById(imageIds[i]);
            imageView.setOnClickListener(v -> handleItemClick(imageView, titles[index]));
        }
    }

    private void handleItemClick(ImageView imageView, String instrumentName) {
        if (!isDialogOpen) {
            isDialogOpen = true;
            AlertDialog dialog = showMagnifiedImage(imageView, instrumentName, "");
            speakInstrumentName(instrumentName);

            // Delay the sound playback by 1 second (1000 milliseconds)
            new Handler().postDelayed(() -> playInstrumentSound(instrumentName, dialog), 500);
        }
    }

    private void speakInstrumentName(String instrumentName) {
        if (textToSpeech != null) {
            textToSpeech.speak(instrumentName, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    private void playInstrumentSound(String instrumentName, AlertDialog dialog) {
        if (!isSoundPlaying) {
            isSoundPlaying = true;

            // Release any previous MediaPlayer if necessary
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            int soundResource = getSoundResourceForInstrument(instrumentName);
            if (soundResource != 0) {
                mediaPlayer = MediaPlayer.create(this, soundResource);
                mediaPlayer.setOnCompletionListener(mp -> {
                    isSoundPlaying = false;
                    dialog.dismiss(); // Dismiss the dialog after sound is played
                    isDialogOpen = false;
                });
                mediaPlayer.start();
            } else {
                isSoundPlaying = false;
                dialog.dismiss(); // Dismiss dialog if no sound resource found
                isDialogOpen = false;
            }
        }
    }

    private int getSoundResourceForInstrument(String instrumentName) {
        // Map instrument name to raw resource file
        switch (instrumentName) {
            case "Guitar":
                return R.raw.guitar;
            case "Violin":
                return R.raw.violin;
            case "Djembe":
                return R.raw.djembe;
            case "Harp":
                return R.raw.harp;
            case "Maracas":
                return R.raw.maracas;
            case "Saxophone":
                return R.raw.saxophone;
            case "Glockenspiel":
                return R.raw.glockenspiel;
            case "Bugle":
                return R.raw.bugle;
            case "Flute":
                return R.raw.flute;
            case "Harmonica":
                return R.raw.harmonica;
            case "Harmonium":
                return R.raw.harmonium;
            case "Drum":
                return R.raw.drum;
            case "Tabla":
               return R.raw.tabla;
            case "Congas":
                return R.raw.congas;
            case "Accordion":
                return R.raw.accordion;
            case "Bassoon":
                return R.raw.bassoon;
            case "Piano":
                return R.raw.piano;
            case "Keytar":
                return R.raw.keytar;
            case "Serpent":
               return R.raw.serpent;
            case "Bagpipes":
                return R.raw.bagpipes;
            case "Oboe":
                return R.raw.oboe;
            default:
                return 0; // No sound found
        }
    }

    private AlertDialog showMagnifiedImage(ImageView imageView, String title, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_magnified_image, null);
        builder.setView(dialogView);

        ImageView magnifiedImageView = dialogView.findViewById(R.id.magnifiedImageView);
        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        magnifiedImageView.setImageDrawable(imageView.getDrawable());
        titleTextView.setText(title);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);

        dialog.setOnDismissListener(dialogInterface -> {
            if (textToSpeech != null) {
                textToSpeech.stop();
                isSoundPlaying = false;
                isDialogOpen = false;
            }
        });

        dialog.show();
        return dialog;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
