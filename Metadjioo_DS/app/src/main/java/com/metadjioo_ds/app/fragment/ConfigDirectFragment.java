package com.metadjioo_ds.app.fragment;

import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.app.activity.used.main_screen.ConfigurationActivity;

public abstract class ConfigDirectFragment extends ConfigFragment implements ConfigObserver {

    protected ConfigurationActivity mConfigObserver;

    public void setConfigObserver(ConfigurationActivity mConfigObserver) {
        this.mConfigObserver = mConfigObserver;
    }
}
