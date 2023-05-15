package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.lab4.audio.AudioListActivity;


public class MainActivity extends AppCompatActivity{

    Button openAudioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openAudioButton = findViewById(R.id.open_audio_button);

        openAudioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, AudioListActivity.class);
                startActivity(intent);
            }
        });
    }


}