package com.example.lab4.audio;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab4.R;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AudioPlayerActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView currentTimeTextView;
    private TextView totalTimeTextView;
    private SeekBar seekBar;
    private ImageView audioIconImageView;
    private ImageView pausePlayImageView;

    private ImageView previousImageView;
    private ImageView nextImageView;


    ArrayList<AudioData> audioList;
    AudioData currentAudio;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_player);

        titleTextView = findViewById(R.id.title_text_view);
        currentTimeTextView = findViewById(R.id.current_time_text_view);
        totalTimeTextView = findViewById(R.id.total_time_text_view);
        seekBar = findViewById(R.id.seek_bar);
        audioIconImageView = findViewById(R.id.audio_icon_image_view);
        pausePlayImageView = findViewById(R.id.pause_play_image_view);
        previousImageView = findViewById(R.id.previous_image_view);
        nextImageView = findViewById(R.id.next_image_view);

        titleTextView.setSelected(true);

        audioList = (ArrayList<AudioData>) getIntent().getSerializableExtra("LIST");

        setResourcesWithAudio();
    }

    private void setResourcesWithAudio(){
        currentAudio = audioList.get(AudioPlayer.getCurrentIndex());

        titleTextView.setText(currentAudio.getTitle());
        totalTimeTextView.setText(convertToMMSS(currentAudio.getDuration()));

        pausePlayImageView.setOnClickListener(v->pausePlay());
        previousImageView.setOnClickListener(v->playPreviousAudio());
        nextImageView.setOnClickListener(v->playNextAudio());

        playAudio();
    }


    private void playAudio(){

    }

    private void playNextAudio(){

    }

    private void playPreviousAudio(){

    }

    private void pausePlay(){

    }

    @SuppressLint("DefaultLocale")
    public static String convertToMMSS(String duration){
        long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
        );
    }
}
