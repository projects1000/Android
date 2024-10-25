package com.game.myapplication01;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IdentifyBirdsActivity extends AppCompatActivity {

    private ImageView parrotImage;
    private ImageView peacockImage, pigeonImage, eagleImage, owlImage, cuckooImage, kingfisherImage, duckImage, falconImage,
            penguinImage, swanImage, woodpeckerImage, crowImage, doveImage, ostrichImage, kiwiImage,
            sparrowImage, turkeyImage, emuImage, henImage, roosterImage;
    private MediaPlayer currentMediaPlayer;
    private boolean isSoundPlaying = false;
    private boolean isDialogOpen = false; // Flag to track if the dialog is open

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        if (screenWidth <= 720) { // Small screens (width <= 720px)
            super.onCreate(savedInstanceState);
            setContentView(R.layout.birds_small);

        } else if(screenWidth > 720)  //large
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.birds_large);
        }

        parrotImage = findViewById(R.id.parrotImage);
        parrotImage.setOnClickListener(v -> handleAnimalClick(parrotImage, "Parrot", R.raw.parrot, R.raw.parrotsound));

        peacockImage = findViewById(R.id.peacockImage);
        peacockImage.setOnClickListener(v -> handleAnimalClick(peacockImage, "Peacock", R.raw.peacock, R.raw.peacocksound));

        pigeonImage = findViewById(R.id.pigeonImage);
        pigeonImage.setOnClickListener(v -> handleAnimalClick(pigeonImage, "Pigeon", R.raw.pigeon, R.raw.pigeonsound));

        eagleImage = findViewById(R.id.eagleImage);
        eagleImage.setOnClickListener(v -> handleAnimalClick(eagleImage, "Eagle", R.raw.eagle, R.raw.eaglesound));

        owlImage = findViewById(R.id.owlImage);
        owlImage.setOnClickListener(v -> handleAnimalClick(owlImage, "Owl", R.raw.owl, R.raw.owlsound));

        cuckooImage = findViewById(R.id.cuckooImage);
        cuckooImage.setOnClickListener(v -> handleAnimalClick(cuckooImage, "Cuckoo", R.raw.cuckoo, R.raw.cuckoosound));

        kingfisherImage = findViewById(R.id.kingfisherImage);
        kingfisherImage.setOnClickListener(v -> handleAnimalClick(kingfisherImage, "Kingfisher", R.raw.kingfisher, R.raw.kingfishersound));

        duckImage = findViewById(R.id.duckImage);
        duckImage.setOnClickListener(v -> handleAnimalClick(duckImage, "Duck", R.raw.duck, R.raw.ducksound));

        falconImage = findViewById(R.id.falconImage);
        falconImage.setOnClickListener(v -> handleAnimalClick(falconImage, "Falcon", R.raw.falcon, R.raw.falconsound));

        penguinImage = findViewById(R.id.penguinImage);
        penguinImage.setOnClickListener(v -> handleAnimalClick(penguinImage, "Penguin", R.raw.penguin, R.raw.penguinsound));

        swanImage = findViewById(R.id.swanImage);
        swanImage.setOnClickListener(v -> handleAnimalClick(swanImage, "Swan", R.raw.swan, R.raw.swansound));

        woodpeckerImage = findViewById(R.id.woodpeckerImage);
        woodpeckerImage.setOnClickListener(v -> handleAnimalClick(woodpeckerImage, "Woodpecker", R.raw.woodpecker, R.raw.woodpeckersound));

        crowImage = findViewById(R.id.crowImage);
        crowImage.setOnClickListener(v -> handleAnimalClick(crowImage, "Crow", R.raw.crow, R.raw.crowsound));

        doveImage = findViewById(R.id.doveImage);
        doveImage.setOnClickListener(v -> handleAnimalClick(doveImage, "Dove", R.raw.dove, R.raw.dovesound));

        ostrichImage = findViewById(R.id.ostrichImage);
        ostrichImage.setOnClickListener(v -> handleAnimalClick(ostrichImage, "Ostrich", R.raw.ostrich, R.raw.ostrichsound));

        kiwiImage = findViewById(R.id.kiwiImage);
        kiwiImage.setOnClickListener(v -> handleAnimalClick(kiwiImage, "Kiwi", R.raw.kiwi, R.raw.kiwisound));

        sparrowImage = findViewById(R.id.sparrowImage);
        sparrowImage.setOnClickListener(v -> handleAnimalClick(sparrowImage, "Sparrow", R.raw.sparrow, R.raw.sparrowsound));

        turkeyImage = findViewById(R.id.turkeyImage);
        turkeyImage.setOnClickListener(v -> handleAnimalClick(turkeyImage, "Turkey", R.raw.turkey, R.raw.turkeysound));

        emuImage = findViewById(R.id.emuImage);
        emuImage.setOnClickListener(v -> handleAnimalClick(emuImage, "Emu", R.raw.emu, R.raw.emusound));

        henImage = findViewById(R.id.henImage);
        henImage.setOnClickListener(v -> handleAnimalClick(henImage, "Hen", R.raw.hen, R.raw.hensound));

        roosterImage = findViewById(R.id.roosterImage);
        roosterImage.setOnClickListener(v -> handleAnimalClick(roosterImage, "Rooster", R.raw.rooster, R.raw.roostersound));
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
