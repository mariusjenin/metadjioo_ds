package com.metadjioo_ds.app.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.metadjioo_ds.MDSApp;

public abstract class MDSActivityMainScreen extends MDSActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MDSApp.setCurrentAct(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSecondScreen();
    }

    protected void onSecondScreenReady(){
        //empty
    }

    public abstract void initSecondScreen();
}
