package com.example.lab4.video;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.lab4.R;

import java.util.concurrent.TimeUnit;

public class VideoPlayerActivity extends AppCompatActivity {

    private TextView titleTextView;
    private VideoView videoView;

    private RelativeLayout controllerRelativeLayout;
    private boolean isOpen = true;

    private ImageView pausePlayImageView;
    private ImageView replayImageView;
    private ImageView forwardImageView;

    private TextView currentTimeTextView;
    private TextView totalTimeTextView;
    private SeekBar seekBar;

    private String videoName;
    private String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoName = getIntent().getStringExtra("videoName");
        videoPath = getIntent().getStringExtra("videoPath");

        titleTextView = findViewById(R.id.title_text_view);
        videoView = findViewById(R.id.video_view);

        controllerRelativeLayout = findViewById(R.id.controller_video);

        pausePlayImageView = findViewById(R.id.pause_play_image_view);
        replayImageView = findViewById(R.id.time_replay_image_view);
        forwardImageView = findViewById(R.id.time_forward_image_view);

        currentTimeTextView = findViewById(R.id.current_time_text_view);
        totalTimeTextView = findViewById(R.id.total_time_text_view);
        seekBar = findViewById(R.id.seek_bar);

        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(videoView.getDuration());
                totalTimeTextView.setText(convertToHHMMSS(videoView.getDuration()) + "");
                videoView.start();
                pausePlayImageView.setImageResource(R.drawable.baseline_pause_circle_outline_24);
            }
        });

        titleTextView.setText(videoName);

        replayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(videoView.getCurrentPosition() - 10000);
            }
        });

        forwardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(videoView.getCurrentPosition() + 10000);
            }
        });

        pausePlayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videoView.isPlaying()){
                    videoView.pause();
                    pausePlayImageView.setImageResource(R.drawable.baseline_play_circle_outline_24);

                }
                else{
                    videoView.start();
                    pausePlayImageView.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                }
            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen){
                    hideControls();
                    isOpen = false;
                }
                else{
                    showControls();
                    isOpen = true;
                }
            }
        });

        VideoPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(videoView.getDuration() > 0){
                    int currentPosition = videoView.getCurrentPosition();
                    seekBar.setProgress(currentPosition);
                    currentTimeTextView.setText(convertToHHMMSS(currentPosition) + "");
                }
                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    videoView.seekTo(i);
                    currentTimeTextView.setText(convertToHHMMSS(videoView.getCurrentPosition()) + "");

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void hideControls(){
        controllerRelativeLayout.setVisibility((View.GONE));

        final Window window = this.getWindow();
        if(window == null){
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN );
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void showControls(){
        controllerRelativeLayout.setVisibility((View.VISIBLE));

        final Window window = this.getWindow();
        if(window == null){
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }

    @SuppressLint("DefaultLocale")
    public static String convertToHHMMSS(int duration){
        long millis = duration;
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis) % TimeUnit.DAYS.toHours(1),
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
        );
    }
}