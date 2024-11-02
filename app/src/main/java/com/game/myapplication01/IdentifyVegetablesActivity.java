package com.game.myapplication01;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IdentifyVegetablesActivity extends AppCompatActivity {

    private ImageView potatoImage, eggplantImage, carrotImage, cauliflowerImage, ladyfingerImage, spinachImage, bottlegourdImage, chickpeaImage, bittergourdImage, onionImage, tomatoImage, bellpepperImage, corianderleaveImage, gingerImage, garlicImage, radishImage, tarorootImage, pumpkinImage, cucumberImage, chiliImage, pointedgourdImage, ridgegourdImage, greenpeasImage, drumstickImage, beetrootImage, cabbageImage;

    private MediaPlayer currentMediaPlayer;
    private boolean isSoundPlaying = false;
    private boolean isDialogOpen = false; // Flag to track if the dialog is open

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            super.onCreate(savedInstanceState);
            setContentView(R.layout.vegetable_small);

        } else if(screenWidth > 720)  //large
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.vegetable_large);
        }
// Vegetable ImageView initializations
        potatoImage = findViewById(R.id.potatoImage);
        potatoImage.setOnClickListener(v -> handleItemClick(potatoImage, "Potato", R.raw.potato, -1));

        eggplantImage = findViewById(R.id.eggplantImage);
        eggplantImage.setOnClickListener(v -> handleItemClick(eggplantImage, "Eggplant", R.raw.eggplant, -1));

        carrotImage = findViewById(R.id.carrotImage);
        carrotImage.setOnClickListener(v -> handleItemClick(carrotImage, "Carrot", R.raw.carrot, -1));

        cauliflowerImage = findViewById(R.id.cauliflowerImage);
        cauliflowerImage.setOnClickListener(v -> handleItemClick(cauliflowerImage, "Cauliflower", R.raw.cauliflower, -1));

        ladyfingerImage = findViewById(R.id.ladyfingerImage);
        ladyfingerImage.setOnClickListener(v -> handleItemClick(ladyfingerImage, "Ladyfinger", R.raw.ladyfinger, -1));

        spinachImage = findViewById(R.id.spinachImage);
        spinachImage.setOnClickListener(v -> handleItemClick(spinachImage, "Spinach", R.raw.spinach, -1));

        bottlegourdImage = findViewById(R.id.bottlegourdImage);
        bottlegourdImage.setOnClickListener(v -> handleItemClick(bottlegourdImage, "Bottle Gourd", R.raw.bottlegourd, -1));

        chickpeaImage = findViewById(R.id.chickpeaImage);
        chickpeaImage.setOnClickListener(v -> handleItemClick(chickpeaImage, "Chickpea", R.raw.chickpea, -1));

        bittergourdImage = findViewById(R.id.bittergourdImage);
        bittergourdImage.setOnClickListener(v -> handleItemClick(bittergourdImage, "Bitter Gourd", R.raw.bittergourd, -1));

        onionImage = findViewById(R.id.onionImage);
        onionImage.setOnClickListener(v -> handleItemClick(onionImage, "Onion", R.raw.onion, -1));

        tomatoImage = findViewById(R.id.tomatoImage);
        tomatoImage.setOnClickListener(v -> handleItemClick(tomatoImage, "Tomato", R.raw.tomato, -1));

        bellpepperImage = findViewById(R.id.bellpepperImage);
        bellpepperImage.setOnClickListener(v -> handleItemClick(bellpepperImage, "Bell Pepper", R.raw.bellpepper, -1));

        corianderleaveImage = findViewById(R.id.corianderleaveImage);
        corianderleaveImage.setOnClickListener(v -> handleItemClick(corianderleaveImage, "Coriander Leave", R.raw.corianderleave, -1));

        gingerImage = findViewById(R.id.gingerImage);
        gingerImage.setOnClickListener(v -> handleItemClick(gingerImage, "Ginger", R.raw.ginger, -1));

        garlicImage = findViewById(R.id.garlicImage);
        garlicImage.setOnClickListener(v -> handleItemClick(garlicImage, "Garlic", R.raw.garlic, -1));

        radishImage = findViewById(R.id.radishImage);
        radishImage.setOnClickListener(v -> handleItemClick(radishImage, "Radish", R.raw.radish, -1));

        tarorootImage = findViewById(R.id.tarorootImage);
        tarorootImage.setOnClickListener(v -> handleItemClick(tarorootImage, "Taro Root", R.raw.taroroot, -1));

        pumpkinImage = findViewById(R.id.pumpkinImage);
        pumpkinImage.setOnClickListener(v -> handleItemClick(pumpkinImage, "Pumpkin", R.raw.pumpkin, -1));

        cucumberImage = findViewById(R.id.cucumberImage);
        cucumberImage.setOnClickListener(v -> handleItemClick(cucumberImage, "Cucumber", R.raw.cucumber, -1));

        chiliImage = findViewById(R.id.chiliImage);
        chiliImage.setOnClickListener(v -> handleItemClick(chiliImage, "Chili", R.raw.chili, -1));

        pointedgourdImage = findViewById(R.id.pointedgourdImage);
        pointedgourdImage.setOnClickListener(v -> handleItemClick(pointedgourdImage, "Pointed Gourd", R.raw.pointedgourd, -1));

        ridgegourdImage = findViewById(R.id.ridgegourdImage);
        ridgegourdImage.setOnClickListener(v -> handleItemClick(ridgegourdImage, "Ridge Gourd", R.raw.ridgegourd, -1));

        greenpeasImage = findViewById(R.id.greenpeasImage);
        greenpeasImage.setOnClickListener(v -> handleItemClick(greenpeasImage, "Green Peas", R.raw.greenpeas, -1));

        drumstickImage = findViewById(R.id.drumstickImage);
        drumstickImage.setOnClickListener(v -> handleItemClick(drumstickImage, "Drumstick", R.raw.drumstick, -1));

        beetrootImage = findViewById(R.id.beetrootImage);
        beetrootImage.setOnClickListener(v -> handleItemClick(beetrootImage, "Beetroot", R.raw.beetroot, -1));

        cabbageImage = findViewById(R.id.cabbageImage);
        cabbageImage.setOnClickListener(v -> handleItemClick(cabbageImage, "Cabbage", R.raw.cabbage, -1));


    }

    private void handleItemClick(ImageView imageView, String title, int primarySoundRes, int secondarySoundRes) {
        if (!isDialogOpen) { // Check if the dialog is already open
            isDialogOpen = true; // Set the flag to true
            AlertDialog dialog = showMagnifiedImage(imageView, title, "");
            playObjectSound(primarySoundRes, secondarySoundRes, dialog);
        }
    }

    private void playObjectSound(int primarySoundRes, int secondarySoundRes, AlertDialog dialog) {
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
