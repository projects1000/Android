package com.game.myapplication01;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity implements OnInitListener {

    private ImageView questionImageView; // ImageView for displaying the GIF
    private GridLayout optionsLayout;
    private int currentQuestionIndex = 0;
    private List<Question> questions; // Question class holding question, options, and correct answer
    private MediaPlayer correctAnswerPlayer;
    private MediaPlayer incorrectAnswerPlayer;
    private TextToSpeech textToSpeech; // Text-to-Speech object
    private boolean isTTSReady = false; // Flag to check if TTS is initialized

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_layout);

        // Initialize views
        questionImageView = findViewById(R.id.imageQuestion); // Use the ImageView for the GIF
        optionsLayout = findViewById(R.id.optionsLayout);

        // Initialize media players
        correctAnswerPlayer = MediaPlayer.create(this, R.raw.a);
        incorrectAnswerPlayer = MediaPlayer.create(this, R.raw.b);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, this);

        // Initialize questions list
        questions = new ArrayList<>();
        loadQuestions();

        // Display the first question
        displayQuestion();
    }

    private void loadQuestions() {
        // Populate the questions list
        questions.add(new Question("What is 2 + 2?",
                new ArrayList<>(Arrays.asList("3", "4", "5", "6")), "4"));
        questions.add(new Question("What is the capital of France?",
                new ArrayList<>(Arrays.asList("Berlin", "Madrid", "Paris", "Rome")), "Paris"));
        questions.add(new Question("Who wrote 'Hamlet'?",
                new ArrayList<>(Arrays.asList("Shakespeare", "Hemingway", "Austen", "Tolkien")), "Shakespeare"));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new ArrayList<>(Arrays.asList("Earth", "Mars", "Jupiter", "Saturn")), "Mars"));
        questions.add(new Question("What is the square root of 16?",
                new ArrayList<>(Arrays.asList("2", "4", "6", "8")), "4"));
    }

    private void displayQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            // All questions answered, show the congratulations message
            showCongratulations();
            return;
        }

        // Get the current question
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Hide the question image (reset)
        questionImageView.setVisibility(View.GONE);

        // Remove all previous options
        optionsLayout.removeAllViews();

        // Shuffle the options
        List<String> options = new ArrayList<>(currentQuestion.getOptions());
        Collections.shuffle(options);

        // Layout configuration
        int columnCount = 2; // Two columns for symmetry
        optionsLayout.setColumnCount(columnCount);

        // Display options in buttons
        for (String option : options) {
            Button optionButton = new Button(this);
            optionButton.setText(option);
            // Customize button appearance
            optionButton.setBackgroundColor(Color.rgb(139, 69, 19)); // Brown background
            optionButton.setTextColor(Color.WHITE); // White text
            optionButton.setTextSize(22); // Larger text
            optionButton.setTypeface(null, android.graphics.Typeface.BOLD); // Bold text
            optionButton.setPadding(20, 20, 20, 20);

            // Set layout parameters
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0; // Equal width distribution
            params.height = GridLayout.LayoutParams.WRAP_CONTENT; // Wrap content
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Weight for even column distribution
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED); // Automatically flow to next row
            params.setMargins(8, 8, 8, 8); // Add margin for spacing
            optionButton.setLayoutParams(params);

            // Add click listener
            optionButton.setOnClickListener(v -> {
                if (option.equals(currentQuestion.getCorrectAnswer())) {
                    // Play correct answer sound
                    correctAnswerPlayer.start();
                    // Wait 1 second before moving to next question
                    new Handler().postDelayed(() -> {
                        currentQuestionIndex++;
                        displayQuestion();
                    }, 1000); // 1 second delay before showing the next question
                } else {
                    // Highlight incorrect answer
                    optionButton.setBackgroundColor(Color.RED);
                    incorrectAnswerPlayer.start();
                    new Handler().postDelayed(() -> optionButton.setBackgroundColor(Color.rgb(139, 69, 19)), 500); // Reset to brown
                }
            });

            optionsLayout.addView(optionButton);
        }

        // Load and show the GIF for the current question (if needed)
        Glide.with(this)
                .load(R.drawable.teacher) // Replace with the name of your GIF
                .into(questionImageView);

        // Now speak only the question (no options) using Text-to-Speech
        if (isTTSReady) {
            String questionText = currentQuestion.getQuestionText(); // Just the question
            // Speak the question text
            textToSpeech.speak(questionText, TextToSpeech.QUEUE_FLUSH, null, "question");
        }
    }

    private void showCongratulations() {
        // Display the congratulations message on the screen
        questionImageView.setVisibility(View.VISIBLE);
        questionImageView.setImageResource(R.drawable.congratulations_icon); // You can display a congratulatory image here
        optionsLayout.removeAllViews();
        if (isTTSReady) {
            textToSpeech.speak("Well done!", TextToSpeech.QUEUE_FLUSH, null, "well_done");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (correctAnswerPlayer != null) {
            correctAnswerPlayer.release();
        }
        if (incorrectAnswerPlayer != null) {
            incorrectAnswerPlayer.release();
        }

        // Shutdown Text-to-Speech
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        // This callback is called when the TextToSpeech engine is initialized
        if (status == TextToSpeech.SUCCESS) {
            int langResult = textToSpeech.setLanguage(Locale.US);
            if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TextToSpeech", "Language is not supported or missing data");
            } else {
                Log.i("TextToSpeech", "Text-to-Speech initialization successful");
                isTTSReady = true; // Mark TTS as ready
                displayQuestion();
            }
        } else {
            Log.e("TextToSpeech", "Initialization failed");
        }
    }
}
