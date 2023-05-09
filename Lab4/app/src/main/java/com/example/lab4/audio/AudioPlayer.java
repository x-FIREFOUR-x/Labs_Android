package com.example.lab4.audio;

import android.media.MediaPlayer;

public class AudioPlayer {
    private static MediaPlayer instance;

    public static MediaPlayer getInstance(){
        if (instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }

    private static int currentIndex = -1;

    public static void setCurrentIndex(int index){
        currentIndex = index;
    }

    public static int getCurrentIndex(){
        return currentIndex;
    }
}
