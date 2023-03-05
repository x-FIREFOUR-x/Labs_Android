package com.example.lab2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentOutput extends Fragment {

    private Button buttonCancel;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_output, container, false);

        buttonCancel = view.findViewById(R.id.buttonCancel);
        textView = view.findViewById(R.id.textView);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
            }
        });

        return view;
    }

    public void setDataTextView(String data) {
        textView.setText(data);
    }
}