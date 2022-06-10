package com.metadjioo_ds.view.activity.used;

import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;

import com.metadjioo_ds.view.presentation.HomePresentation;
import com.metadjioo_ds.R;
import com.metadjioo_ds.view.activity.MDSActivity;

public class HomeActivity extends MDSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initSecondMonitor();
    }

    public void initSecondMonitor(){
        DisplayManager dm = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        if (dm != null)
        {
            Display[] displays = dm.getDisplays();

            Display display = displays[1];
            mPresentation = new HomePresentation(this, display);
            mPresentation.show();
        }
    }
}