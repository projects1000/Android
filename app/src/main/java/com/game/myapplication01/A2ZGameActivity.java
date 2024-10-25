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
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.animation.ValueAnimator; // Add this line

public class A2ZGameActivity extends AppCompatActivity {

    private char currentLetter = 'A';
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

        generateButtons();
    }

    private void generateButtons() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mainLayout);

        List<Character> letters = new ArrayList<>();
        for (char c = 'A'; c <= 'Z'; c++) {
            letters.add(c);
        }
        Collections.shuffle(letters);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        double buttonSizeMargin = 0.0;
        int leftMargin =0;
        int rightMargin =0;

        if (screenWidth <= 720) { // Small screens (width <= 720px)
            buttonSizeMargin = 1.2;  // Adjust this value for medium screens
            leftMargin = 11;
            rightMargin = 2;
        } else if(screenWidth > 720)  //large
        {
            leftMargin = -18;
            rightMargin = -18;
            buttonSizeMargin = 1;  // Adjust this value for large screens
        }

        int numColumns = screenWidth > screenHeight ? 5 : 4; // Adjust columns based on orientation
        int margin = (int) (18 * displayMetrics.density); // Increase the margin for more gap
        int buttonSize = (int) ((screenWidth - (numColumns + 1) * margin) / (numColumns * buttonSizeMargin)); // Decrease button size

        for (int i = 0; i < letters.size(); i++) {
            char letter = letters.get(i);
            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(String.valueOf(letter));
            button.setTextSize(28);
            button.setTypeface(null, Typeface.BOLD);
            button.setBackgroundColor(Color.parseColor("#fa5936"));
            button.setTextColor(Color.WHITE);

            // Set layout parameters with margin
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(buttonSize, buttonSize);
            params.setMargins(margin, margin, margin, margin); // Set margins for gaps
            button.setLayoutParams(params);
            mainLayout.addView(button);

            constraintSet.constrainWidth(button.getId(), buttonSize);
            constraintSet.constrainHeight(button.getId(), buttonSize);

            if (i % numColumns == 0) {
                constraintSet.connect(button.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, margin + (i / numColumns) * (buttonSize + margin));
                constraintSet.connect(button.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, margin + leftMargin); // Increase this value to shift right

            } else {
                constraintSet.connect(button.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, margin + (i / numColumns) * (buttonSize + margin));
                constraintSet.connect(button.getId(), ConstraintSet.LEFT, ((Button) mainLayout.getChildAt(i - 1)).getId(), ConstraintSet.RIGHT, margin + rightMargin);
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
                        if (currentLetter > 'Z') {
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
        instructionsText.setText("Press A to Z");
        instructionsText.setTextSize(26); // Increase text size
        instructionsText.setTextColor(Color.parseColor("#FF5733")); // Change text color
        instructionsText.setTypeface(null, Typeface.ITALIC); // Make it italic
        instructionsText.setGravity(Gravity.END); // Align text to the right
        instructionsText.setPadding(16, 16, 16, 16); // Add some padding

        ConstraintLayout.LayoutParams paramsText = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        paramsText.bottomToBottom = mainLayout.getId(); // Align to the bottom of the main layout
        paramsText.endToEnd = mainLayout.getId(); // Align to the right end of the main layout
        paramsText.setMargins(0, 0, 20, margin * 2); // Add margin from the bottom and 20dp margin from the right

        instructionsText.setLayoutParams(paramsText);
        mainLayout.addView(instructionsText);

// Add animation
        // Create up and down animation
        ObjectAnimator moveUp = ObjectAnimator.ofFloat(instructionsText, "translationY", 0f, -20f);
        ObjectAnimator moveDown = ObjectAnimator.ofFloat(instructionsText, "translationY", -20f, 0f);

//        moveUp.setDuration(5000); // Duration for moving up
//        moveDown.setDuration(5000); // Duration for moving down

        moveUp.setInterpolator(new AccelerateDecelerateInterpolator());
        moveDown.setInterpolator(new AccelerateDecelerateInterpolator());

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 20f); // Move it up and down
        animator.setDuration(1000); // Duration of one cycle
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE); // Repeat infinitely
        animator.setRepeatMode(ValueAnimator.REVERSE); // Reverse animation at the end of each cycle

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                instructionsText.setTranslationY(value); // Move the TextView up and down
            }
        });

        animator.start(); // Start the animation

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
        View dialogView = getLayoutInflater().inflate(R.layout.custom_congratulations_dialog, null);

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

        // Find and configure the Home button
        Button homeButton = dialogView.findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss(); // Close the dialog
                }

                // Navigate to the landing page
                Intent intent = new Intent(A2ZGameActivity.this, HomeActivity.class);
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
            case 'A': return R.raw.a; // Replace with actual file for 'A'
            case 'B': return R.raw.b; // Replace with actual file for 'B'
            case 'C': return R.raw.c; // Replace with actual file for 'C'
            case 'D': return R.raw.d; // Replace with actual file for 'D'
            case 'E': return R.raw.e; // Replace with actual file for 'E'
            case 'F': return R.raw.f; // Replace with actual file for 'F'
            case 'G': return R.raw.g; // Replace with actual file for 'G'
            case 'H': return R.raw.h; // Replace with actual file for 'H'
            case 'I': return R.raw.i; // Replace with actual file for 'I'
            case 'J': return R.raw.j; // Replace with actual file for 'J'
            case 'K': return R.raw.k; // Replace with actual file for 'K'
            case 'L': return R.raw.l; // Replace with actual file for 'L'
            case 'M': return R.raw.m; // Replace with actual file for 'M'
            case 'N': return R.raw.n; // Replace with actual file for 'N'
            case 'O': return R.raw.o; // Replace with actual file for 'O'
            case 'P': return R.raw.p; // Replace with actual file for 'P'
            case 'Q': return R.raw.q; // Replace with actual file for 'Q'
            case 'R': return R.raw.r; // Replace with actual file for 'R'
            case 'S': return R.raw.s; // Replace with actual file for 'S'
            case 'T': return R.raw.t; // Replace with actual file for 'T'
            case 'U': return R.raw.u; // Replace with actual file for 'U'
            case 'V': return R.raw.v; // Replace with actual file for 'V'
            case 'W': return R.raw.w; // Replace with actual file for 'W'
            case 'X': return R.raw.x; // Replace with actual file for 'X'
            case 'Y': return R.raw.y; // Replace with actual file for 'Y'
            case 'Z': return R.raw.z; // Replace with actual file for 'Z'
            default: return R.raw.b; // Fallback if no specific sound is found
        }
    }

    private void restartGame() {
        currentLetter = 'A';
        mainLayout.removeAllViews();
        generateButtons();

        if (backgroundMusicPlayer != null && !backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.start();
        }
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
                        MediaPlayer wrongLetterSoundPlayer = MediaPlayer.create(A2ZGameActivity.this, getSoundResourceForLetter(correctLetter));
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
