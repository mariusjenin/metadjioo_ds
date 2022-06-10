package com.metadjioo_ds.view.presentation;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.metadjioo_ds.R;
import com.metadjioo_ds.view.FullScreenBehavior;

public abstract class MDSPresentation extends Presentation {
    private FullScreenBehavior mFullScreenBehavior;
    public MDSPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final View decorView = getWindow().getDecorView();
        mFullScreenBehavior = new FullScreenBehavior(decorView);
        mFullScreenBehavior.doFullScreen();

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
        {
            @Override
            public void onSystemUiVisibilityChange(int visibility)
            {
                if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                {
                    mFullScreenBehavior.doFullScreen();
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        mFullScreenBehavior.doFullScreen();
    }


}
