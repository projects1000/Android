package com.game.myapplication01;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class MusicManager {
    private static MediaPlayer mediaPlayer;
    private static final String TAG = "MusicManager";

    // Start music with synchronized control to prevent overlapping calls
    public static synchronized void startMusic(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.welcomesong);
            mediaPlayer.setLooping(true);
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Log.e(TAG, "MediaPlayer error occurred: what=" + what + ", extra=" + extra);
                resetMediaPlayer();
                return true;
            });
        }
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public static synchronized void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public static synchronized void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            resetMediaPlayer();
        }
    }

    public static boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    private static void resetMediaPlayer() {
        if (mediaPlayer != null) {
            // Optional DRM cleanup if your app uses DRM
            // mediaPlayer.releaseDrm();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d(TAG, "MediaPlayer has been released and set to null");
        }
    }
}
