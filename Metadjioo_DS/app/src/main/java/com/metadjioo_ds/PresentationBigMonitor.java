package com.metadjioo_ds;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;

public class PresentationBigMonitor extends Presentation {
    public PresentationBigMonitor(Context outerContext,
                          Display display)
    {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_monitor);
//        populate(findViewById(R.id.main), getDisplay());
    }
}
