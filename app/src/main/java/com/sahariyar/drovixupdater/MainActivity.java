/*
 * Copyright (c) 2025-Present by Sahariyar Ahamad
 *
 * This file is part of DrovixUpdater Library and is provided for use without modification.
 * Redistribution or modification is strictly prohibited.
 * Contact: https://github.com/sahariyarahamad/
 */


package com.sahariyar.drovixupdater;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sahariyar.drovixupdater.utiles.OnUpdateDownloadListener;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tv = findViewById(R.id.tv);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DrovixUpdater().setUp(MainActivity.this)
                        .setUrl("https://github.com/sahariyarahamad/mediacaptionapp/releases/download/v1.1.0/Media_Caption.apk")
                        .setFileNameFromUrl()
                        .setOnUpdateDownloadListener(new OnUpdateDownloadListener() {
                            @Override
                            public void OnConnectingBuffer(String connectingMsg) {
                                tv.setText(connectingMsg);
                            }

                            @Override
                            public void OnDownloadProgress(int progress) {
                                tv.setText(""+progress+" %");
                            }

                            @Override
                            public void OnDownloadComplete(File path) {
                                tv.setText("download complete");
                            }

                            @Override
                            public void OnDownloadError(String errorMsg) {
                                tv.setText(errorMsg);
                            }


                        })
                        .start();

            }
        });

    }


}