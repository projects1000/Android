//package com.game.myapplication01;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.GridLayout;
//import android.widget.ImageView;
//import androidx.appcompat.app.AppCompatActivity;
//import java.util.Collections;
//import java.util.ArrayList;
//
//public class NumberPuzzle extends AppCompatActivity {
//
//    private GridLayout gridLayout;
//    private ArrayList<Integer> numbers = new ArrayList<>();
//    private int emptyIndex = 11; // Position of the empty space
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_numberpuzzle);
//
//        gridLayout = findViewById(R.id.gridLayout);
//        Button resetButton = findViewById(R.id.resetButton);
//
//        // Initialize numbers and shuffle
//        for (int i = 1; i <= 11; i++) {
//            numbers.add(i);
//        }
//        numbers.add(0); // 0 represents the empty space
//        Collections.shuffle(numbers);
//
//        setupGrid();
//
//        resetButton.setOnClickListener(v -> resetGame());
//    }
//
//    private void setupGrid() {
//        gridLayout.removeAllViews();
//        for (int i = 0; i < 12; i++) {
//            ImageView imageView = new ImageView(this);
//            if (numbers.get(i) == 0) {
//                imageView.setBackgroundResource(android.R.color.transparent); // Empty space
//            } else {
//                imageView.setBackgroundResource(R.drawable.square_background); // Placeholder background
//                imageView.setImageResource(getDrawableForNumber(numbers.get(i)));
//                imageView.setTag(i);
//                imageView.setOnClickListener(this::onTileClick);
//            }
//            gridLayout.addView(imageView, new GridLayout.LayoutParams(
//                    GridLayout.spec(i / 4),
//                    GridLayout.spec(i % 4)
//            ));
//        }
//    }
//
//    private int getDrawableForNumber(int number) {
//        // Replace with actual drawable resource IDs for each number
//        switch (number) {
//            case 1: return R.drawable.number1;
//            case 2: return R.drawable.number2;
//            case 3: return R.drawable.number3;
//            case 4: return R.drawable.number4;
//            case 5: return R.drawable.number5;
//            case 6: return R.drawable.number6;
//            case 7: return R.drawable.number7;
//            case 8: return R.drawable.number8;
//            case 9: return R.drawable.number9;
//            case 10: return R.drawable.number10;
//            case 11: return R.drawable.number11;
//            default: return 0; // No drawable for empty space
//        }
//    }
//
//    private void onTileClick(View view) {
//        int clickedIndex = (int) view.getTag();
//        if (canMove(clickedIndex)) {
//            // Swap clicked tile with the empty space
//            Collections.swap(numbers, clickedIndex, emptyIndex);
//            emptyIndex = clickedIndex;
//            setupGrid();
//        }
//    }
//
//    private boolean canMove(int clickedIndex) {
//        // Check if clicked tile is adjacent to the empty space
//        int[] validMoves = {emptyIndex - 1, emptyIndex + 1, emptyIndex - 4, emptyIndex + 4};
//        for (int move : validMoves) {
//            if (move == clickedIndex && move >= 0 && move < 12) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void resetGame() {
//        numbers.clear();
//        for (int i = 1; i <= 11; i++) {
//            numbers.add(i);
//        }
//        numbers.add(0); // Add empty space
//        Collections.shuffle(numbers);
//        setupGrid();
//    }
//}
