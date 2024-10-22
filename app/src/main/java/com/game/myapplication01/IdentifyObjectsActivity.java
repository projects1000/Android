package com.game.myapplication01;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;

public class IdentifyObjectsActivity extends AppCompatActivity {

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
        setContentView(R.layout.animal_object);

        // Initialize image views
        tigerImage = findViewById(R.id.tigerImage);
        dogImage = findViewById(R.id.dogImage);
        elephantImage = findViewById(R.id.elephantImage);
        lionImage = findViewById(R.id.lionImage);
        bearImage = findViewById(R.id.bearImage);
        catImage = findViewById(R.id.catImage);
        horseImage = findViewById(R.id.horseImage);
        frogImage = findViewById(R.id.frogImage);
        cowImage = findViewById(R.id.cowImage);
        alligatorImage = findViewById(R.id.alligatorImage);
        anacondaImage = findViewById(R.id.anacondaImage);
        antelopeImage = findViewById(R.id.antelopeImage);
        baboon = findViewById(R.id.baboonImage);
        blackpanther = findViewById(R.id.blackPantherImage);
        buffalo = findViewById(R.id.buffaloImage);
        bull = findViewById(R.id.bullImage);
        cheetah = findViewById(R.id.cheetahImage);
        chimpanzee = findViewById(R.id.chimpanzeeImage);
        hippopotamus = findViewById(R.id.hippopotamusImage);
        crocodile = findViewById(R.id.crocodileImage);
        dolphin = findViewById(R.id.dolphinImage);
        donkey = findViewById(R.id.donkeyImage);
        eel = findViewById(R.id.eelImage);
        hyena = findViewById(R.id.hyenaImage);
        fox = findViewById(R.id.foxImage);
        jackal = findViewById(R.id.jackalImage);
        gharial = findViewById(R.id.gharialImage);
        gibbon = findViewById(R.id.gibbonImage);
        giraffe = findViewById(R.id.giraffeImage);
        goat = findViewById(R.id.goatImage);
        gorilla = findViewById(R.id.gorillaImage);
        rabbit = findViewById(R.id.rabbitImage);   // For rabbit
        zebra = findViewById(R.id.zebraImage);

        // Set click listeners
        tigerImage.setOnClickListener(v -> handleAnimalClick(tigerImage, "Tiger", R.raw.tiger, R.raw.tigersound));
        dogImage.setOnClickListener(v -> handleAnimalClick(dogImage, "Dog", R.raw.dog, R.raw.dogsound));
        elephantImage.setOnClickListener(v -> handleAnimalClick(elephantImage, "Elephant", R.raw.elephant, R.raw.elephantsound));
        lionImage.setOnClickListener(v -> handleAnimalClick(lionImage, "Lion", R.raw.lion, R.raw.lionsound));
        bearImage.setOnClickListener(v -> handleAnimalClick(bearImage, "Bear", R.raw.bear, R.raw.bearsound));
        catImage.setOnClickListener(v -> handleAnimalClick(catImage, "Cat", R.raw.cat, R.raw.catsound));
        horseImage.setOnClickListener(v -> handleAnimalClick(horseImage, "Horse", R.raw.horse, R.raw.horsesound));
        frogImage.setOnClickListener(v -> handleAnimalClick(frogImage, "Frog", R.raw.frog, R.raw.frogsound));
        cowImage.setOnClickListener(v -> handleAnimalClick(cowImage, "Cow", R.raw.cow, R.raw.cowsound));
        alligatorImage.setOnClickListener(v -> handleAnimalClick(alligatorImage, "Alligator", R.raw.alligator, -1));
        anacondaImage.setOnClickListener(v -> handleAnimalClick(anacondaImage, "Anaconda", R.raw.anaconda, -1));
        antelopeImage.setOnClickListener(v -> handleAnimalClick(antelopeImage, "Antelope", R.raw.antelope, -1));
        baboon.setOnClickListener(v -> handleAnimalClick(baboon, "Baboon", R.raw.baboon, -1));
        blackpanther.setOnClickListener(v -> handleAnimalClick(blackpanther, "Black Panther", R.raw.blackpanther, -1));
        buffalo.setOnClickListener(v -> handleAnimalClick(buffalo, "Buffalo", R.raw.buffalo, -1));
        bull.setOnClickListener(v -> handleAnimalClick(bull, "Bull", R.raw.bull, -1));
        cheetah.setOnClickListener(v -> handleAnimalClick(cheetah, "Cheetah", R.raw.cheetah, -1));
        chimpanzee.setOnClickListener(v -> handleAnimalClick(chimpanzee, "Chimpanzee", R.raw.chimpanzee, -1));
        hippopotamus.setOnClickListener(v -> handleAnimalClick(hippopotamus, "Hippopotamus", R.raw.hippopotamus, -1));
        crocodile.setOnClickListener(v -> handleAnimalClick(crocodile, "Crocodile", R.raw.crocodile, -1));
        dolphin.setOnClickListener(v -> handleAnimalClick(dolphin, "Dolphin", R.raw.dolphin, -1));
        donkey.setOnClickListener(v -> handleAnimalClick(donkey, "Donkey", R.raw.donkey, -1));
        eel.setOnClickListener(v -> handleAnimalClick(eel, "Eel", R.raw.eel, -1));
        hyena.setOnClickListener(v -> handleAnimalClick(hyena, "Hyena", R.raw.hyena, -1));
        fox.setOnClickListener(v -> handleAnimalClick(fox, "Fox", R.raw.fox, -1));
        jackal.setOnClickListener(v -> handleAnimalClick(jackal, "Jackal", R.raw.jackal, R.raw.jackalsound));
        gharial.setOnClickListener(v -> handleAnimalClick(gharial, "Gharial", R.raw.gharial, -1));
        gibbon.setOnClickListener(v -> handleAnimalClick(gibbon, "Gibbon", R.raw.gibbon, -1));
        giraffe.setOnClickListener(v -> handleAnimalClick(giraffe, "Giraffe", R.raw.giraffe, -1));
        goat.setOnClickListener(v -> handleAnimalClick(goat, "Goat", R.raw.goat, -1));
        gorilla.setOnClickListener(v -> handleAnimalClick(gorilla, "Gorilla", R.raw.gorilla, -1));
        rabbit.setOnClickListener(v -> handleAnimalClick(rabbit, "Rabbit", R.raw.rabbit, R.raw.rabbitsound));  // For rabbit
        zebra.setOnClickListener(v -> handleAnimalClick(zebra, "Zebra", R.raw.zebra, R.raw.zebrasound));      // For zebra

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
