package com.game.myapplication01;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.Random;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import android.media.MediaPlayer;
import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    private  static final int MY_REQ_CODE =100;
    private TextView welcomeText, mindGameText;
    private Button goInsideButton;
    private ImageView bird1, bird2;
    private Random random;
    private Handler handler;
    private Runnable runnable;

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
        setContentView(R.layout.activity_home);  // Ensure this is activity_home.xml

        bird1 = findViewById(R.id.bird1);
        bird2 = findViewById(R.id.bird2);

        random = new Random();
        handler = new Handler();

        startBirdAnimation(bird1);
        startBirdAnimation(bird2);
//        mediaPlayer = MediaPlayer.create(this, R.raw.startupmusic); // Replace with your file name
//        mediaPlayer.setLooping(true); // Loop the music
//        mediaPlayer.start();

        // Start the music using MusicManager
        MusicManager.startMusic(this);
        Log.w("OnCreate","OnCreate");
        checkForUpdates();


        // Initialize Views
        welcomeText = findViewById(R.id.welcomeText);
        mindGameText = findViewById(R.id.mindGameText);
        goInsideButton = findViewById(R.id.goInsideButton);
        applyPulseAndSwingAnimation(welcomeText);
        applyPulseAndSwingAnimation(mindGameText);
        applyPulseAnimation(goInsideButton);


        Button goInsideButton = findViewById(R.id.goInsideButton);
        goInsideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                    mediaPlayer.pause();
//                }

                Intent intent = new Intent(HomeActivity.this, AZListingActivity.class);
                startActivity(intent);

            }
        });

    }

    private void applyBounceAnimation(View view, float startTranslationX, float startTranslationY) {
        // Fade-in effect with loop
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeIn.setDuration(1000);
        fadeIn.setRepeatCount(ObjectAnimator.INFINITE);
        fadeIn.setRepeatMode(ObjectAnimator.REVERSE);

        // Horizontal movement with loop
        ObjectAnimator moveX = ObjectAnimator.ofFloat(view, "translationX", startTranslationX, 0f);
        moveX.setDuration(1000);
        moveX.setRepeatCount(ObjectAnimator.INFINITE);
        moveX.setRepeatMode(ObjectAnimator.REVERSE);

        // Vertical movement with loop
        ObjectAnimator moveY = ObjectAnimator.ofFloat(view, "translationY", startTranslationY, 0f);
        moveY.setDuration(1000);
        moveY.setRepeatCount(ObjectAnimator.INFINITE);
        moveY.setRepeatMode(ObjectAnimator.REVERSE);

        // Combine animations in a set
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fadeIn, moveX, moveY);
        animatorSet.start();
    }

    private void applyPulseAndSwingAnimation(View view) {
        // Scale animation for slight magnification and reverse
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.1f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.1f, 1f);
        scaleX.setDuration(800);
        scaleY.setDuration(800);

        // Slight left-right movement for a swing effect
        ObjectAnimator moveX = ObjectAnimator.ofFloat(view, "translationX", -10f, 10f, -10f);
        moveX.setDuration(800);

        // Repeat all animations infinitely with reverse
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);
        moveX.setRepeatCount(ObjectAnimator.INFINITE);
        moveX.setRepeatMode(ObjectAnimator.REVERSE);

        // Combine all animations
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, moveX);
        animatorSet.start();
    }

    private void applyPulseAnimation(Button button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 1.1f, 1f);
        scaleX.setDuration(800);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 1.1f, 1f);
        scaleY.setDuration(800);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);

        AnimatorSet pulse = new AnimatorSet();
        pulse.playTogether(scaleX, scaleY);
        pulse.start();
    }


    private void checkForUpdates() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
        Log.i("UpdateCheck", "Checking for updates...");

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            // Check if an update is available and if immediate updates are allowed
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            this,
                            MY_REQ_CODE
                    );
                } catch (IntentSender.SendIntentException e) {
                    Log.e("UpdateCheck", "Error starting update flow", e);
                }
            } else {
                Log.i("UpdateCheck", "No updates available or immediate updates not allowed.");
            }
        }).addOnFailureListener(e -> {
            Log.e("UpdateCheck", "Failed to check for updates", e);
        });
    }
    private void startBirdAnimation(ImageView bird) {
        bird.setVisibility(View.VISIBLE);
        moveBird(bird);
    }

    private void moveBird(final ImageView bird) {
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final float startX = random.nextInt(width - 100);
        final float startY = random.nextInt(height - 100);
        final float endX = random.nextInt(width - 100);
        final float endY = random.nextInt(height - 100);

        bird.setX(startX);
        bird.setY(startY);

        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(bird, "x", endX);
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(bird, "y", endY);

        xAnimator.setDuration(3000);
        yAnimator.setDuration(3000);

        xAnimator.setInterpolator(new LinearInterpolator());
        yAnimator.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(xAnimator, yAnimator);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                moveBird(bird); // Recursively call to create a continuous movement
            }
        });

        animatorSet.start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                Log.i("HomeActivity", "Update flow succeeded!");
                clearCache();
            } else {
                Log.w("HomeActivity", "Update flow failed! Result Code: " + resultCode);
            }
        }
    }

    private void clearCache() {
        try {
            File cacheDir = getCacheDir();
            deleteDir(cacheDir);
            Log.i("HomeActivity", "Cache cleared successfully.");
        } catch (Exception e) {
            Log.e("HomeActivity", "Failed to clear cache", e);
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        return dir != null && dir.delete();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) { // Check if the activity is closing
            MusicManager.pauseMusic();
        }
        MusicManager.pauseMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (!MusicManager.isPlaying()) {  // Only start if music is not already playing
                MusicManager.startMusic(this);
            }
        }, 1000); // Adjust delay as needed, or remove if not necessary
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("onPause","onPause");
        MusicManager.stopMusic(); // Stop the music only when the app closes completely
    }

}