package com.example.lab4.audio;

import java.io.Serializable;

public class AudioData implements Serializable {
    private final String path;
    private final String title;
    private final String duration;
    private byte[] bitmap;

    public AudioData(String path, String title, String duration) {
        this.path = path;
        this.title = title;
        this.duration = duration;
        bitmap = null;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }
}
