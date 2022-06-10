package com.metadjioo_ds.view.presentation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.metadjioo_ds.R;
import com.metadjioo_ds.view.FullScreenBehavior;
import com.metadjioo_ds.view.activity.MDSActivity;

public class HomePresentation extends MDSPresentation {

    public HomePresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_monitor);
    }
}
