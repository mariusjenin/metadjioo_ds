package com.metadjioo_ds.view;

import android.app.Dialog;
import android.view.View;
import android.view.WindowInsetsController;

import com.google.android.material.appbar.AppBarLayout;

public class FullScreenBehavior {
    private final View mView;

    private final static int FLAGS_FULLSCREEN = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;


    public FullScreenBehavior(View view){
        mView = view;
    }

    public void doFullScreen(){
        mView.setSystemUiVisibility(FLAGS_FULLSCREEN);
    }
}
