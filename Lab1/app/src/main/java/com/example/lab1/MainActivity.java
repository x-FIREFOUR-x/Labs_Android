package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

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

                if(type != "" && firm != "")
                {
                    textView.setText(type + " " + firm);
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
}