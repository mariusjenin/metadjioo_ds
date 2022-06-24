package com.metadjioo_ds.app.activity.used.main_screen;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

import androidx.fragment.app.FragmentManager;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivityMainScreen;
import com.metadjioo_ds.app.activity.used.second_screen.ExperiencePreviewActivity;
import com.metadjioo_ds.app.activity.used.second_screen.VideoDataSheetActivity;
import com.metadjioo_ds.app.fragment.ExperienceFragment;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.utils.Utils;

public class ExperienceActivity extends MDSActivityMainScreen {

    private ExperienceFragment experience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);
    }

    @Override
    public void onBackPressed() {
        //nothing
    }

    @Override
    public void initSecondScreen(){
        Utils.launchActivityOnSecondScreen(VideoDataSheetActivity.class);
    }

    @Override
    protected void onSecondScreenReady() {
        experience = new ExperienceFragment(this, AppDatabase.getInstance1(this),true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.root, experience,null).commit();
    }
}