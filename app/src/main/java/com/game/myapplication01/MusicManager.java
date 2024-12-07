package com.game.myapplication01;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.speech.tts.TextToSpeech;
import java.util.Locale;
public class MusicManager {
    private static MediaPlayer mediaPlayer;
    private static final String TAG = "MusicManager";
    private static TextToSpeech textToSpeech;

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

    // Initialize TextToSpeech and provide callback
    public static void initializeTextToSpeech(Context context, TextToSpeech.OnInitListener listener) {
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(context, status -> {
                if (status == TextToSpeech.SUCCESS) {
                    int langResult = textToSpeech.setLanguage(Locale.US);
                    if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(TAG, "Language data missing or language not supported.");
                    } else {
                        textToSpeech.setSpeechRate(0.5f); // Slow down the speech rate
                        if (listener != null) listener.onInit(status); // Notify the listener if provided
                    }
                } else {
                    Log.e(TAG, "TextToSpeech initialization failed.");
                    if (listener != null) listener.onInit(status); // Notify failure
                }
            });
        }
    }

    public static TextToSpeech getTextToSpeech() {
        return textToSpeech;
    }
}
