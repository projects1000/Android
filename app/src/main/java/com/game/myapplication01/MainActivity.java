package com.game.myapplication01;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private char currentLetter = 'A';
    private MediaPlayer mediaPlayer;
    private MediaPlayer backgroundMusicPlayer; // Declare the MediaPlayer for background music
    private ConstraintLayout mainLayout;
    private List<Integer> colors;
    private AlertDialog dialog; // Declare the AlertDialog variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.burst_sound);

        // Initialize media player for background music
        backgroundMusicPlayer = MediaPlayer.create(this, R.raw.background_music);
        backgroundMusicPlayer.setLooping(true); // Loop the background music
        backgroundMusicPlayer.start(); // Start playing background music

        mainLayout = findViewById(R.id.mainLayout);

        // Define colors
        colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.GRAY);
        colors.add(Color.LTGRAY);
        colors.add(Color.DKGRAY);

        // Generate buttons for A to Z
        generateButtons();
    }

    private void generateButtons() {
        // Create a ConstraintSet object
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mainLayout);  // Clone the layout to apply constraints later

        List<Character> letters = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            letters.add(c);
        }
        Collections.shuffle(letters); // Randomize the order

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;

        int numColumns = 4; // Number of columns
        int margin = 16; // Margin between buttons
        int buttonSize = (screenWidth - (numColumns + 1) * margin) / numColumns; // Calculate size with margin

        for (int i = 0; i < letters.size(); i++) {
            char letter = letters.get(i);
            Button button = new Button(this);
            button.setId(View.generateViewId()); // Generate unique ID for each button
            button.setText(String.valueOf(letter));
            button.setTextSize(29); // Adjust text size (larger)
            button.setTypeface(null, android.graphics.Typeface.BOLD); // Set text to bold
            button.setBackgroundColor(Color.parseColor("#6F4F28")); // Set brown background color
            button.setTextColor(Color.WHITE);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(buttonSize, buttonSize);
            params.setMargins(margin, margin, margin, margin); // Set margins

            button.setLayoutParams(params);
            mainLayout.addView(button);

            constraintSet.constrainWidth(button.getId(), buttonSize);
            constraintSet.constrainHeight(button.getId(), buttonSize);

            if (i % numColumns == 0) {
                constraintSet.connect(button.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, margin + (i / numColumns) * (buttonSize + margin));
            } else {
                constraintSet.connect(button.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, margin + (i / numColumns) * (buttonSize + margin));
                constraintSet.connect(button.getId(), ConstraintSet.LEFT, ((Button) mainLayout.getChildAt(i - 1)).getId(), ConstraintSet.RIGHT, margin);
            }

            final char currentChar = letter;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentChar == currentLetter) {
                        button.setVisibility(View.INVISIBLE);
                        mediaPlayer.start();
                        currentLetter++;
                        if (currentLetter > 'Z') {
                            showCompletionDialog();
                        }
                    } else {
                        showErrorDialog(currentLetter); // Pass the current letter that needs to be clicked
                    }
                }
            });
        }

        TextView instructionsText = new TextView(this);
        instructionsText.setId(View.generateViewId());
        instructionsText.setText("Click A to Z");
        instructionsText.setTextSize(43); // Larger text size
        instructionsText.setTextColor(Color.parseColor("#8B4513")); // Brown color
        instructionsText.setTypeface(null, Typeface.BOLD); // Bold text
        instructionsText.setGravity(Gravity.CENTER); // Center the text
        instructionsText.setBackgroundColor(Color.LTGRAY); // Light gray background (bar effect)
        instructionsText.setPadding(30, 20, 30, 20); // Padding around the text for bar effect

        ConstraintLayout.LayoutParams paramsText = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        paramsText.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID; // Align to the bottom
        paramsText.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID; // Center horizontally
        paramsText.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID; // Center horizontally
        paramsText.setMargins(0, 0, 0, 16); // Margin at the bottom for spacing

        instructionsText.setLayoutParams(paramsText);
        mainLayout.addView(instructionsText);

        View separationLine = new View(this);
        separationLine.setId(View.generateViewId());
        ConstraintLayout.LayoutParams lineParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, 2); // 2dp height
        lineParams.bottomToTop = instructionsText.getId(); // Align above instructions
        lineParams.setMargins(0, 0, 0, 16); // Margin between the line and instructions text
        separationLine.setLayoutParams(lineParams);
        separationLine.setBackgroundColor(Color.BLACK); // Set color
        mainLayout.addView(separationLine);

        constraintSet.applyTo(mainLayout);
    }

    private void showCompletionDialog() {
        // Inflate and show completion dialog
        View dialogView = getLayoutInflater().inflate(R.layout.custom_congratulations_dialog, null);

        // Find and configure the TextView for the congratulations message
        TextView congratsText = dialogView.findViewById(R.id.congratsText);
//        congratsText.setText("Congratulations!");

        // Find and configure the Play Again button
        Button playAgainButton = dialogView.findViewById(R.id.playAgainButton);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss(); // Close the dialog
                }
                // Restart the game
                restartGame();
            }
        });

        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        dialog = builder.create(); // Initialize the dialog
        dialog.show();
    }

    private void restartGame() {
        // Reset the current letter
        currentLetter = 'A';

        // Remove all buttons from the layout
        mainLayout.removeAllViews();

        // Generate new buttons
        generateButtons();
    }

    private void showErrorDialog(final char correctLetter) {
        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

        // Set the text of the dialog
        TextView dialogText = dialogView.findViewById(R.id.dialogText);
        dialogText.setText("Click " + correctLetter);

        // Create and configure the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create(); // Initialize the dialog
        dialog.show();

        // Set up the OK button
        Button dialogButton = dialogView.findViewById(R.id.dialogButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.release();
        }
    }
}
