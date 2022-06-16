package com.metadjioo_ds.app.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.app.FullScreenBehavior;
import com.metadjioo_ds.app.presentation.MDSPresentation;

/**
 * Activity of Metadjioo Display Stand App
 */
public abstract class MDSActivity extends AppCompatActivity {
    private ProgressDialog mProgDialog;
    private FullScreenBehavior mFullScreenBehavior;
    protected MDSPresentation mPresentation;
    protected AppDatabase appDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MDSApp.setCurrentAct(this);

        appDatabase = AppDatabase.getInstance(this);

        final View decorView = getWindow().getDecorView();
        mFullScreenBehavior = new FullScreenBehavior(decorView);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();


        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                {

                    @Override
                    public void onSystemUiVisibilityChange(int visibility)
                    {
                        if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                        {
                            mFullScreenBehavior.doFullScreen();
                        }
                    }
                });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        mFullScreenBehavior.doFullScreen();
    }

    public void showProgress() {
        mProgDialog = ProgressDialog.show(this, null, null, false, false);
        mProgDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgDialog.setContentView(R.layout.progress_bar);
    }

    public boolean isPresentationAvailable(){
        return mPresentation != null;
    }

    public void hideProgress() {
        mProgDialog.dismiss();
    }
}
