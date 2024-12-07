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

public class IdentifyCommunityActivity extends AppCompatActivity {

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
            setContentView(R.layout.community_small);
        } else { // Large screens
            setContentView(R.layout.community_large);
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
                R.id.doctorImage, R.id.nurseImage, R.id.paramedicImage, R.id.ambulancedriverImage,
                R.id.veterinarianImage, R.id.teacherImage, R.id.sanitationworkerImage, R.id.postalworkerImage,
                R.id.electricianImage, R.id.policeofficerImage, R.id.librarianImage, R.id.schoolbusdriverImage,
                R.id.plumberImage, R.id.gardenerImage, R.id.farmerImage, R.id.grocerImage,
                R.id.carpenterImage, R.id.chefImage, R.id.butcherImage, R.id.tailorImage, R.id.securityguardImage
        };

        // Titles corresponding to each image
        String[] titles = {
                "Doctor", "Nurse", "Paramedic", "Ambulance Driver", "Veterinarian", "Teacher", "Sanitation Worker",
                "Postal Worker", "Electrician", "Police Officer", "Librarian", "School Bus Driver", "Plumber",
                "Gardener", "Farmer", "Grocer", "Carpenter", "Chef", "Butcher", "Tailor", "Security Guard"
        };

        // Descriptions for each profession
        String[] descriptions = {
                "Doctor: A medical professional who diagnoses and treats illnesses.",
                "Nurse: A healthcare professional who provides patient care.",
                "Paramedic: A trained medical professional who responds to emergencies.",
                "Ambulance Driver: A person responsible for driving an ambulance.",
                "Veterinarian: A doctor for animals.",
                "Teacher: An educator who imparts knowledge to students.",
                "Sanitation Worker: A person responsible for cleaning and maintaining public spaces.",
                "Postal Worker: A person who delivers mail and packages.",
                "Electrician: A professional who installs and maintains electrical systems.",
                "Police Officer: A law enforcement officer responsible for maintaining public safety.",
                "Librarian: A person who manages and organizes library resources.",
                "School Bus Driver: A driver who transports children to and from school.",
                "Plumber: A person who installs and repairs water and piping systems.",
                "Gardener: A person who takes care of plants and outdoor spaces.",
                "Farmer: A person who cultivates crops or raises animals for food and resources.",
                "Grocer: A person who sells food and other goods at a store.",
                "Carpenter: A person who builds and repairs wooden structures.",
                "Chef: A professional cook responsible for food preparation.",
                "Butcher: A person who cuts and prepares meat for sale.",
                "Tailor: A person who makes and repairs clothes.",
                "Security Guard: A person responsible for protecting property and people."
        };

        for (int i = 0; i < imageIds.length; i++) {
            int index = i; // Capture index for lambda
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
