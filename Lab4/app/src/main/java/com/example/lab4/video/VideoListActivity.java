package com.example.lab4.video;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lab4.R;

import java.io.File;
import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity {

    private TextView noVideoTextView;
    private RecyclerView recyclerView;

    private ArrayList<VideoData> videoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        noVideoTextView = findViewById(R.id.no_video_text_view);
        recyclerView = findViewById(R.id.video_recycler_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermission();

        videoList = new ArrayList<>();

        String[] projection = {
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE
        };
        Cursor cursor = getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null
        );

        while (cursor.moveToNext())
        {
            String path = cursor.getString(0);
            String title = cursor.getString(1);

            if(new File(path).exists()){
                Bitmap videoScreen = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
                videoList.add(new VideoData(path, title, videoScreen));
            }
        }

        if(videoList.isEmpty()){
            noVideoTextView.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new VideoListAdapter(videoList, getApplicationContext()));
    }


    private boolean isPermission(){
        return ContextCompat.checkSelfPermission(VideoListActivity.this,
                Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (isPermission()) {
            Toast.makeText(
                    VideoListActivity.this,
                    "Audio storage permission allowed.",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            ActivityResultLauncher<String> requestPermissionAudio = registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    isGranted -> {if (!isGranted) {
                        sendToSettingDialog();
                    }}
            );
            requestPermissionAudio.launch(Manifest.permission.READ_MEDIA_VIDEO);
        }
    }

    private void sendToSettingDialog() {
        new AlertDialog.Builder(VideoListActivity.this)
                .setTitle("Alert for permission")
                .setMessage("Go to setting and enable video permissions to use this app")
                .setPositiveButton("Settings", (dialogInterface, i) -> {
                    Intent settingsIntent = new Intent();
                    settingsIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    settingsIntent.setData(uri);
                    startActivity(settingsIntent);
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Exit", (dialogInterface, i) -> finish())
                .setCancelable(false)
                .show();
    }
}