package com.metadjioo_ds.app.fragment;

public abstract class ConfigIndirectFragment extends ConfigFragment{

    protected ConfigDirectFragment mConfigObserver;

    public void setConfigObserver(ConfigDirectFragment mConfigObserver) {
        this.mConfigObserver = mConfigObserver;
    }
}
