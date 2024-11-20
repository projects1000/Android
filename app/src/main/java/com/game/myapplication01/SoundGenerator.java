package com.game.myapplication01;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class SoundGenerator {

    public static void generateCarSound() {
        // Example of generating a simple tone (You can replace this with more complex audio generation logic)
        int sampleRate = 44100; // 44.1 kHz sample rate
        int duration = 1000; // 1 second duration
        int frequency = 440; // Frequency of the sound (A4 note)

        int numSamples = duration * sampleRate / 1000;
        short[] soundData = new short[numSamples];

        // Generate sound (sine wave)
        for (int i = 0; i < numSamples; i++) {
            soundData[i] = (short) (Math.sin(2 * Math.PI * i / (sampleRate / frequency)) * Short.MAX_VALUE);
        }

        // Play sound using AudioTrack
        AudioTrack audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                numSamples * 2, // 2 bytes per sample (16-bit audio)
                AudioTrack.MODE_STATIC
        );

        audioTrack.write(soundData, 0, soundData.length);
        audioTrack.play();
    }
}
