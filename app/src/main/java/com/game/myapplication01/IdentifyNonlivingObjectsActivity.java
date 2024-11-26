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


public class IdentifyNonlivingObjectsActivity extends AppCompatActivity {

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
            setContentView(R.layout.nonliving_small);
        } else { // Large screens
            setContentView(R.layout.nonliving_large);
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

        // Set up non-living object image click handlers
        setupImageClickListeners();
    }

    private void setupImageClickListeners() {
        int[] imageIds = {
                R.id.televisionImage, R.id.refrigeratorImage, R.id.stoveImage, R.id.bedImage, R.id.chairImage,
                R.id.tableImage, R.id.microwaveImage, R.id.fanImage, R.id.washingmachineImage, R.id.airconditionerImage,
                R.id.lampImage, R.id.phoneImage, R.id.computerImage, R.id.keyboardImage, R.id.mouseImage,
                R.id.laptopImage, R.id.carpetImage, R.id.mirrorImage, R.id.clockImage, R.id.bookshelfImage,
                R.id.curtainImage, R.id.drawerImage, R.id.ironImage, R.id.toasterImage, R.id.blenderImage,
                R.id.speakerImage, R.id.penImage, R.id.bottleImage, R.id.cameraImage, R.id.sofaImage
        };

        String[] titles = {
                "Television", "Refrigerator", "Stove", "Bed", "Chair",
                "Table", "Microwave", "Fan", "Washing Machine", "Air Conditioner",
                "Lamp", "Phone", "Computer", "Keyboard", "Mouse",
                "Laptop", "Carpet", "Mirror", "Clock", "Bookshelf",
                "Curtain", "Drawer", "Iron", "Toaster", "Blender",
                "Speaker", "Pen","Bottle","Camera","Sofa"
        };

        String[] descriptions = {
                "Television: An electronic device for receiving television signals.",
                "Refrigerator: An appliance used to store food at low temperatures.",
                "Stove: A device used for cooking food.",
                "Bed: A piece of furniture for sleeping.",
                "Chair: A piece of furniture designed to sit on.",
                "Table: A piece of furniture with a flat surface for working or eating.",
                "Microwave: An appliance used to heat or cook food quickly using microwave radiation.",
                "Fan: A device used to circulate air.",
                "Washing Machine: A machine used for washing clothes automatically.",
                "Air Conditioner: A device used to cool down and dehumidify the air in a room.",
                "Lamp: A device that produces light, typically used for illumination.",
                "Phone: A portable device used for communication.",
                "Computer: An electronic device used for processing data and running programs.",
                "Keyboard: A device used for typing input into a computer.",
                "Mouse: A pointing device used to interact with a computer.",
                "Laptop: A portable computer that can run on batteries.",
                "Carpet: A thick fabric used to cover floors.",
                "Mirror: A reflective surface used for viewing oneself or for decoration.",
                "Clock: A device used to measure and display time.",
                "Bookshelf: A piece of furniture used to store books.",
                "Curtain: A piece of fabric used to cover a window for privacy or decoration.",
                "Drawer: A sliding storage compartment, often found in furniture.",
                "Iron: A tool used to press clothes and remove wrinkles.",
                "Toaster: An appliance used to brown slices of bread.",
                "Blender: A kitchen appliance used for mixing, pureeing, or emulsifying food.",
                "Speaker: A device used to produce sound from electronic signals.",
                "Pen: A writing instrument used to apply ink to a surface.",
                "Bottle: A container used for holding liquids.",
                "Camera: A device used for capturing photos or videos.",
                "Sofa: A piece of furniture used for seating."
        };


        for (int i = 0; i < imageIds.length; i++) {
            int index = i; // Capture index for lambda
            ImageView imageView = findViewById(imageIds[i]);
            imageView.setOnClickListener(v ->
                    handleItemClick(imageView, titles[index], descriptions[index]));
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
