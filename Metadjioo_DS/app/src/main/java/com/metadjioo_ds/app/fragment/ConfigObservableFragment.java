package com.metadjioo_ds.app.fragment;

import androidx.fragment.app.Fragment;

import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.db.AppDatabase;

public abstract class ConfigObservableFragment extends Fragment{
    protected final AppDatabase db;
    protected final AppDatabase copyDB;
    protected ConfigObserver mConfigObserver;

    public ConfigObservableFragment(){
        db = AppDatabase.getInstance1(getContext());
        copyDB = AppDatabase.getInstance2(getContext());
    }

    public void setConfigObserver(ConfigObserver configObserver){
        mConfigObserver = configObserver;
    }

    protected abstract void init();

    public abstract void updateDatabase();

    public void defaultLanguageModified(){
        //nothing : base behavior
    }

    public void teaserModified(){
        //nothing : base behavior
    }

    public void productsModified(){
        //nothing : base behavior
    }

    public void orderProductsModified(){
        //nothing : base behavior
    }

    public void additionnalVideoModified(){
        //nothing : base behavior
    }

    public void languagesModified(){
        //nothing : base behavior
    }

    public void databaseReload(){
        //nothing : base behavior
    }

}
