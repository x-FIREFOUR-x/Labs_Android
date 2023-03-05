package com.example.lab2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupTypeProduct;
    private RadioGroup radioGroupFirmProduct;

    private Button buttonOk;
    private Button buttonCancel;

    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupTypeProduct = findViewById(R.id.radioGroupTypeProduct);
        radioGroupFirmProduct = findViewById(R.id.radioGroupFirmProduct);
        buttonOk = findViewById(R.id.buttonOk);
        buttonCancel = findViewById(R.id.buttonCancel);
        textView = findViewById(R.id.textView);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = getTextCheckedRadioButton(radioGroupTypeProduct);
                String firm = getTextCheckedRadioButton(radioGroupFirmProduct);

                if (Objects.equals(type, ""))
                {
                    CreateAlertDialog("Select type of product");
                    return;
                }

                if (Objects.equals(firm, ""))
                {
                    CreateAlertDialog("Select firm of product");
                    return;
                }

                if(!Objects.equals(type, "") && !Objects.equals(firm, ""))
                {
                    String text = type + " " + firm;
                    textView.setText(text);
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
            }
        });
    }

    private String getTextCheckedRadioButton(RadioGroup radioGroup){
        String text = "";
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (radioButton.isChecked())
            {
                text = radioButton.getText().toString();
            }
        }
        return text;
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
}