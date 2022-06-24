package com.metadjioo_ds.app.activity.used.main_screen;

import android.app.ActivityOptions;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivityMainScreen;
import com.metadjioo_ds.app.activity.used.second_screen.EmptyActivity;
import com.metadjioo_ds.utils.Utils;

public class LogInActivity extends MDSActivityMainScreen {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logging_in);
        Button btnConnect = findViewById(R.id.log_in);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO verify the user
                Intent intent = new Intent(LogInActivity.this, ConfigurationActivity.class);
                LogInActivity.this.startActivity(intent);
            }
        });

        ImageButton btnGoBack = findViewById(R.id.go_back);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogInActivity.this.finish();
            }
        });
    }

    public void initSecondScreen(){
        Utils.launchActivityOnSecondScreen(EmptyActivity.class);
    }
}