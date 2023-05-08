package com.example.lab4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4.audio.AudioData;
import com.example.lab4.audio.AudioListAdapter;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    private TextView noSongsTextView;

    RecyclerView recyclerView;
    ArrayList<AudioData> audioList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noSongsTextView = findViewById(R.id.no_audio_text_view);

        recyclerView = findViewById(R.id.audio_recycler_view);

        if(!checkPermission()){
            requestPermission();
            //return;
        }

        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, selection, null, null
        );

        while (cursor.moveToNext()){
            AudioData audioData = new AudioData(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );

            if(new File(audioData.getPath()).exists())
                audioList.add(audioData);
        }

        if(audioList.isEmpty()){
            noSongsTextView.setVisibility(View.VISIBLE);
        }
        else{
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new AudioListAdapter(audioList, getApplicationContext()));
        }

    }

    private boolean checkPermission() {
        int permission = ContextCompat.checkSelfPermission(
                MainActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
        );

        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        boolean isPermission = ActivityCompat.shouldShowRequestPermissionRationale(
                MainActivity.this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
        );

        if (isPermission) {
            Toast.makeText(
                    MainActivity.this,
                    "READ EXTERNAL STORAGE PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTING",
                    Toast.LENGTH_LONG
            ).show();
        }
        else {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    123
            );
        }
    }
}