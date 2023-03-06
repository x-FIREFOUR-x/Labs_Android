package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewFileActivity extends AppCompatActivity {

    private TextView textView;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_file);

        textView = findViewById(R.id.textViewFile);
        buttonBack = findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        String passData = intent.getStringExtra("Data");
        textView.setText(passData);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewFileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}