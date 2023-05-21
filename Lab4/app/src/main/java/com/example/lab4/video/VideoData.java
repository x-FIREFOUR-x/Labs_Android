package com.example.lab4.video;

import android.graphics.Bitmap;

import java.io.Serializable;

public class VideoData implements Serializable {
    private final String path;
    private final String title;
    private Bitmap bitmap;

    public VideoData(String path, String title, Bitmap bitmap) {
        this.path = path;
        this.title = title;
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
