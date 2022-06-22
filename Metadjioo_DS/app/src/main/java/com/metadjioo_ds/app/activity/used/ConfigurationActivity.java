package com.metadjioo_ds.app.activity.used;

import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentContainerView;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivity;
import com.metadjioo_ds.app.fragment.ChoiceDefaultLanguageFragment;
import com.metadjioo_ds.app.fragment.ChoiceProductsDisplayedFragment;
import com.metadjioo_ds.app.fragment.ChoiceTeaserVideoFragment;
import com.metadjioo_ds.app.fragment.ConfigFragment;
import com.metadjioo_ds.app.presentation.EmptyPresentation;
import com.metadjioo_ds.app.presentation.ExperiencePreviewPresentation;
import com.metadjioo_ds.db.AppDatabase;

public class ConfigurationActivity extends MDSActivity implements ConfigObserver {

    private FragmentContainerView fcvChoiceLanguage;
    private FragmentContainerView fcvChoiceTeaserVideo;
    private FragmentContainerView fcvChoiceProductsDisplayed;
    private TextView databaseEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration);
        initSecondMonitor();
        ImageButton refreshDatabase = findViewById(R.id.refresh_database);
        fcvChoiceLanguage = findViewById(R.id.choice_language);
        fcvChoiceTeaserVideo = findViewById(R.id.choice_teaser_video);
        fcvChoiceProductsDisplayed = findViewById(R.id.choice_products_displayed);
        databaseEmpty = findViewById(R.id.database_empty);
        ChoiceDefaultLanguageFragment choiceLanguage = (ChoiceDefaultLanguageFragment) getSupportFragmentManager().findFragmentById(R.id.choice_language);
        ChoiceTeaserVideoFragment choiceTeaserVideo = (ChoiceTeaserVideoFragment) getSupportFragmentManager().findFragmentById(R.id.choice_teaser_video);
        ChoiceProductsDisplayedFragment choiceProductsDisplayed = (ChoiceProductsDisplayedFragment) getSupportFragmentManager().findFragmentById(R.id.choice_products_displayed);
        assert choiceLanguage != null;
        assert choiceTeaserVideo != null;
        assert choiceProductsDisplayed != null;
        choiceLanguage.setConfigObserver(this);
        choiceTeaserVideo.setConfigObserver(this);
        choiceProductsDisplayed.setConfigObserver(this);
        refreshDisplay();
        refreshDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                appDatabaseCopy.clear();
                appDatabase.clear();
                appDatabase.fill();
                AppDatabase.copyDB1ToDB2();
                refreshDisplay();
                choiceLanguage.refreshDisplayOnReload();
                choiceTeaserVideo.refreshDisplayOnReload();
                choiceProductsDisplayed.refreshDisplayOnReload();
                Toast.makeText(ConfigurationActivity.this, "Database loaded", Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });

        ImageButton btnGoBack = findViewById(R.id.go_back);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigurationActivity.this.finish();
            }
        });
    }

    public void initSecondMonitor(){
        DisplayManager dm = (DisplayManager) getSystemService(DISPLAY_SERVICE);
        if (dm != null)
        {
            Display[] displays = dm.getDisplays();
            if(displays.length>0){
                Display display = displays[1];
                mPresentation = new ExperiencePreviewPresentation(this, display);
                mPresentation.show();
            }
        }
    }

    public void refreshDisplay(){
        if(!AppDatabase.isInstance1Filled() || !AppDatabase.isInstance2Filled()){
            fcvChoiceLanguage.setVisibility(View.GONE);
            fcvChoiceTeaserVideo.setVisibility(View.GONE);
            fcvChoiceProductsDisplayed.setVisibility(View.GONE);
            databaseEmpty.setVisibility(View.VISIBLE);
        } else {
            fcvChoiceLanguage.setVisibility(View.VISIBLE);
            fcvChoiceTeaserVideo.setVisibility(View.VISIBLE);
            fcvChoiceProductsDisplayed.setVisibility(View.VISIBLE);
            databaseEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDefaultLanguageModified() {
        //TODO
    }

    @Override
    public void onTeaserModified() {
        //TODO
    }

    @Override
    public void onProductsModified() {
        //TODO
    }

    @Override
    public void onOrderProductsModified() {
        //TODO
    }

    @Override
    public void onLanguagesModified() {
        //TODO
    }
}