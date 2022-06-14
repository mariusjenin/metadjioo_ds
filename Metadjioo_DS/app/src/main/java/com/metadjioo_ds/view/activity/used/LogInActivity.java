package com.metadjioo_ds.view.activity.used;

import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;

import com.metadjioo_ds.R;
import com.metadjioo_ds.view.activity.MDSActivity;
import com.metadjioo_ds.view.presentation.EmptyPresentation;

public class LogInActivity extends MDSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logging_in);
        initSecondMonitor();
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