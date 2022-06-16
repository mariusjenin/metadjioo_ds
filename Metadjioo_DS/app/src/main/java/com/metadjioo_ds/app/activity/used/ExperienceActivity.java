package com.metadjioo_ds.app.activity.used;

import android.app.Activity;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivity;
import com.metadjioo_ds.app.fragment.CardWineFragment;
import com.metadjioo_ds.app.fragment.ExperienceFragment;
import com.metadjioo_ds.app.presentation.EmptyPresentation;
import com.metadjioo_ds.utils.CallbackListener;

import java.util.Objects;

public class ExperienceActivity extends MDSActivity {

    private ExperienceFragment experience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience_activity);
        initSecondMonitor();

        experience = new ExperienceFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.root_experience_activity, experience,null).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        experience.updateWineCards();
    }

    public void initSecondMonitor(){
        DisplayManager dm = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        if (dm != null)
        {
            Display[] displays = dm.getDisplays();
            if(displays.length>0){
                Display display = displays[1];
                mPresentation = new EmptyPresentation(this, display);
                mPresentation.show();
            }
        }
    }
}