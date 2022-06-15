package com.metadjioo_ds.view.activity.used;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import com.metadjioo_ds.view.presentation.EmptyPresentation;
import com.metadjioo_ds.R;
import com.metadjioo_ds.view.activity.MDSActivity;

public class HomeActivity extends MDSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initSecondMonitor();

        Button btn_setup = findViewById(R.id.setup_experience);
        Button btn_launch = findViewById(R.id.launch_experience);
        btn_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                HomeActivity.this.startActivity(intent);
            }
        });

        btn_launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ExperienceActivity.class);
                HomeActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    public void initSecondMonitor(){
        DisplayManager dm = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        if (dm != null)
        {
            Display[] displays = dm.getDisplays();

            if(displays.length>0){
                Display display = displays[1];
                mPresentation = new EmptyPresentation(this, display);
                mPresentation.show();
            }
        }
    }
}