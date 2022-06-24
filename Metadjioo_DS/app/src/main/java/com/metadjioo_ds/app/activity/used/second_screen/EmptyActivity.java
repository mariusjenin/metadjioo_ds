package com.metadjioo_ds.app.activity.used.second_screen;

import android.os.Bundle;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivitySecondScreen;

public class EmptyActivity extends MDSActivitySecondScreen {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_presentation);
    }
}
