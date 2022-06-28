package com.metadjioo_ds.app.fragment;

import android.util.Log;

import com.metadjioo_ds.app.activity.used.main_screen.ConfigurationActivity;

public abstract class ConfigIndirectFragment extends ConfigFragment{

    protected ConfigDirectFragment mConfigObserver;

    public void setConfigObserver(ConfigDirectFragment mConfigObserver) {
        this.mConfigObserver = mConfigObserver;
    }
}
