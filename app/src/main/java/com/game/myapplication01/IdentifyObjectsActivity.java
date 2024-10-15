package com.game.myapplication01;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Handler;

public class IdentifyObjectsActivity extends AppCompatActivity {

    private MediaPlayer tiger, tigersound;
    private MediaPlayer dog, dogsound;
    private MediaPlayer elephant, elephantsound;
    private MediaPlayer horse, horsesound;
    private MediaPlayer frog, frogsound;
    private MediaPlayer cow, cowsound;

    private ImageView tigerImage, dogImage, elephantImage;
    private ImageView horseImage, frogImage, cowImage;

    private boolean isSoundPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_object);

        // Initialize sounds
        tiger = MediaPlayer.create(this, R.raw.tiger);
        tigersound = MediaPlayer.create(this, R.raw.tigersound);
        dog= MediaPlayer.create(this, R.raw.dog);
        dogsound = MediaPlayer.create(this, R.raw.dogsound);
        elephant = MediaPlayer.create(this, R.raw.elephant);
        elephantsound = MediaPlayer.create(this, R.raw.elephantsound);
        horse = MediaPlayer.create(this, R.raw.horse);
        horsesound = MediaPlayer.create(this, R.raw.horsesound);
        frog = MediaPlayer.create(this, R.raw.frog);
        frogsound = MediaPlayer.create(this, R.raw.frogsound);
        cow = MediaPlayer.create(this, R.raw.cow);
        cowsound = MediaPlayer.create(this, R.raw.cowsound);

        // Initialize image views
        tigerImage = findViewById(R.id.tigerImage);
        dogImage = findViewById(R.id.dogImage);
        elephantImage = findViewById(R.id.elephantImage);
        horseImage = findViewById(R.id.horseImage);
        frogImage = findViewById(R.id.frogImage);
        cowImage = findViewById(R.id.cowImage);

        // Set onClickListeners for animals
        tigerImage.setOnClickListener(v -> {
            showMagnifiedImage(tigerImage, "Tiger", "");
            playTigerSound();
        });

        dogImage.setOnClickListener(v -> {
            showMagnifiedImage(dogImage, "Dog", "");
            playDogSound();
        });

        elephantImage.setOnClickListener(v -> {
            showMagnifiedImage(elephantImage, "Elephant", "");
            playElephantSound();
        });

        horseImage.setOnClickListener(v -> {
            showMagnifiedImage(horseImage, "Horse", "");
            playHorseSound();
        });

        frogImage.setOnClickListener(v -> {
            showMagnifiedImage(frogImage, "Frog", "");
            playFrogSound();
        });

        cowImage.setOnClickListener(v -> {
            showMagnifiedImage(cowImage, "Cow", "");
            playCowSound();
        });
    }

    private void showMagnifiedImage(ImageView imageView, String title, String description) {
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
        dialog.setCancelable(false);
        dialog.show();

        new Handler().postDelayed(dialog::dismiss, 5000);
    }

    private void playTigerSound() {
        if (!isSoundPlaying && tiger != null) {
            isSoundPlaying = true;
            tiger.reset();
            tiger = MediaPlayer.create(this, R.raw.tiger);
            tiger.start();
            tiger.setNextMediaPlayer(tigersound);
            tiger.setOnCompletionListener(mp -> playTigerRoarSound());
        }
    }

    private void playTigerRoarSound() {
        if (tigersound != null) {
            tigersound.reset();
            tigersound = MediaPlayer.create(this, R.raw.tigersound);
            tigersound.start();
            tigersound.setOnCompletionListener(mp -> isSoundPlaying = false);
        }
    }

    private void playDogSound() {
        if (!isSoundPlaying && dog != null) {
            isSoundPlaying = true;
            dog.reset();
            dog = MediaPlayer.create(this, R.raw.dog);
            dog.start();
            dog.setNextMediaPlayer(dogsound);
            dog.setOnCompletionListener(mp -> playDogBarkSound());
        }
    }

    private void playDogBarkSound() {
        if (dogsound != null) {
            dogsound.reset();
            dogsound = MediaPlayer.create(this, R.raw.dogsound);
            dogsound.start();
            dogsound.setOnCompletionListener(mp -> isSoundPlaying = false);
        }
    }

    private void playElephantSound() {
        if (!isSoundPlaying && elephant != null) {
            isSoundPlaying = true;
            elephant.reset();
            elephant = MediaPlayer.create(this, R.raw.elephant);
            elephant.setNextMediaPlayer(elephantsound);
            elephant.start();
            elephant.setOnCompletionListener(mp -> elephantsound.start());
            elephantsound.setOnCompletionListener(mp -> isSoundPlaying = false);
        }
    }

    private void playHorseSound() {
        if (!isSoundPlaying && horse != null) {
            isSoundPlaying = true;
            horse.reset();
            horse = MediaPlayer.create(this, R.raw.horse);
            horse.setNextMediaPlayer(horsesound);
            horse.start();
            horse.setOnCompletionListener(mp -> horsesound.start());
            horsesound.setOnCompletionListener(mp -> isSoundPlaying = false);
        }
    }
    private void playCowSound() {
        if (!isSoundPlaying && cow != null) {
            isSoundPlaying = true;
            cow.reset();
            cow = MediaPlayer.create(this, R.raw.cow);
            cow.setNextMediaPlayer(cowsound);
            cow.start();
            cow.setOnCompletionListener(mp -> cowsound.start());
            cowsound.setOnCompletionListener(mp -> isSoundPlaying = false);
        }
    }
    private void playFrogSound() {
        if (!isSoundPlaying && frog != null) {
            isSoundPlaying = true;
            frog.reset();
            frog = MediaPlayer.create(this, R.raw.frog);
            frog.setNextMediaPlayer(frogsound);
            frog.start();
            frog.setOnCompletionListener(mp -> frogsound.start());
            frogsound.setOnCompletionListener(mp -> isSoundPlaying = false);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayers();
    }

    private void releaseMediaPlayers() {
        if (tiger != null) tiger.release();
        if (tigersound != null) tigersound.release();
        if (dogsound != null) dogsound.release();
        if (dogsound != null) dogsound.release();
        if (elephant != null) elephant.release();
        if (elephantsound != null) elephantsound.release();
        if (horse != null) horse.release();
        if (horsesound != null) horsesound.release();
        if (frog != null) frog.release();
        if (frogsound != null) frogsound.release();
        if (cow != null) cow.release();
        if (cowsound != null) cowsound.release();
    }
}
