package com.game.myapplication01;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IdentifyFlowersActivity extends AppCompatActivity {

    private ImageView tigerImage, dogImage, elephantImage, lionImage, bearImage, catImage;
    private ImageView horseImage, frogImage, cowImage;
    private ImageView alligatorImage, anacondaImage, antelopeImage;
    private ImageView baboon, blackpanther, buffalo;
    private ImageView bull, cheetah, chimpanzee, hippopotamus, crocodile, dolphin, donkey, eel, hyena, fox, jackal, gharial, gibbon, giraffe, goat, gorilla, rabbit, zebra;
    private MediaPlayer currentMediaPlayer;
    private boolean isSoundPlaying = false;
    private boolean isDialogOpen = false; // Flag to track if the dialog is open

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flower_object);


    }

    private void handleAnimalClick(ImageView imageView, String title, int primarySoundRes, int secondarySoundRes) {
        if (!isDialogOpen) { // Check if the dialog is already open
            isDialogOpen = true; // Set the flag to true
            AlertDialog dialog = showMagnifiedImage(imageView, title, "");
            playAnimalSound(primarySoundRes, secondarySoundRes, dialog);
        }
    }

    private void playAnimalSound(int primarySoundRes, int secondarySoundRes, AlertDialog dialog) {
        if (currentMediaPlayer != null) {
            currentMediaPlayer.release();
            currentMediaPlayer = null;
        }

        if (!isSoundPlaying) {
            isSoundPlaying = true;
            currentMediaPlayer = MediaPlayer.create(this, primarySoundRes);
            currentMediaPlayer.start();

            if (secondarySoundRes != -1) {
                MediaPlayer secondarySound = MediaPlayer.create(this, secondarySoundRes);
                currentMediaPlayer.setNextMediaPlayer(secondarySound);
                currentMediaPlayer.setOnCompletionListener(mp -> {
                    secondarySound.start();
                    secondarySound.setOnCompletionListener(mp2 -> {
                        isSoundPlaying = false;
                        currentMediaPlayer.release();
                        currentMediaPlayer = null;
                        dialog.dismiss(); // Dismiss the dialog after sound ends
                        isDialogOpen = false; // Reset the dialog open flag
                    });
                });
            } else {
                currentMediaPlayer.setOnCompletionListener(mp -> {
                    isSoundPlaying = false;
                    currentMediaPlayer.release();
                    currentMediaPlayer = null;
                    dialog.dismiss(); // Dismiss the dialog after sound ends
                    isDialogOpen = false; // Reset the dialog open flag
                });
            }
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
            if (currentMediaPlayer != null && currentMediaPlayer.isPlaying()) {
                currentMediaPlayer.stop();
                currentMediaPlayer.release();
                currentMediaPlayer = null;
                isSoundPlaying = false;
                isDialogOpen = false; // Reset the dialog open flag
            }
        });

        dialog.show();
        return dialog;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentMediaPlayer != null) {
            currentMediaPlayer.release();
            currentMediaPlayer = null;
        }
    }
}
