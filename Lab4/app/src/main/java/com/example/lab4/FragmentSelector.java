package com.example.lab4;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class FragmentSelector extends Fragment {

    private Button buttonOpenAudioPlayer;

    private Button buttonOpenVideoPlayer;

    private ViewController viewController;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            viewController = (ViewController) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must release interface ViewController");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selector, container, false);

        buttonOpenAudioPlayer = view.findViewById(R.id.buttonAudio);
        buttonOpenVideoPlayer = view.findViewById(R.id.buttonVideo);

        buttonOpenAudioPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewController.openAudioPlayer();
            }
        });

        buttonOpenVideoPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewController.openVideoPlayer();
            }
        });

        return view;
    }
}