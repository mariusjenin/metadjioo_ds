package com.metadjioo_ds.app.activity.used.main_screen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentContainerView;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.app.activity.MDSActivityMainScreen;
import com.metadjioo_ds.app.activity.MDSActivitySecondScreen;
import com.metadjioo_ds.app.activity.used.second_screen.ExperiencePreviewActivity;
import com.metadjioo_ds.app.fragment.ChoiceCompanyVideoFragment;
import com.metadjioo_ds.app.fragment.ChoiceDefaultLanguageFragment;
import com.metadjioo_ds.app.fragment.ChoiceOrderProductsDisplayedFragment;
import com.metadjioo_ds.app.fragment.ChoiceProductsDisplayedFragment;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationActivity extends MDSActivityMainScreen implements ConfigObserver {

    private FragmentContainerView fcvChoiceLanguage;
    private FragmentContainerView fcvChoiceTeaserVideo;
    private FragmentContainerView fcvChoiceProductsDisplayed;
    private FragmentContainerView fcvChoiceOrderProductsDisplayed;
    private FragmentContainerView fcvChoiceAdditionnalVideo;
    private List<ConfigObserver> configObservers;
    private TextView databaseEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration);
        configObservers = new ArrayList<>();

        ImageButton refreshDatabase = findViewById(R.id.refresh_database);
        databaseEmpty = findViewById(R.id.database_empty);

        //Get the Fragment Container view
        fcvChoiceLanguage = findViewById(R.id.choice_language);
        fcvChoiceTeaserVideo = findViewById(R.id.choice_teaser_video);
        fcvChoiceProductsDisplayed = findViewById(R.id.choice_products_displayed);
        fcvChoiceOrderProductsDisplayed = findViewById(R.id.choice_order_products_displayed);
        fcvChoiceAdditionnalVideo = findViewById(R.id.choice_additionnal_video);

        //Get the fragments
        ChoiceDefaultLanguageFragment choiceLanguage = (ChoiceDefaultLanguageFragment) getSupportFragmentManager().findFragmentById(R.id.choice_language);
        ChoiceCompanyVideoFragment choiceTeaserVideo = (ChoiceCompanyVideoFragment) getSupportFragmentManager().findFragmentById(R.id.choice_teaser_video);
        ChoiceProductsDisplayedFragment choiceProductsDisplayed = (ChoiceProductsDisplayedFragment) getSupportFragmentManager().findFragmentById(R.id.choice_products_displayed);
        ChoiceOrderProductsDisplayedFragment choiceOrderProductsDisplayed = (ChoiceOrderProductsDisplayedFragment) getSupportFragmentManager().findFragmentById(R.id.choice_order_products_displayed);
        ChoiceCompanyVideoFragment choiceAdditionnalVideo = (ChoiceCompanyVideoFragment) getSupportFragmentManager().findFragmentById(R.id.choice_additionnal_video);

        //Add to the list of observers
        configObservers.add(choiceLanguage);
        configObservers.add(choiceTeaserVideo);
        configObservers.add(choiceProductsDisplayed);
        configObservers.add(choiceOrderProductsDisplayed);
        configObservers.add(choiceAdditionnalVideo);

        assert choiceLanguage != null;
        choiceLanguage.setConfigObserver(this);

        assert choiceTeaserVideo != null;
        choiceTeaserVideo.setConfigObserver(this);
        choiceTeaserVideo.setIsTeaser(true);

        assert choiceProductsDisplayed != null;
        choiceProductsDisplayed.setConfigObserver(this);

        assert choiceOrderProductsDisplayed != null;
        choiceOrderProductsDisplayed.setConfigObserver(this);

        assert choiceAdditionnalVideo != null;
        choiceAdditionnalVideo.setConfigObserver(this);
        choiceAdditionnalVideo.setIsTeaser(false);
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
                onDatabaseReload();
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

    @Override
    public void initSecondScreen() {
//        TODO remove
//         AppDatabase.getInstance1(this).clear();
//         AppDatabase.getInstance2(this).clear();
//         AppDatabase.getInstance1(this).fill();
//         AppDatabase.copyDB1ToDB2();
        Utils.launchActivityOnSecondScreen(ExperiencePreviewActivity.class);
    }

    @Override
    protected void onSecondScreenReady() {
        super.onSecondScreenReady();
        MDSActivitySecondScreen activitySecondScreen = MDSApp.getCurrentSecondScreenAct();
        if(activitySecondScreen instanceof ExperiencePreviewActivity){
            configObservers.add(((ExperiencePreviewActivity)activitySecondScreen).getExperience());
        }
    }

    public void refreshDisplay() {
        if (!AppDatabase.isInstance1Filled() || !AppDatabase.isInstance2Filled()) {
            fcvChoiceLanguage.setVisibility(View.GONE);
            fcvChoiceTeaserVideo.setVisibility(View.GONE);
            fcvChoiceProductsDisplayed.setVisibility(View.GONE);
            fcvChoiceOrderProductsDisplayed.setVisibility(View.GONE);
            fcvChoiceAdditionnalVideo.setVisibility(View.GONE);
            databaseEmpty.setVisibility(View.VISIBLE);
        } else {
            fcvChoiceLanguage.setVisibility(View.VISIBLE);
            fcvChoiceTeaserVideo.setVisibility(View.VISIBLE);
            fcvChoiceProductsDisplayed.setVisibility(View.VISIBLE);
            fcvChoiceOrderProductsDisplayed.setVisibility(View.VISIBLE);
            fcvChoiceAdditionnalVideo.setVisibility(View.VISIBLE);
            databaseEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDefaultLanguageModified() {
        for(ConfigObserver configObserver: configObservers){
            configObserver.onDefaultLanguageModified();
        }
    }

    @Override
    public void onTeaserModified() {
        for(ConfigObserver configObserver: configObservers){
            configObserver.onTeaserModified();
        }
    }

    @Override
    public void onProductsModified() {
        for(ConfigObserver configObserver: configObservers){
            configObserver.onProductsModified();
        }
    }

    @Override
    public void onOrderProductsModified() {
        for(ConfigObserver configObserver: configObservers){
            configObserver.onOrderProductsModified();
        }
    }

    @Override
    public void onAdditionnalVideoModified() {
        for(ConfigObserver configObserver: configObservers){
            configObserver.onAdditionnalVideoModified();
        }
    }

    @Override
    public void onLanguagesModified() {
        for(ConfigObserver configObserver: configObservers){
            configObserver.onLanguagesModified();
        }
    }

    @Override
    public void onDatabaseReload() {
        for(ConfigObserver configObserver: configObservers){
            configObserver.onDatabaseReload();
        }
    }
}