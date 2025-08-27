package com.game.myapplication01;

import android.app.AlertDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import android.speech.tts.UtteranceProgressListener;
import androidx.annotation.NonNull;

public class IdentifyGamesActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private boolean isSoundPlaying = false;
    private boolean isDialogOpen = false; // Flag to track if the dialog is open

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_games);

        // Set up Game buttons
        findViewById(R.id.btnGame1).setOnClickListener(v -> handleGameClick(1));
        findViewById(R.id.btnGame2).setOnClickListener(v -> handleGameClick(2));
        findViewById(R.id.btnGame3).setOnClickListener(v -> handleGameClick(3));
        findViewById(R.id.btnGame4).setOnClickListener(v -> handleGameClick(4));

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
                R.id.footballImage, R.id.cricketImage, R.id.basketballImage, R.id.tennisImage,
                R.id.hockeyImage, R.id.boxingImage, R.id.golfImage, R.id.swimmingImage,
                R.id.cyclingImage, R.id.archeryImage, R.id.chessImage, R.id.tabletennisImage,
                R.id.dartsImage, R.id.ludoImage, R.id.checkersImage, R.id.monopolyImage,
                R.id.carromImage, R.id.billiardsImage, R.id.bowlingImage, R.id.wrestlingImage,
                R.id.khoKhoImage, R.id.gillidandaImage, R.id.cornholeImage, R.id.tugofwarImage,
                R.id.bocceballImage, R.id.hopscotchImage, R.id.sepaktakrawImage
        };

        // Titles corresponding to each image
        String[] titles = {
                "Football", "Cricket", "Basketball", "Tennis", "Hockey", "Boxing", "Golf", "Swimming",
                "Cycling", "Archery", "Chess", "Table Tennis", "Darts", "Ludo", "Checkers", "Monopoly",
                "Carrom", "Billiards", "Bowling", "Wrestling", "Kho-Kho", "Gilli Danda", "Cornhole",
                "Tug of War", "Bocce Ball", "Hopscotch", "Sepak Takraw"
        };

        // Descriptions for each sport
        String[] descriptions = {
                "Football: A fun sport played with a round ball.",
                "Cricket: A bat-and-ball game played between two teams.",
                "Basketball: A sport where you score points by throwing a ball through a hoop.",
                "Tennis: A sport where players hit a ball over a net using rackets.",
                "Hockey: A team sport played on ice or a field with a stick and a puck or ball.",
                "Boxing: A combat sport where two people fight using their fists.",
                "Golf: A sport where players hit a ball into a series of holes using clubs.",
                "Swimming: A sport of moving through water by using arms and legs.",
                "Cycling: A sport where participants ride bicycles.",
                "Archery: A sport of shooting arrows with a bow.",
                "Chess: A strategic board game played with 16 pieces per player.",
                "Table Tennis: A sport played on a table with small paddles and a ping-pong ball.",
                "Darts: A game where players throw darts at a circular board.",
                "Ludo: A classic board game where players race to reach the finish.",
                "Checkers: A strategy board game played on a checkered board.",
                "Monopoly: A board game where players buy and trade properties.",
                "Carrom: A game where players flick pieces into pockets on a board.",
                "Billiards: A cue sport played with balls and a table.",
                "Bowling: A sport where players roll a ball to knock down pins.",
                "Wrestling: A combat sport where participants fight using holds and grappling.",
                "Kho-Kho: A traditional Indian sport where players chase and tag each other.",
                "Gilli Danda: A traditional Indian game played with two sticks.",
                "Cornhole: A lawn game where players toss bags into a hole in a board.",
                "Tug of War: A game where two teams pull on a rope in opposite directions.",
                "Bocce Ball: A sport where players throw balls towards a target ball.",
                "Hopscotch: A children's game where players hop on a numbered grid.",
                "Sepak Takraw: A sport similar to volleyball, played with a rattan ball."
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

    private void handleGameClick(int gameNumber) {
        // TODO: Implement what should happen when each game button is clicked
        // For now, just speak the game number
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(this, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.US);
                    textToSpeech.speak("Game " + gameNumber, TextToSpeech.QUEUE_FLUSH, null, null);
                }
            });
        } else {
            textToSpeech.speak("Game " + gameNumber, TextToSpeech.QUEUE_FLUSH, null, null);
        }
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
