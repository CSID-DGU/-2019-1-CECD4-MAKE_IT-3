package com.example.socket_sj;


import android.os.Bundle;

import android.os.Environment;

import java.io.File;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client c = new client();
        c.run();
        /*Log.d("STATE", Environment.getExternalStorageState());
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "work_data");
        Log.d("PATH", f.getAbsolutePath());
        if (!f.exists()) {
            Log.d("MAKE DIR", f.mkdirs() + "");
        }*/
    }
}
