package com.game.myapplication01;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class IdentifyGamesActivity extends AppCompatActivity {

    private ImageView carView, completionView;
    private TextView resultText, carLetterText, largeWordText;
    private Button btnG, btnD;
    private String targetWord = "GOD";
    private String currentWord = "";
    private boolean isGReached = false;
    private boolean isDReached = false;
    private float downX, downY;

    @SuppressLint("ClickableViewAccessibility")
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
        setContentView(R.layout.games_large);

        // Initialize Views
        carView = findViewById(R.id.carView);
        completionView = findViewById(R.id.completionView);
        resultText = findViewById(R.id.resultText);
        carLetterText = findViewById(R.id.carLetterText);  // This will display the letter "O" with the car
        largeWordText = findViewById(R.id.largeWordText);  // Large TextView for "GOD"
        btnG = findViewById(R.id.btnG);
        btnD = findViewById(R.id.btnD);
        completionView.setVisibility(View.INVISIBLE); // Hide initially
        largeWordText.setVisibility(View.INVISIBLE); // Hide initially

        // Set the letter "O" to move with the car
        carLetterText.setText("O");

        // Set Car Dragging Listener
        carView.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float moveX = motionEvent.getRawX() - downX;
                    float moveY = motionEvent.getRawY() - downY;
                    view.setX(moveX);
                    view.setY(moveY);
                    carLetterText.setX(moveX + view.getWidth() / 2 - carLetterText.getWidth() / 2); // Center "O" on car
                    carLetterText.setY(moveY - carLetterText.getHeight()); // Position "O" slightly above the car

                    checkProximity();
                    break;
            }
            return true;
        });
    }

    private void checkProximity() {
        // Get button coordinates
        int[] gLocation = new int[2];
        btnG.getLocationOnScreen(gLocation);
        int[] dLocation = new int[2];
        btnD.getLocationOnScreen(dLocation);

        // Get car coordinates
        int[] carLocation = new int[2];
        carView.getLocationOnScreen(carLocation);

        // Check if car is near button "G" and not already reached
        if (!isGReached && isNear(carLocation, gLocation)) {
            btnG.setBackgroundColor(Color.GREEN);
            isGReached = true;
            currentWord += "G";
            resultText.setText("Good! Now move to 'D'");
        }
        // Check if car is near button "D" and "G" is already reached
        if (isGReached && !isDReached && isNear(carLocation, dLocation)) {
            btnD.setBackgroundColor(Color.GREEN);
            isDReached = true;
            currentWord += "D";
            resultText.setText("Well done! You spelled GOD");

            // Show the large "GOD" text, hide car, and show completion image
            largeWordText.setVisibility(View.VISIBLE);
            largeWordText.setText("GOD"); // Display "GOD" at the top
            carView.setVisibility(View.INVISIBLE);
            carLetterText.setVisibility(View.INVISIBLE);
            completionView.setVisibility(View.VISIBLE);
        }
    }

    private boolean isNear(int[] carLocation, int[] targetLocation) {
        int carX = carLocation[0];
        int carY = carLocation[1];
        int targetX = targetLocation[0];
        int targetY = targetLocation[1];

        // Define a threshold distance
        int threshold = 100;
        return Math.abs(carX - targetX) < threshold && Math.abs(carY - targetY) < threshold;
    }
}
