package com.example.lab2;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class FragmentPicker() extends Fragment {

    private RadioGroup radioGroupTypeProduct;
    private RadioGroup radioGroupFirmProduct;

    private Button buttonOk;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioGroupTypeProduct = view.findViewById(R.id.radioGroupTypeProduct);
        radioGroupFirmProduct = view.findViewById(R.id.radioGroupFirmProduct);
        buttonOk = view.findViewById(R.id.buttonOk);

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picker, container, false);
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