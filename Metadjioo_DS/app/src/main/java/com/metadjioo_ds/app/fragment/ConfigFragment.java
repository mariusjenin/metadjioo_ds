package com.metadjioo_ds.app.fragment;

import androidx.fragment.app.Fragment;

import com.metadjioo_ds.app.activity.used.ConfigObserver;
import com.metadjioo_ds.db.AppDatabase;

public abstract class ConfigFragment extends Fragment{
    protected final AppDatabase db;
    protected final AppDatabase copyDB;
    protected ConfigObserver mConfigObserver;

    public ConfigFragment(){
        db = AppDatabase.getInstance1(getContext());
        copyDB = AppDatabase.getInstance2(getContext());
    }

    public void setConfigObserver(ConfigObserver configObserver){
        mConfigObserver = configObserver;
    }

    public abstract void updateDatabase();

    public abstract void refreshDisplayOnDefaultLanguageModified();
    public abstract void refreshDisplayOnTeaserModified();
    public abstract void refreshDisplayOnProductsModified();
    public abstract void refreshDisplayOnOrderProductsModified();
    public abstract void refreshDisplayOnLanguagesModified();
    public abstract void refreshDisplayOnReload();
}
