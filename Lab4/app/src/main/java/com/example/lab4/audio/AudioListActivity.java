package com.example.lab4.audio;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
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

public class AudioListActivity extends AppCompatActivity {

    private TextView noSongsTextView;
    private RecyclerView recyclerView;

    private ArrayList<AudioData> audioList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_list);

        noSongsTextView = findViewById(R.id.no_audio_text_view);

        recyclerView = findViewById(R.id.audio_recycler_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(AudioPlayer.getInstance().isPlaying())
            AudioPlayer.getInstance().pause();
        AudioPlayer.setCurrentIndex(-1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermission();

        audioList = new ArrayList<>();

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


    private boolean isPermission(){
        return ContextCompat.checkSelfPermission(AudioListActivity.this,
                Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (isPermission()) {
            Toast.makeText(
                    AudioListActivity.this,
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
            requestPermissionAudio.launch(Manifest.permission.READ_MEDIA_AUDIO);
        }
    }

    private void sendToSettingDialog() {
        new AlertDialog.Builder(AudioListActivity.this)
                .setTitle("Alert for permission")
                .setMessage("Go to setting and enable audio permissions to use this app")
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

    @Override
    protected void onResume(){
        super.onResume();
        if(recyclerView != null){
            recyclerView.setAdapter(new AudioListAdapter(audioList, getApplicationContext()));
        }
    }
}