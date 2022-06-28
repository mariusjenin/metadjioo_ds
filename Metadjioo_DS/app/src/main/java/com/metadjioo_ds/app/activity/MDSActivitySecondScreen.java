package com.metadjioo_ds.app.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.metadjioo_ds.MDSApp;

public abstract class MDSActivitySecondScreen extends MDSActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MDSApp.setCurrentSecondScreenAct(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        MDSApp.getCurrentAct().onSecondScreenReady();
    }
}
