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

public class IdentifyCommunityActivity extends AppCompatActivity {

    private ImageView carImage;

    private boolean isSoundPlaying = false;
    private boolean isDialogOpen = false; // Flag to track if the dialog is open
    private TextToSpeech textToSpeech;

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
        int screenHeight = displayMetrics.heightPixels;
        MusicManager.pauseMusic();

        if (screenWidth <= 720) { // Small screens (width <= 720px)
            setContentView(R.layout.community_small);
        } else { // Large screens
            setContentView(R.layout.community_large);
        }

        // Initialize TTS
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int languageResult = textToSpeech.setLanguage(Locale.US);
                if (languageResult == TextToSpeech.LANG_MISSING_DATA
                        || languageResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Handle missing language data or unsupported language
                }
            }
        });

    }

    private void handleItemClick(ImageView imageView, String title, String speechText) {
        if (!isDialogOpen) { // Check if the dialog is already open
            isDialogOpen = true; // Set the flag to true
            AlertDialog dialog = showMagnifiedImage(imageView, title, "");
            speakText(speechText, dialog);  // Convert text to speech
        }
    }

    private void speakText(String text, AlertDialog dialog) {
        if (!isSoundPlaying) {
            isSoundPlaying = true;
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

            // Set a listener for when TTS finishes speaking
            textToSpeech.setOnUtteranceCompletedListener(utteranceId -> {
                isSoundPlaying = false;
                dialog.dismiss(); // Dismiss the dialog after speech ends
                isDialogOpen = false; // Reset the dialog open flag
            });
        }
    }

    private AlertDialog showMagnifiedImage(ImageView imageView, String title, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_magnified_image, null);
        builder.setView(dialogView);
        ImageView magnifiedImageView = dialogView.findViewById(R.id.magnifiedImageView);
        TextView descriptionTextView = dialogView.findViewById(R.id.descriptionTextView);
        TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
        magnifiedImageView.setImageDrawable(imageView.getDrawable());
        descriptionTextView.setText(description);
        titleTextView.setText(title);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);  // Prevent dismissing by clicking outside

        dialog.setOnDismissListener(dialogInterface -> {
            if (textToSpeech != null) {
                textToSpeech.stop();  // Stop speech if dialog is dismissed
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
