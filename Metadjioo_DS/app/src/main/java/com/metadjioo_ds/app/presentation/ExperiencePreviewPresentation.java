package com.metadjioo_ds.app.presentation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;

import androidx.fragment.app.FragmentManager;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivity;
import com.metadjioo_ds.app.fragment.ExperienceFragment;

public class ExperiencePreviewPresentation extends MDSPresentation {

    public ExperiencePreviewPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

//        MDSActivity act = MDSApp.getCurrentAct();
//        ExperienceFragment experience = new ExperienceFragment(act);
//        FragmentManager fragmentManager = this.();
//        fragmentManager.beginTransaction().add(R.id.root, experience,null).commit();
    }


}
