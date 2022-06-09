package com.metadjioo_ds;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    private PresentationBigMonitor mPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_monitor);
        initSecondMonitor();
    }

    public void initSecondMonitor(){
        DisplayManager dm =
                (DisplayManager) getSystemService(DISPLAY_SERVICE);
        if (dm != null)
        {
            Display[] displays =
                    dm.getDisplays(
                            );


            Log.e(""+displays.length,"");
            Display display = displays[1];
//            for (Display display : displays)
//            {
                mPresentation = new PresentationBigMonitor(this, display);
                mPresentation.show();
//            }
        }
    }
}