package com.example.lab4.video;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.lab4.R;


public class VideoSearchActivity extends AppCompatActivity {

    private EditText linkEditText;

    private Button searchVideoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);

        linkEditText = findViewById(R.id.link_video_edit_text);
        searchVideoButton = findViewById(R.id.search_video_button);

        searchVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String path = linkEditText.getText().toString();
                if(!path.equals("")){
                    Intent intent = new Intent(VideoSearchActivity.this, VideoPlayerActivity.class);
                    intent.putExtra("videoName", "");
                    intent.putExtra("videoPath", path);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}