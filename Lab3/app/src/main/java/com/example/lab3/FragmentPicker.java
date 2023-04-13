package com.example.lab3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;


public class FragmentPicker extends Fragment {


    private RadioGroup radioGroupTypeProduct;
    private RadioGroup radioGroupFirmProduct;
    private Button buttonOk;

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
        View view = inflater.inflate(R.layout.fragment_picker, container, false);

        radioGroupTypeProduct = view.findViewById(R.id.radioGroupTypeProduct);
        radioGroupFirmProduct = view.findViewById(R.id.radioGroupFirmProduct);
        buttonOk = view.findViewById(R.id.buttonOk);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = getTextCheckedRadioButton(radioGroupTypeProduct);
                String firm = getTextCheckedRadioButton(radioGroupFirmProduct);
                fragmentSendDataListener.onSendData(type, firm);
            }
        });

        return view;
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