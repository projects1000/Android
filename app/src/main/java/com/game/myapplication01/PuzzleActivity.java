package com.game.myapplication01;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PuzzleActivity extends AppCompatActivity {

    private Button buttonDisplayedWord;
    private LinearLayout lettersLayout;
    private ImageView imageAnimal;
    private Button startAgainButton;

    private Button backToHomeButton;
    private String[] words = {
            "DOG", "CAT", "TIGER", "PARROT", "FROG",
            "APPLE", "BANANA", "BED", "BOAT", "BUS",
            "CAR", "FAN", "HORSE", "LION", "MANGO",
            "PEN", "POTATO", "TAXI", "TOMATO", "VAN",
            "ZEBRA", "LOTUS", "DONKEY"
    };
    private String currentWord;
    private String missingLetter;
    private int currentWordIndex;
    private TextView instructionText;

    private TextToSpeech textToSpeech;

    private MediaPlayer redBuzzSound;
    private Vibrator vibrator;
    private float buttonTextSize;
    private int buttonWidth;
    private int buttonHeight;
    private int margin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout based on screen size
        String layout = getLayoutForScreenSize();
        setContentView(getResources().getIdentifier(layout, "layout", getPackageName()));

        buttonDisplayedWord = findViewById(R.id.buttonDisplayedWord);
        lettersLayout = findViewById(R.id.lettersLayout);
        imageAnimal = findViewById(R.id.imageAnimal);
        startAgainButton = findViewById(R.id.startAgainButton);
        instructionText = findViewById(R.id.instructionText);
        backToHomeButton = findViewById(R.id.backToHomeButton);

        setupInstructionText();

        redBuzzSound = MediaPlayer.create(this, R.raw.wrong_buzz);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        startAgainButton.setOnClickListener(v -> {
            currentWordIndex = 0;
            setupGame();
            backToHomeButton.setVisibility(View.GONE);
            startAgainButton.setVisibility(View.GONE);
        });

        currentWordIndex = 0;
        setupGame();

        // Initialize TextToSpeech
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(this, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setSpeechRate(0.7f); // Slow down the speech rate

                    int langResult = textToSpeech.setLanguage(Locale.US);
                    if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Handle missing data or unsupported language
                    }
                }
            });
        }



    }

    private String getLayoutForScreenSize() {
        // Get the screen width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        // Set the button text size based on screen size
        if (screenWidth <= 720) {  // Small screens
            buttonTextSize = 22f;
            buttonWidth = 107;  // Width in pixels
            buttonHeight = 100;  // Height in pixels
            margin  =15;
        } else {  // Large screens
            buttonTextSize = 30f;
            buttonWidth = 190;  // Width in pixels
            buttonHeight = 190;  // Height in pixels
            margin  =15;
        }

        // Return layout based on screen size
        return screenWidth <= 720 ? "word_puzzle_small" : "word_puzzle_large"; // Assume you have two layouts
    }

    private void setupInstructionText() {
        String instruction = "Click the missing Letter";
        SpannableString spannableInstruction = new SpannableString(instruction);

        spannableInstruction.setSpan(new StyleSpan(Typeface.BOLD), 0, instruction.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableInstruction.setSpan(new ForegroundColorSpan(Color.parseColor("#FF5722")), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableInstruction.setSpan(new ForegroundColorSpan(Color.parseColor("#FFEB3B")), 6, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableInstruction.setSpan(new ForegroundColorSpan(Color.parseColor("#009688")), 14, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        instructionText.setText(spannableInstruction);
        instructionText.setTextSize(30f);
        instructionText.setShadowLayer(5, 2, 2, Color.DKGRAY);
        instructionText.setLetterSpacing(0.1f);
        instructionText.setVisibility(View.VISIBLE);
    }

    private void setupGame() {
        if (currentWordIndex >= words.length) {
            // Game is complete, show a congratulatory message
            buttonDisplayedWord.setText("Congratulations! You've completed the game!");
            buttonDisplayedWord.setTextColor(Color.GREEN); // Optional: set text color to green
            lettersLayout.removeAllViews();
            imageAnimal.setVisibility(View.GONE);
            startAgainButton.setVisibility(View.VISIBLE);  // Show the "Start Again" button
            backToHomeButton.setVisibility(View.VISIBLE);


            // Optionally, provide feedback
            generateVoice("Congratulations! You've completed the game!");
            if (vibrator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)); // Vibration feedback
                }
            }

            // Optionally play a sound or perform additional actions on completion
            MediaPlayer congratulationSound = MediaPlayer.create(this, R.raw.clap);
            congratulationSound.start();
            congratulationSound.setOnCompletionListener(mp -> {
                mp.release(); // Release media player after sound completes
            });

            backToHomeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backToHomeButton.setVisibility(View.GONE);
                    // Assuming the home activity is named MainActivity
                    Intent intent = new Intent(PuzzleActivity.this, AZListingActivity.class);
                    startActivity(intent);
                    finish(); // Optional: to close the current activity
                }
            });

            return;
        }

        // Continue setting up the game if there are more words
        currentWord = words[currentWordIndex];

        generateVoice("Click the missing letter for " + currentWord);

        Random random = new Random();
        int missingIndex = random.nextInt(currentWord.length());
        missingLetter = String.valueOf(currentWord.charAt(missingIndex));

        String displayedWord = currentWord.replace(missingLetter, "_");
        SpannableStringBuilder spannable = new SpannableStringBuilder();

        for (int i = 0; i < displayedWord.length(); i++) {
            char letter = displayedWord.charAt(i);
            int start = spannable.length();
            spannable.append(String.valueOf(letter));

            if (letter == '_') {
                spannable.setSpan(new StyleSpan(Typeface.BOLD), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new ForegroundColorSpan(Color.RED), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannable.setSpan(new ForegroundColorSpan(Color.WHITE), start, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (i < displayedWord.length() - 1) {
                spannable.append(" ");
            }
        }

        buttonDisplayedWord.setText(spannable);
        buttonDisplayedWord.setTypeface(null, Typeface.BOLD);
        buttonDisplayedWord.setBackgroundResource(R.drawable.button_style);
        buttonDisplayedWord.setVisibility(View.VISIBLE);

        setAnimalImage(currentWord);

        List<String> letterOptions = new ArrayList<>();
        letterOptions.add(missingLetter);
        for (int i = 0; i < 3; i++) {
            char randomChar;
            do {
                randomChar = (char) ('A' + random.nextInt(26));
            } while (letterOptions.contains(String.valueOf(randomChar)));
            letterOptions.add(String.valueOf(randomChar));
        }
        Collections.shuffle(letterOptions);

        lettersLayout.removeAllViews();

        for (String letter : letterOptions) {
            Button letterButton = new Button(this);
            letterButton.setText(letter);
//            int margin = (int) (10 * getResources().getDisplayMetrics().density); // Convert dp to pixels
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(buttonWidth, buttonHeight);
            params.setMargins(margin, margin, margin, margin);  // Set margins (left, top, right, bottom)
            letterButton.setLayoutParams(params);     // Add space between buttons using margin (e.g., 10dp)

            letterButton.setTextSize(buttonTextSize);
            letterButton.setTypeface(null, Typeface.BOLD);
            letterButton.setBackgroundColor(getResources().getColor(R.color.green));
            letterButton.setTextColor(getResources().getColor(R.color.white));
            letterButton.setPadding(8, 8, 8, 8);
            letterButton.setBackgroundResource(R.drawable.button_style);

            letterButton.setOnClickListener(v -> {
                // If the clicked letter is correct, disable all the letter buttons
                if (letter.equals(missingLetter)) {
                    // Replace the underscore with the correct letter
                    String updatedWord = buttonDisplayedWord.getText().toString().replace("_", letter);
                    SpannableStringBuilder updatedSpannable = new SpannableStringBuilder();

                    for (int i = 0; i < updatedWord.length(); i++) {
                        char updatedLetter = updatedWord.charAt(i);
                        int start = updatedSpannable.length();
                        updatedSpannable.append(String.valueOf(updatedLetter));

                        if (updatedLetter == letter.charAt(0)) {
                            updatedSpannable.setSpan(new StyleSpan(Typeface.BOLD), start, updatedSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            updatedSpannable.setSpan(new ForegroundColorSpan(Color.GREEN), start, updatedSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // Correct letter color
                        } else {
                            updatedSpannable.setSpan(new ForegroundColorSpan(Color.WHITE), start, updatedSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }

                        if (i < updatedWord.length() - 1) {
                            updatedSpannable.append(" ");
                        }
                    }

                    buttonDisplayedWord.setText(updatedSpannable);

                    // Prepare the formatted prompt for voice: D O G DOG
                    StringBuilder formattedPrompt = new StringBuilder("Correct! ");
                    for (int i = 0; i < currentWord.length(); i++) {
                        formattedPrompt.append(currentWord.charAt(i)).append(" ");
                    }
                    formattedPrompt.append(currentWord);  // Append the full word at the end

                    // Generate the voice with spaced letters and the full word at the end
                    generateVoice(formattedPrompt.toString());

                    // Disable all buttons now that the user has clicked the correct letter
                    for (int i = 0; i < lettersLayout.getChildCount(); i++) {
                        Button letterBtn = (Button) lettersLayout.getChildAt(i);
                        letterBtn.setEnabled(false); // Disable all buttons
                    }

                    currentWordIndex++;

                    // Delay to show the next game (3 seconds delay)
                    new Handler().postDelayed(this::setupGame, 3000); // Wait 3 seconds before showing the next game
                } else {
                    // Incorrect letter handling
                    letterButton.setBackgroundColor(Color.RED);
                    redBuzzSound.start();

                    if (vibrator != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                    }

                    generateVoice("Incorrect, try again!");
                }
            });

            lettersLayout.addView(letterButton);
        }

        imageAnimal.setVisibility(View.VISIBLE);
    }

    private void setAnimalImage(String word) {
        switch (word) {
            case "DOG":
                imageAnimal.setImageResource(R.drawable.dog);
                break;
            case "CAT":
                Glide.with(this).load(R.drawable.catgif).into(imageAnimal);
                break;
            case "TIGER":
                imageAnimal.setImageResource(R.drawable.tiger);
                break;
            case "PARROT":
                imageAnimal.setImageResource(R.drawable.parrot);
                break;
            case "FROG":
                imageAnimal.setImageResource(R.drawable.frog);
                break;
            case "APPLE":
                imageAnimal.setImageResource(R.drawable.apple); // Add apple image
                break;
            case "BANANA":
                imageAnimal.setImageResource(R.drawable.banana); // Add banana image
                break;
            case "BED":
                imageAnimal.setImageResource(R.drawable.bed); // Add bed image
                break;
            case "BOAT":
                imageAnimal.setImageResource(R.drawable.boat); // Add boat image
                break;
            case "BUS":
                imageAnimal.setImageResource(R.drawable.bus); // Add bus image
                break;
            case "CAR":
                imageAnimal.setImageResource(R.drawable.car); // Add car image
                break;
            case "FAN":
                imageAnimal.setImageResource(R.drawable.fan); // Add fan image
                break;
            case "HORSE":
                imageAnimal.setImageResource(R.drawable.horse); // Add horse image
                break;
            case "LION":
                imageAnimal.setImageResource(R.drawable.lion); // Add lion image
                break;
            case "MANGO":
                imageAnimal.setImageResource(R.drawable.mango); // Add mango image
                break;
            case "PEN":
                imageAnimal.setImageResource(R.drawable.pen); // Add pen image
                break;
            case "POTATO":
                imageAnimal.setImageResource(R.drawable.potato); // Add potato image
                break;
            case "TAXI":
                imageAnimal.setImageResource(R.drawable.taxi); // Add taxi image
                break;
            case "TOMATO":
                imageAnimal.setImageResource(R.drawable.tomato); // Add tomato image
                break;
            case "VAN":
                imageAnimal.setImageResource(R.drawable.van); // Add van image
                break;
            case "ZEBRA":
                imageAnimal.setImageResource(R.drawable.zebra); // Add zebra image
                break;
            case "LOTUS":
                imageAnimal.setImageResource(R.drawable.lotus); // Add lotus image
                break;
            case "DONKEY":
                imageAnimal.setImageResource(R.drawable.donkey); // Add donkey image
                break;
            default:
                imageAnimal.setImageResource(0); // No image available for this word
                break;
        }
    }

    private void generateVoice(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        if (redBuzzSound != null) {
            redBuzzSound.release();
        }
    }
}

