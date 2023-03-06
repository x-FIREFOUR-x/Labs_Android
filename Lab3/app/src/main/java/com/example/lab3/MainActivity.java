package com.example.lab3;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements FragmentPicker.OnFragmentSendDataListener {

    private FragmentPicker fragmentPicker;
    private FragmentOutput fragmentOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentPicker = (FragmentPicker) getSupportFragmentManager()
                .findFragmentById(R.id.frameLayoutPicker);

        fragmentOutput = (FragmentOutput) getSupportFragmentManager()
                .findFragmentById(R.id.frameLayoutOutput);
    }

    private void CreateAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(message);
        builder.setTitle("Warning!");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onSendData(String dataType, String dateFirm) {
        if (Objects.equals(dataType, ""))
        {
            CreateAlertDialog("Select type of product");
            return;
        }

        if (Objects.equals(dateFirm, ""))
        {
            CreateAlertDialog("Select firm of product");
            return;
        }

        if(!Objects.equals(dataType, "") && !Objects.equals(dateFirm, ""))
        {
            String text = dataType + " " + dateFirm;
            fragmentOutput.setDataTextView(text);
        }
    }
}