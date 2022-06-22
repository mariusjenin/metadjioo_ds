package com.metadjioo_ds.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.metadjioo_ds.R;

public class ChoiceProductsDisplayedFragment extends ConfigFragment {

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choice_wine_displayed, container, false);
        return view;
    }

    @Override
    public void updateDatabase() {
        //TODO
    }

    @Override
    public void refreshDisplayOnDefaultLanguageModified() {
        //TODO
    }

    @Override
    public void refreshDisplayOnTeaserModified() {
        //nothing : can't trigger this
    }

    @Override
    public void refreshDisplayOnProductsModified() {
        //nothing : can't trigger this
    }

    @Override
    public void refreshDisplayOnOrderProductsModified() {
        //nothing : can't trigger this
    }

    @Override
    public void refreshDisplayOnLanguagesModified() {
        //nothing : can't trigger this
    }

    @Override
    public void refreshDisplayOnReload() {
        //TODO
    }
}