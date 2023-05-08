package com.example.lab4;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab4.audio.ViewAudioPlayer;


public class MainActivity extends AppCompatActivity implements ViewController {

    private FragmentSelector fragmentSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentSelector = (FragmentSelector) getSupportFragmentManager()
                .findFragmentById(R.id.frame_layout_selector);
    }

    private void CreateAlertDialog(String typeMessage, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(message);
        builder.setTitle(typeMessage);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void openAudioPlayer() {
        try {
            Intent intent = new Intent(MainActivity.this, ViewAudioPlayer.class);
            startActivity(intent);
        } catch (Exception e) {
            CreateAlertDialog("!Error", "Failed to open audio player");
        }
    }

    @Override
    public void openVideoPlayer() {
        try {
            Intent intent = new Intent(MainActivity.this, ViewVideoPlayer.class);
            startActivity(intent);
        } catch (Exception e) {
            CreateAlertDialog("!Error", "Failed to open video player");
        }
    }
}