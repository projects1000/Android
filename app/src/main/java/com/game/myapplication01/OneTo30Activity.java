package com.game.myapplication01;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OneTo30Activity extends AppCompatActivity {

    private int currentLetter = 1;
    private MediaPlayer mediaPlayer;
    private MediaPlayer backgroundMusicPlayer;
    private ConstraintLayout mainLayout;
    private List<Integer> colors;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set full-screen mode
        MusicManager.pauseMusic();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
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

        List<Integer> letters = new ArrayList<>();
        for (int c = 1; c <= 30; c++) {
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
        int textSize =0;

        if (screenWidth <= 720) { // Small screens (width <= 720px)
            buttonSizeMargin = 1.5;  // Adjust this value for medium screens
            leftMargin = 27;
            rightMargin = 29;
            textSize = 23;
        } else if(screenWidth > 720)  //large
        {
            leftMargin = -18;
            rightMargin = -18;
            buttonSizeMargin = 1;  // Adjust this value for large screens
            textSize = 28;
        }
        int numColumns = screenWidth > screenHeight ? 5 : 4; // Adjust columns based on orientation
        int margin = (int) (8 * displayMetrics.density); // Dynamic margin based on screen density
        int buttonSize = (int) ((screenWidth - (numColumns + 1) * margin) / (numColumns * buttonSizeMargin)); // Decrease button size

        for (int i = 0; i < letters.size(); i++) {
            int letter = letters.get(i);
            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(String.valueOf(letter));
            button.setTextSize(textSize); // Adjusted to be smaller
            button.setTypeface(null, Typeface.BOLD);
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
                constraintSet.connect(button.getId(), ConstraintSet.LEFT, mainLayout.getId(), ConstraintSet.LEFT, margin + leftMargin); // Increase this value to shift right

            } else {
                constraintSet.connect(button.getId(), ConstraintSet.TOP, mainLayout.getId(), ConstraintSet.TOP, margin + (i / numColumns) * (buttonSize + margin));
                constraintSet.connect(button.getId(), ConstraintSet.LEFT, ((Button) mainLayout.getChildAt(i - 1)).getId(), ConstraintSet.RIGHT, margin + rightMargin);
            }

            final int currentChar = letter;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentChar == currentLetter) {
                        button.setVisibility(View.INVISIBLE);
                        mediaPlayer.start(); // This is for the burst sound, keep it if needed
                        playLetterSound(currentChar); // Play the letter sound
                        currentLetter++;
                        if (currentLetter > 30) {
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
        instructionsText.setText("Press 1 to 30");
        instructionsText.setTextSize(26);
        instructionsText.setTextColor(Color.parseColor("#FF5733"));
        instructionsText.setTypeface(null, Typeface.ITALIC);
        instructionsText.setGravity(Gravity.END);
        instructionsText.setPadding(16, 16, 16, 16);

        ConstraintLayout.LayoutParams paramsText = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        paramsText.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsText.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsText.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
        paramsText.setMargins(230, 0, 0, margin * 2);

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
//        animator.setDuration(1000); // Duration of one cycle
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
        View dialogView = getLayoutInflater().inflate(R.layout.custom_congratulations_dialog_oneto30, null);

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
                Intent intent = new Intent(OneTo30Activity.this, HomeActivity.class);
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

    private int getSoundResourceForLetter(int letter) {
        switch (letter) {
            case 1: return R.raw.one;     // Replace with actual file for '1'
            case 2: return R.raw.two;     // Replace with actual file for '2'
            case 3: return R.raw.three;   // Replace with actual file for '3'
            case 4: return R.raw.four;    // Replace with actual file for '4'
            case 5: return R.raw.five;    // Replace with actual file for '5'
            case 6: return R.raw.six;     // Replace with actual file for '6'
            case 7: return R.raw.seven;   // Replace with actual file for '7'
            case 8: return R.raw.eight;   // Replace with actual file for '8'
            case 9: return R.raw.nine;    // Replace with actual file for '9'
            case 10: return R.raw.ten;    // Replace with actual file for '10'
            case 11: return R.raw.eleven; // Replace with actual file for '11'
            case 12: return R.raw.twelve; // Replace with actual file for '12'
            case 13: return R.raw.thirteen; // Replace with actual file for '13'
            case 14: return R.raw.fourteen; // Replace with actual file for '14'
            case 15: return R.raw.fifteen; // Replace with actual file for '15'
            case 16: return R.raw.sixteen; // Replace with actual file for '16'
            case 17: return R.raw.seventeen; // Replace with actual file for '17'
            case 18: return R.raw.eighteen; // Replace with actual file for '18'
            case 19: return R.raw.nineteen; // Replace with actual file for '19'
            case 20: return R.raw.twenty;  // Replace with actual file for '20'
            case 21: return R.raw.twentyone; // Replace with actual file for '21'
            case 22: return R.raw.twentytwo; // Replace with actual file for '22'
            case 23: return R.raw.twentythree; // Replace with actual file for '23'
            case 24: return R.raw.twentyfour; // Replace with actual file for '24'
            case 25: return R.raw.twentyfive; // Replace with actual file for '25'
            case 26: return R.raw.twentysix; // Replace with actual file for '26'
            case 27: return R.raw.twentyseven; // Replace with actual file for '27'
            case 28: return R.raw.twentyeight; // Replace with actual file for '28'
            case 29: return R.raw.twentynine; // Replace with actual file for '29'
            case 30: return R.raw.thirty;   // Replace with actual file for '30'
            default: return R.raw.one; // Fallback if no specific sound is found
        }

    }

    private void restartGame() {
        currentLetter = 1;
        mainLayout.removeAllViews();
        generateButtons();

        if (backgroundMusicPlayer != null && !backgroundMusicPlayer.isPlaying()) {
            backgroundMusicPlayer.start();
        }
    }

    private void showErrorDialog(final int correctLetter) {
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
                        MediaPlayer wrongLetterSoundPlayer = MediaPlayer.create(OneTo30Activity.this, getSoundResourceForLetter(correctLetter));
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



    private void playLetterSound(int letter) {
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
