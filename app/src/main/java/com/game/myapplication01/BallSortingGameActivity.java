package com.game.myapplication01;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BallSortingGameActivity extends Activity {
    private static final int TUBE_CAPACITY = 4;
    private List<Integer>[] tubes = new ArrayList[3]; // 0: Tube1, 1: Tube2, 2: Tube3
    private LinearLayout[] tubeLayouts = new LinearLayout[3];
    private int selectedTube = -1;
    private MediaPlayer winPlayer;

    private int level = 1;
    private static final int MAX_LEVEL = 3;
    private static final int[][] LEVEL_COLORS = {
        {Color.RED, Color.BLUE}, // Level 1: 2 colors
        {Color.RED, Color.BLUE, Color.GREEN}, // Level 2: 3 colors
        {Color.RED, Color.BLUE, Color.GREEN} // Level 3: 3 colors
    };
    private static final int[] LEVEL_BALLS_PER_COLOR = {3, 3, 4};
    private static final int TUBE_COUNT = 3;
    private Button nextLevelButton;
    private Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball_sorting_game);
        nextLevelButton = new Button(this);
        nextLevelButton.setText("Good Job! Next Level");
        nextLevelButton.setTextSize(26);
        nextLevelButton.setTextColor(Color.WHITE);
        nextLevelButton.setBackgroundResource(R.drawable.button_curved);
        nextLevelButton.setPadding(40, 30, 40, 30);
        nextLevelButton.setVisibility(View.GONE);
        finishButton = new Button(this);
        finishButton.setText("You have completed all levels! Go Back");
        finishButton.setTextSize(24);
        finishButton.setTextColor(Color.WHITE);
        finishButton.setBackgroundResource(R.drawable.button_curved);
        finishButton.setPadding(40, 30, 40, 30);
        finishButton.setVisibility(View.GONE);
        // Instead of adding to root FrameLayout, add to the main LinearLayout in the layout
        LinearLayout mainLayout = findViewById(R.id.mainBallSortingLayout);
        mainLayout.addView(nextLevelButton);
        mainLayout.addView(finishButton);
        nextLevelButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BallSortingGameActivity.class);
            intent.putExtra("level", level+1);
            startActivity(intent);
            finish();
        });
        finishButton.setOnClickListener(v -> {
            finish();
        });
        level = getIntent().getIntExtra("level", 1);
        setupTubesAndBalls();
        setupTubeListeners();
    }

    private void setupTubesAndBalls() {
        tubes = new ArrayList[TUBE_COUNT];
        tubeLayouts = new LinearLayout[TUBE_COUNT];
        for (int i = 0; i < TUBE_COUNT; i++) {
            int resId = getResources().getIdentifier("tube" + (i+1), "id", getPackageName());
            tubeLayouts[i] = findViewById(resId);
            if (tubeLayouts[i] != null) tubeLayouts[i].setVisibility(View.VISIBLE);
        }
        // Prepare balls
        List<Integer> balls = new ArrayList<>();
        for (int color : LEVEL_COLORS[level-1]) {
            for (int i = 0; i < LEVEL_BALLS_PER_COLOR[level-1]; i++) balls.add(color);
        }
        Collections.shuffle(balls);
        // Distribute balls to tubes (all but last tube)
        int[] tubeSizes = new int[TUBE_COUNT-1];
        int totalBalls = balls.size();
        // Distribute as evenly as possible
        for (int i = 0; i < tubeSizes.length; i++) tubeSizes[i] = totalBalls / (TUBE_COUNT-1);
        for (int i = 0; i < totalBalls % (TUBE_COUNT-1); i++) tubeSizes[i]++;
        int idx = 0;
        for (int i = 0; i < TUBE_COUNT-1; i++) {
            tubes[i] = new ArrayList<>();
            for (int j = 0; j < tubeSizes[i]; j++) {
                tubes[i].add(balls.get(idx++));
            }
        }
        tubes[TUBE_COUNT-1] = new ArrayList<>(); // Last tube empty
        updateTubes();
    }

    private void setupTubeListeners() {
        for (int i = 0; i < tubeLayouts.length; i++) {
            final int tubeIndex = i;
            tubeLayouts[i].setOnClickListener(v -> onTubeClicked(tubeIndex));
        }
    }

    private void onTubeClicked(int tubeIndex) {
        if (selectedTube == -1) {
            // Pick
            if (tubes[tubeIndex].isEmpty()) {
                Toast.makeText(this, "Tube is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            selectedTube = tubeIndex;
            tubeLayouts[tubeIndex].setBackgroundResource(R.drawable.tube_selected_bg);
        } else if (selectedTube == tubeIndex) {
            // Deselect
            tubeLayouts[selectedTube].setBackgroundResource(R.drawable.tube_bg);
            selectedTube = -1;
        } else {
            // Drop
            int color = tubes[selectedTube].remove(tubes[selectedTube].size() - 1);
            tubes[tubeIndex].add(color);
            animateBallMove(selectedTube, tubeIndex, color);
            tubeLayouts[selectedTube].setBackgroundResource(R.drawable.tube_bg);
            selectedTube = -1;
            updateTubes();
            if (checkWin()) {
                playWinSound();
                if (level < MAX_LEVEL) {
                    nextLevelButton.setVisibility(View.VISIBLE);
                } else {
                    finishButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void animateBallMove(int from, int to, int color) {
        // Simple fade animation for now (can be improved)
        // ...
    }

    private void updateTubes() {
        for (int i = 0; i < tubeLayouts.length; i++) {
            tubeLayouts[i].removeAllViews();
            for (int j = tubes[i].size() - 1; j >= 0; j--) {
                View ball = new View(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(80, 80);
                lp.setMargins(0, 12, 0, 0);
                ball.setLayoutParams(lp);
                int color = tubes[i].get(j);
                if (color == Color.RED) ball.setBackgroundResource(R.drawable.red_ball);
                else if (color == Color.BLUE) ball.setBackgroundResource(R.drawable.blue_ball);
                else if (color == Color.GREEN) ball.setBackgroundResource(R.drawable.green_ball);
                else if (color == Color.YELLOW) ball.setBackgroundResource(R.drawable.yellow_ball);
                tubeLayouts[i].addView(ball);
            }
        }
    }

    private boolean checkWin() {
        int colorCount = LEVEL_COLORS[level-1].length;
        int ballsPerColor = LEVEL_BALLS_PER_COLOR[level-1];
        int tubesWithColor = 0;
        for (List<Integer> tube : tubes) {
            if (tube.size() == ballsPerColor && allColor(tube, tube.get(0))) {
                tubesWithColor++;
            } else if (!tube.isEmpty() && (!allColor(tube, tube.get(0)) || tube.size() != ballsPerColor)) {
                return false;
            }
        }
        return tubesWithColor == colorCount;
    }

    private boolean allColor(List<Integer> tube, int color) {
        for (int c : tube) if (c != color) return false;
        return true;
    }

    private void playWinSound() {
        if (winPlayer == null) {
            winPlayer = MediaPlayer.create(this, R.raw.congas);
        }
        winPlayer.start();
    }

    @Override
    protected void onDestroy() {
        if (winPlayer != null) winPlayer.release();
        super.onDestroy();
    }
}
