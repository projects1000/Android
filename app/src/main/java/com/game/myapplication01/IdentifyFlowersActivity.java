package com.game.myapplication01;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IdentifyFlowersActivity extends AppCompatActivity {

    // Declare ImageView variables for fruits and flowers
    private ImageView appleImage, grapesImage, bananaImage, mangoImage, orangeImage, pineappleImage, strawberryImage, watermelonImage,
            papayaImage, cherryImage, pomegranateImage, kiwifruitImage, guavaImage, lycheeImage, dragonImage, coconutImage, cranberryImage, jackfruitImage,
            roseImage, lilyImage, lavenderImage, hibiscusImage, jasmineImage, tulsiImage, dahliaImage, lotusImage, marigoldImage,crapeJasmineImage ,sunflowerImage;

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
            setContentView(R.layout.flower_small);

        } else if(screenWidth > 720)  //large
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.flower_large);
        }

// Fruits
        // Fruits
        appleImage = findViewById(R.id.appleImage);
        appleImage.setOnClickListener(v -> handleItemClick(appleImage, "Apple", R.raw.apple, -1));

        grapesImage = findViewById(R.id.grapesImage);
        grapesImage.setOnClickListener(v -> handleItemClick(grapesImage, "Grapes", R.raw.grapes, -1));

        bananaImage = findViewById(R.id.bananaImage);
        bananaImage.setOnClickListener(v -> handleItemClick(bananaImage, "Banana", R.raw.banana, -1));

        mangoImage = findViewById(R.id.mangoImage);
        mangoImage.setOnClickListener(v -> handleItemClick(mangoImage, "Mango", R.raw.mango, -1));

        orangeImage = findViewById(R.id.orangeImage);
        orangeImage.setOnClickListener(v -> handleItemClick(orangeImage, "Orange", R.raw.orange, -1));

        pineappleImage = findViewById(R.id.pineappleImage);
        pineappleImage.setOnClickListener(v -> handleItemClick(pineappleImage, "Pineapple", R.raw.pineapple, -1));

        strawberryImage = findViewById(R.id.strawberryImage);
        strawberryImage.setOnClickListener(v -> handleItemClick(strawberryImage, "Strawberry", R.raw.strawberry, -1));

        watermelonImage = findViewById(R.id.watermelonImage);
        watermelonImage.setOnClickListener(v -> handleItemClick(watermelonImage, "Watermelon", R.raw.watermelon, -1));

        papayaImage = findViewById(R.id.papayaImage);
        papayaImage.setOnClickListener(v -> handleItemClick(papayaImage, "Papaya", R.raw.papaya, -1));

        cherryImage = findViewById(R.id.cherryImage);
        cherryImage.setOnClickListener(v -> handleItemClick(cherryImage, "Cherry", R.raw.cherry, -1));

        pomegranateImage = findViewById(R.id.pomegranateImage);
        pomegranateImage.setOnClickListener(v -> handleItemClick(pomegranateImage, "Pomegranate", R.raw.pomegranate, -1));

        kiwifruitImage = findViewById(R.id.kiwifruitImage);
        kiwifruitImage.setOnClickListener(v -> handleItemClick(kiwifruitImage, "Kiwifruit", R.raw.kiwifruit, -1));

        guavaImage = findViewById(R.id.guavaImage);
        guavaImage.setOnClickListener(v -> handleItemClick(guavaImage, "Guava", R.raw.guava, -1));

        lycheeImage = findViewById(R.id.lycheeImage);
        lycheeImage.setOnClickListener(v -> handleItemClick(lycheeImage, "Lychee", R.raw.lychee, -1));

        dragonImage = findViewById(R.id.dragonImage);
        dragonImage.setOnClickListener(v -> handleItemClick(dragonImage, "Dragon", R.raw.dragon, -1));

        coconutImage = findViewById(R.id.coconutImage);
        coconutImage.setOnClickListener(v -> handleItemClick(coconutImage, "Coconut", R.raw.coconut, -1));

        cranberryImage = findViewById(R.id.cranberryImage);
        cranberryImage.setOnClickListener(v -> handleItemClick(cranberryImage, "Cranberry", R.raw.cranberry, -1));

        jackfruitImage = findViewById(R.id.jackfruitImage);
        jackfruitImage.setOnClickListener(v -> handleItemClick(jackfruitImage, "Jackfruit", R.raw.jackfruit, -1));

// Flowers
        roseImage = findViewById(R.id.roseImage);
        roseImage.setOnClickListener(v -> handleItemClick(roseImage, "Rose", R.raw.rose, -1));

        lilyImage = findViewById(R.id.lilyImage);
        lilyImage.setOnClickListener(v -> handleItemClick(lilyImage, "Lily", R.raw.lily, -1));

        lavenderImage = findViewById(R.id.lavenderImage);
        lavenderImage.setOnClickListener(v -> handleItemClick(lavenderImage, "Lavender", R.raw.lavender, -1));

        hibiscusImage = findViewById(R.id.hibiscusImage);
        hibiscusImage.setOnClickListener(v -> handleItemClick(hibiscusImage, "Hibiscus", R.raw.hibiscus, -1));

        jasmineImage = findViewById(R.id.jasmineImage);
        jasmineImage.setOnClickListener(v -> handleItemClick(jasmineImage, "Jasmine", R.raw.jasmine, -1));

        tulsiImage = findViewById(R.id.tulsiImage);
        tulsiImage.setOnClickListener(v -> handleItemClick(tulsiImage, "Tulsi", R.raw.tulsi, -1));

        dahliaImage = findViewById(R.id.dahliaImage);
        dahliaImage.setOnClickListener(v -> handleItemClick(dahliaImage, "Dahlia", R.raw.dahlia, -1));

        lotusImage = findViewById(R.id.lotusImage);
        lotusImage.setOnClickListener(v -> handleItemClick(lotusImage, "Lotus", R.raw.lotus, -1));

        sunflowerImage = findViewById(R.id.sunflowerImage);
        sunflowerImage.setOnClickListener(v -> handleItemClick(sunflowerImage, "Sunflower", R.raw.sunflower, -1));

        crapeJasmineImage = findViewById(R.id.crapeJasmineImage);
        crapeJasmineImage.setOnClickListener(v -> handleItemClick(crapeJasmineImage, "Crape Jasmine", R.raw.crapejasmine, -1));

        marigoldImage = findViewById(R.id.marigoldImage);
        marigoldImage.setOnClickListener(v -> handleItemClick(marigoldImage, "Marigold", R.raw.marigold, -1));

    }

    private void handleItemClick(ImageView imageView, String title, int primarySoundRes, int secondarySoundRes) {
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
