package com.metadjioo_ds.app.presentation;

import android.content.Context;
import android.os.Bundle;
import android.view.Display;

import com.metadjioo_ds.R;

public class EmptyPresentation extends MDSPresentation {

    public EmptyPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_presentation);
    }
}
