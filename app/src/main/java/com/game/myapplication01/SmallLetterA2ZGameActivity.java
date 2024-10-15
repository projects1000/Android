package com.game.myapplication01;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
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

public class SmallLetterA2ZGameActivity extends AppCompatActivity {

    // Change currentLetter to 'a'
    private char currentLetter = 'a';
    private MediaPlayer mediaPlayer;
    private MediaPlayer backgroundMusicPlayer;
    private ConstraintLayout mainLayout;
    private List<Integer> colors;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer(); // Create an empty MediaPlayer
        backgroundMusicPlayer = MediaPlayer.create(this, R.raw.background_music);
        backgroundMusicPlayer.setLooping(true);
        backgroundMusicPlayer.setVolume(0.15f, 0.15f); // Set background music volume lower (0.0f to 1.0f)
        backgroundMusicPlayer.start();

        mainLayout = findViewById(R.id.mainLayout);

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

        generateButtons();  // Generate lowercase buttons
    }

    private void generateButtons() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mainLayout);

        // Adjust to lowercase letters
        List<Character> letters = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            letters.add(c);
        }
        Collections.shuffle(letters);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        int numColumns = screenWidth > screenHeight ? 5 : 4; // Adjust columns based on orientation
        int margin = (int) (8 * displayMetrics.density); // Dynamic margin based on screen density
        int buttonSize = (screenWidth - (numColumns + 1) * margin) / numColumns;

        for (int i = 0; i < letters.size(); i++) {
            char letter = letters.get(i);
            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(String.valueOf(letter));
            button.setAllCaps(false);  // Disable automatic capitalization
            button.setTextSize(38); // Adjusted to be smaller
            button.setTypeface(null, android.graphics.Typeface.BOLD);
            button.setBackgroundColor(Color.parseColor("#fa5936"));
            button.setTextColor(Color.WHITE);

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(buttonSize, buttonSize);
            params.setMargins(margin, margin, margin, margin);
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
                        mediaPlayer.start(); // This is for the burst sound, keep it if needed
                        playLetterSound(currentChar); // Play the letter sound
                        currentLetter++;
                        if (currentLetter > 'z') {
                            showCompletionDialog();
                        }
                    } else {
                        showErrorDialog(currentLetter);
                    }
                }
            });
        }

        TextView instructionsText = new TextView(this);
        instructionsText.setId(View.generateViewId());
        instructionsText.setText("Press a to z");
        instructionsText.setTextSize(24);
        instructionsText.setTextColor(Color.parseColor("#8B4513"));
        instructionsText.setTypeface(null, Typeface.BOLD);
        instructionsText.setGravity(Gravity.CENTER);
        instructionsText.setBackgroundColor(Color.LTGRAY);
        instructionsText.setPadding(16, 16, 16, 16);

        ConstraintLayout.LayoutParams paramsText = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        paramsText.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsText.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsText.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsText.setMargins(0, 0, 0, margin * 2);

        instructionsText.setLayoutParams(paramsText);
        mainLayout.addView(instructionsText);

        View separationLine = new View(this);
        separationLine.setId(View.generateViewId());
        ConstraintLayout.LayoutParams lineParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, 2);
        lineParams.bottomToTop = instructionsText.getId();
        lineParams.setMargins(0, 0, 0, margin * 2);
        separationLine.setLayoutParams(lineParams);
        separationLine.setBackgroundColor(Color.BLACK);
        mainLayout.addView(separationLine);

        constraintSet.applyTo(mainLayout);
    }
    private void showErrorDialog(final char correctLetter) {
        // Inflate the custom dialog layout
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

        // Set the text of the dialog
        TextView dialogText = dialogView.findViewById(R.id.dialogText);
        dialogText.setText(""+correctLetter);

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

        // Play the "Click the letter" sound
        MediaPlayer clickSoundPlayer = MediaPlayer.create(this, R.raw.click_sound);
        clickSoundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release the MediaPlayer resources when done

                // Use a Handler to introduce a short delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Play the wrong letter sound
                        MediaPlayer wrongLetterSoundPlayer = MediaPlayer.create(SmallLetterA2ZGameActivity.this, getSoundResourceForLetter(correctLetter));
                        wrongLetterSoundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release(); // Release the MediaPlayer resources when done
                            }
                        });
                        wrongLetterSoundPlayer.start(); // Play the sound for the wrong letter
                    }
                }, 10); // 100 milliseconds delay
            }
        });
        clickSoundPlayer.start(); // Play the "Click the letter" sound
    }
    private void showCompletionDialog() {
        // Stop background music
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.pause(); // Pause the background music
        }

        // Play clap sound
        MediaPlayer clapSoundPlayer = MediaPlayer.create(this, R.raw.clap); // Assuming clap.mp3 is in res/raw
        clapSoundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release the MediaPlayer resources when done
            }
        });
        clapSoundPlayer.start(); // Play the clap sound

        // Inflate and show completion dialog
        View dialogView = getLayoutInflater().inflate(R.layout.custom_congratulations_dialog_small_letter, null);

        // Find and configure the Play Again button
        Button playAgainButton = dialogView.findViewById(R.id.playAgainButtonTest);
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

        // Find and configure the Home button
        Button homeButton = dialogView.findViewById(R.id.homeButtonTest);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss(); // Close the dialog
                }

                // Navigate to the landing page
                Intent intent = new Intent(SmallLetterA2ZGameActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION); // Clears the activity stack and disables animations
                startActivity(intent);

                // Disable transition animation to prevent flicker
                overridePendingTransition(0, 0); // This line disables the transition animation

                finish(); // Finish the current activity immediately to avoid rendering the game page
            }
        });

        // Create and show the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        dialog = builder.create();

        // Make the dialog full-screen
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent); // Transparent background
        dialog.show();

        // Force the dialog to fill the entire screen
        dialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
    }
    private int getSoundResourceForLetter(char letter) {
        switch (letter) {
            case 'a': return R.raw.a;
            case 'b': return R.raw.b;
            case 'c': return R.raw.c;
            case 'd': return R.raw.d;
            case 'e': return R.raw.e;
            case 'f': return R.raw.f;
            case 'g': return R.raw.g;
            case 'h': return R.raw.h;
            case 'i': return R.raw.i;
            case 'j': return R.raw.j;
            case 'k': return R.raw.k;
            case 'l': return R.raw.l;
            case 'm': return R.raw.m;
            case 'n': return R.raw.n;
            case 'o': return R.raw.o;
            case 'p': return R.raw.p;
            case 'q': return R.raw.q;
            case 'r': return R.raw.r;
            case 's': return R.raw.s;
            case 't': return R.raw.t;
            case 'u': return R.raw.u;
            case 'v': return R.raw.v;
            case 'w': return R.raw.w;
            case 'x': return R.raw.x;
            case 'y': return R.raw.y;
            case 'z': return R.raw.z;
            default: return R.raw.b; // Fallback
        }
    }

    private void playLetterSound(char letter) {
        int soundResource = getSoundResourceForLetter(letter);
        MediaPlayer letterSoundPlayer = MediaPlayer.create(this, soundResource);
        letterSoundPlayer.setVolume(13.0f, 13.0f); // Set letter sound volume to maximum
        letterSoundPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release(); // Release the MediaPlayer resources when done
            }
        });
        letterSoundPlayer.start(); // Play the sound
    }
    private void restartGame() {
        currentLetter = 'a';
        mainLayout.removeAllViews();
        generateButtons();

        if (backgroundMusicPlayer != null && !backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the background music when the activity is not in the foreground
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume the background music when the activity comes back to the foreground
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.start();
        }
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