package com.metadjioo_ds.app.activity.used.second_screen;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivitySecondScreen;
import com.metadjioo_ds.app.fragment.ExperienceFragment;
import com.metadjioo_ds.db.AppDatabase;

public class ExperiencePreviewActivity extends MDSActivitySecondScreen {

    private ExperienceFragment mExperience;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

        mExperience = new ExperienceFragment(this, AppDatabase.getInstance2(this),false);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.root, mExperience ,null).commit();
    }

    public ExperienceFragment getExperience() {
        return mExperience;
    }
}
