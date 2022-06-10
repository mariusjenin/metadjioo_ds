package com.metadjioo_ds.view.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.view.FullScreenBehavior;
import com.metadjioo_ds.view.presentation.MDSPresentation;

/**
 * Activity of Metadjioo Display Stand App
 */
public abstract class MDSActivity extends AppCompatActivity {
    private ProgressDialog mProgDialog;
    private FullScreenBehavior mFullScreenBehavior;
    protected MDSPresentation mPresentation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MDSApp.setCurrentAct(this);


        final View decorView = getWindow().getDecorView();
        mFullScreenBehavior = new FullScreenBehavior(decorView);
//        Log.e("Activity","1");

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                {

                    @Override
                    public void onSystemUiVisibilityChange(int visibility)
                    {
                        if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                        {
                            mFullScreenBehavior.doFullScreen();
//                            Log.e("Activity","2");
                        }
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        mFullScreenBehavior.doFullScreen();
//        Log.e("Activity","3");
    }

    public void showProgress() {
        mProgDialog = ProgressDialog.show(this, null, null, false, false);
        mProgDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgDialog.setContentView(R.layout.progress_bar);
    }

    public void hideProgress() {
        mProgDialog.dismiss();
    }
}
