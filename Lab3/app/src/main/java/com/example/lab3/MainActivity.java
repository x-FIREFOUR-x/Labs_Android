package com.example.lab3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements OnFragmentSendDataListener {

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
    public void onSendData(String dataType, String dateFirm) {
        if (Objects.equals(dataType, ""))
        {
            CreateAlertDialog( "!Warning", "Select type of product");
            return;
        }

        if (Objects.equals(dateFirm, ""))
        {
            CreateAlertDialog("!Warning", "Select firm of product");
            return;
        }

        if(!Objects.equals(dataType, "") && !Objects.equals(dateFirm, ""))
        {
            String text = dataType + " " + dateFirm;
            fragmentOutput.setDataTextView(text);
            saveDataToFile(text);
        }
    }

    @Override
    public void openFileView() {
        try {
            String data = loadDataWithFile();
            Intent intent = new Intent(MainActivity.this, ViewFileActivity.class);
            intent.putExtra("Data", data);
            startActivity(intent);
        } catch (Exception e) {
            CreateAlertDialog("!Error", "Failed to load data with file");
        }
    }


    private void saveDataToFile(String data)  {
        FileOutputStream outputStream;
        String fileName = getResources().getString(R.string.filename);

        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
            CreateAlertDialog("Ok", "Data successfully saved to file");
        }catch (Exception e){
            e.printStackTrace();
            CreateAlertDialog("!Error", "Failed to save data to file");
        }
    }

    private String loadDataWithFile() throws Exception {
        String fileName = getResources().getString(R.string.filename);
        File file = new File(getFilesDir(), fileName);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        return stringBuilder.toString();
    }
}