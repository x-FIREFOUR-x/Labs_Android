package com.example.lab3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class FragmentOutput extends Fragment {

    private Button buttonCancel;
    private TextView textView;
    private Button buttonOpen;


    private OnFragmentSendDataListener fragmentSendDataListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must release interface OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_output, container, false);

        buttonCancel = view.findViewById(R.id.buttonCancel);
        textView = view.findViewById(R.id.textView);
        buttonOpen = view.findViewById(R.id.buttonOpen);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
            }
        });

        buttonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentSendDataListener.openFileView();
            }
        });

        return view;
    }

    public void setDataTextView(String data) {
        textView.setText(data);
    }
}