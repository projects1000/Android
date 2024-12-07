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


public class IdentifyVehiclesActivity extends AppCompatActivity {

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
            setContentView(R.layout.vehicle_small);
        } else { // Large screens
            setContentView(R.layout.vehicle_large);
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

        // Set up vehicle image click handlers
        setupImageClickListeners();
    }

    private void setupImageClickListeners() {
        int[] imageIds = {
                R.id.carImage, R.id.truckImage, R.id.busImage, R.id.tankImage, R.id.bikeImage, R.id.scooterImage,
                R.id.tractorImage, R.id.jeepImage, R.id.pickupImage, R.id.craneImage, R.id.minivanImage, R.id.rickshawImage,
                R.id.ambulanceImage, R.id.taxiImage, R.id.helicopterImage, R.id.firetruckImage, R.id.shipImage, R.id.trainImage,
                R.id.autoImage, R.id.bicycleImage, R.id.airplaneImage, R.id.boatImage, R.id.rocketImage, R.id.tramImage,
                R.id.submarineImage, R.id.bullcartImage,R.id.sportscarImage
        };
        String[] titles = {"Car", "Truck", "Bus", "Tank", "Bike", "Scooter", "Tractor", "Jeep", "Pickup",
                "Crane", "Minivan", "Rickshaw", "Ambulance", "Taxi", "Helicopter", "Firetruck",
                "Ship", "Train", "Auto", "Bicycle", "Airplane", "Boat", "Rocket", "Tram",
                "Submarine", "Bullcart", "Sportscar"};

        String[] descriptions = {
                "Car: It's so comfortable and takes you everywhere!",
                "Truck: A strong vehicle that carries heavy stuff.",
                "Bus: A big vehicle that carries lots of people.",
                "Tank: A heavily armored military vehicle.",
                "Bike: A fun vehicle you pedal with your feet!",
                "Scooter: A small vehicle with two wheels.",
                "Tractor: A vehicle used on farms for heavy tasks.",
                "Jeep: A small rugged vehicle for off-road driving.",
                "Pickup: A truck with an open cargo area.",
                "Crane: A large machine for lifting heavy objects.",
                "Minivan: A family vehicle with plenty of space.",
                "Rickshaw: A small vehicle pulled by a human or motor.",
                "Ambulance: A vehicle used for transporting the sick or injured.",
                "Taxi: A car for hire to take passengers to destinations.",
                "Helicopter: A flying vehicle that uses rotor blades.",
                "Firetruck: A vehicle used by firefighters to extinguish fires.",
                "Ship: A large boat that travels across seas and oceans.",
                "Train: It runs on tracks and goes fast!",
                "Auto: A small, three-wheeled vehicle often used in cities.",
                "Bicycle: A two-wheeled vehicle you pedal with your legs.",
                "Airplane: It flies high and takes you far.",
                "Boat: A small watercraft used for traveling across water.",
                "Rocket: A vehicle designed to travel in outer space.",
                "Tram: A vehicle that runs on tracks within cities.",
                "Submarine: A watercraft designed to travel underwater.",
                "Bullcart: A cart pulled by bulls, often used in rural areas.",
                "SportsCar: A high-performance vehicle designed for speed and agility."
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
