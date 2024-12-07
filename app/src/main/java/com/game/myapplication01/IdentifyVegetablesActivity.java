package com.game.myapplication01;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import android.speech.tts.UtteranceProgressListener;
import androidx.annotation.NonNull;

public class IdentifyVegetablesActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
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
            setContentView(R.layout.vegetable_small); // small layout
        } else { // Large screens
            setContentView(R.layout.vegetable_large); // large layout
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

        // Set up vegetable image click handlers
        setupImageClickListeners();
    }

    private void setupImageClickListeners() {
        int[] imageIds = {
                R.id.potatoImage, R.id.eggplantImage, R.id.carrotImage, R.id.cauliflowerImage, R.id.ladyfingerImage,
                R.id.spinachImage, R.id.bottlegourdImage, R.id.chickpeaImage, R.id.bittergourdImage, R.id.onionImage,
                R.id.tomatoImage, R.id.bellpepperImage, R.id.corianderleaveImage, R.id.gingerImage, R.id.garlicImage,
                R.id.radishImage, R.id.tarorootImage, R.id.pumpkinImage, R.id.cucumberImage, R.id.chiliImage,
                R.id.pointedgourdImage, R.id.ridgegourdImage, R.id.greenpeasImage, R.id.drumstickImage, R.id.beetrootImage,
                R.id.cabbageImage
        };

        String[] titles = {
                "Potato", "Eggplant", "Carrot", "Cauliflower", "Ladyfinger", "Spinach", "Bottle Gourd", "Chickpea",
                "Bitter Gourd", "Onion", "Tomato", "Bell Pepper", "Coriander Leave", "Ginger", "Garlic", "Radish",
                "Taro Root", "Pumpkin", "Cucumber", "Chili", "Pointed Gourd", "Ridge Gourd", "Green Peas", "Drumstick",
                "Beetroot", "Cabbage"
        };

        String[] descriptions = {
                "Potato: Starchy, often fried.",
                "Eggplant: Purple, used in curries.",
                "Carrot: Crunchy, orange, good for eyes.",
                "Cauliflower: White, high in fiber.",
                "Ladyfinger: Green, used in curries.",
                "Spinach: Leafy, rich in iron.",
                "Bottle Gourd: Mild, used in curries.",
                "Chickpea: Small, used in soups.",
                "Bitter Gourd: Bitter, used in stir-fries.",
                "Onion: Adds flavor to dishes.",
                "Tomato: Juicy, used in cooking.",
                "Bell Pepper: Sweet, used in stir-fries.",
                "Coriander Leave: Herb, used for garnishing.",
                "Ginger: Spicy, used in cooking.",
                "Garlic: Pungent, used in many dishes.",
                "Radish: Crunchy, peppery in taste.",
                "Taro Root: Starchy, used in soups.",
                "Pumpkin: Orange, used in soups.",
                "Cucumber: Cool, used in salads.",
                "Chili: Small, adds heat to food.",
                "Pointed Gourd: Small, used in stir-fries.",
                "Ridge Gourd: Used in curries.",
                "Green Peas: Sweet, often in soups.",
                "Drumstick: Slender, used in curries.",
                "Beetroot: Deep red, used in juices.",
                "Cabbage: Leafy, used in salads."
        };


        // Set click listeners for each vegetable image
        for (int i = 0; i < imageIds.length; i++) {
            int index = i;
            ImageView imageView = findViewById(imageIds[i]);
            imageView.setOnClickListener(v -> handleItemClick(imageView, titles[index], descriptions[index]));
        }
    }

    private void handleItemClick(ImageView imageView, String title, String speechText) {
        if (!isDialogOpen) {
            isDialogOpen = true;
            AlertDialog dialog = showMagnifiedImage(imageView, title, "");
            speakText(speechText, dialog);
        }
    }

    private void speakText(String text, AlertDialog dialog) {
        if (!isSoundPlaying) {
            isSoundPlaying = true;

            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    // Speech has started
                }

                @Override
                public void onDone(String utteranceId) {
                    runOnUiThread(() -> {
                        isSoundPlaying = false;
                        dialog.dismiss(); // Dismiss the dialog
                        isDialogOpen = false;
                    });
                }

                @Override
                public void onError(String utteranceId) {
                    runOnUiThread(() -> {
                        isSoundPlaying = false;
                        dialog.dismiss(); // Dismiss the dialog on error
                        isDialogOpen = false;
                    });
                }
            });

            // Use a unique utterance ID
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "utteranceId");
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
    }
}
