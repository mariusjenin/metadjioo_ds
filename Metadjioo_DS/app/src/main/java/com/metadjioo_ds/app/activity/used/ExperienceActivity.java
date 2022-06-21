package com.metadjioo_ds.app.activity.used;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;

import androidx.fragment.app.FragmentManager;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivity;
import com.metadjioo_ds.app.fragment.ExperienceFragment;
import com.metadjioo_ds.app.presentation.VideoDataSheetPresentation;

public class ExperienceActivity extends MDSActivity {

    private ExperienceFragment experience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experience_activity);
        initSecondMonitor();

        experience = new ExperienceFragment(this);
        experience.setPresentation((VideoDataSheetPresentation) mPresentation);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.root_experience_activity, experience,null).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        experience.updateWineCards();
        experience.updateAdditionnalVideo();
    }

    @Override
    public void onBackPressed() {
        //nothing
    }

    public void initSecondMonitor(){
        DisplayManager dm = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        if (dm != null)
        {
            Display[] displays = dm.getDisplays();
            if(displays.length>0){
                Display display = displays[1];
                mPresentation = new VideoDataSheetPresentation(this, display);
                mPresentation.show();
            }
        }
    }
}