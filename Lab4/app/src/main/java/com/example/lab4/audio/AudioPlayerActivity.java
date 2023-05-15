package com.example.lab4.audio;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab4.R;

import java.io.IOException;
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


    private ArrayList<AudioData> audioList;
    private AudioData currentAudio;

    private MediaPlayer audioPlayer = AudioPlayer.getInstance();


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

        AudioPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (audioPlayer != null && audioPlayer.isPlaying()) {
                    seekBar.setProgress(audioPlayer.getCurrentPosition());
                    currentTimeTextView.setText(
                            convertToMMSS(audioPlayer.getCurrentPosition() + ""));
                }

                if (audioPlayer.getCurrentPosition() >= audioPlayer.getDuration() - 100)
                    playNextAudio();

                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (audioPlayer != null && b) {
                    audioPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setResourcesWithAudio();
    }

    private void setResourcesWithAudio(){
        currentAudio = audioList.get(AudioPlayer.getCurrentIndex());

        titleTextView.setText(currentAudio.getTitle());
        totalTimeTextView.setText(convertToMMSS(currentAudio.getDuration()));

        pausePlayImageView.setOnClickListener(v->pausePlay());
        previousImageView.setOnClickListener(v->playPreviousAudio());
        nextImageView.setOnClickListener(v->playNextAudio());

        if (AudioPlayer.getContinueAudio()) {
            continueAudio();
        }
        else {
            playAudio();
        }
    }


    private void playAudio(){
        audioPlayer.reset();

        try {
            audioPlayer.setDataSource(currentAudio.getPath());
            audioPlayer.prepare();
        }catch (IOException e){
            e.printStackTrace();
        }
            audioPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(audioPlayer.getDuration());

            pausePlayImageView.setImageResource(R.drawable.baseline_pause_circle_outline_24);
    }

    private void continueAudio(){
        seekBar.setProgress(audioPlayer.getCurrentPosition());
        seekBar.setMax(audioPlayer.getDuration());

        pausePlayImageView.setImageResource(R.drawable.baseline_pause_circle_outline_24);
    }

    private void playNextAudio(){
        if(AudioPlayer.getCurrentIndex() == audioList.size() - 1)
            return;

        AudioPlayer.incrementCurrentIndex();
        setResourcesWithAudio();
    }

    private void playPreviousAudio(){
        if(AudioPlayer.getCurrentIndex() == 0)
            return;

        AudioPlayer.decrementCurrentIndex();
        setResourcesWithAudio();
    }

    private void pausePlay(){
        if(audioPlayer.isPlaying()) {
            audioPlayer.pause();
            pausePlayImageView.setImageResource(R.drawable.baseline_play_circle_outline_24);
        }
        else {
            audioPlayer.start();
            pausePlayImageView.setImageResource(R.drawable.baseline_pause_circle_outline_24);
        }
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
