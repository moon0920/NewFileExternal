package com.example.edu.newfileexternal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextinput;
    FileInputStream fileInputStream;
    FileOutputStream fileOutputStream;
    final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnread = findViewById(R.id.btnread);
        btnread.setOnClickListener(this);
        Button btnwrite = findViewById(R.id.btnwrite);
        btnwrite.setOnClickListener(this);

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("", "Permission has been granted by user");
                }
        }
    }

    @Override
    public void onClick(View v) {
        File file = null;

        switch (v.getId()){

            case R.id.btnread:


                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"moon.txt");
                try {
                    fileInputStream = new FileInputStream(file);
                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);
                    editTextinput.setText(new String(buffer));
                    fileInputStream.close();  }
                catch (FileNotFoundException e) {
                    e.printStackTrace(); }
                catch (IOException e) {
                    e.printStackTrace(); }

                break;
            case R.id.btnwrite:

                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                if(!dir.exists()) dir.mkdirs();

                file = new File(dir,"moon.txt");
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fileOutputStream.write(editTextinput.getText().toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                editTextinput.setText("");
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}
