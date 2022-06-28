package com.metadjioo_ds.app.activity.used.main_screen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.metadjioo_ds.app.fragment.ConfigDirectFragment;
import com.metadjioo_ds.app.fragment.ConfigFragment;
import com.metadjioo_ds.app.fragment.ExperienceFragment;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.dao.WineCuveeDAO;
import com.metadjioo_ds.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationActivity extends MDSActivityMainScreen implements ConfigObserver {

    private FragmentContainerView mFcvChoiceLanguage;
    private FragmentContainerView mFcvChoiceTeaserVideo;
    private FragmentContainerView mFcvChoiceProductsDisplayed;
    private FragmentContainerView mFcvChoiceOrderProductsDisplayed;
    private FragmentContainerView mFcvChoiceAdditionnalVideo;
    private List<ConfigDirectFragment> mConfigFragments;
    private ExperienceFragment mExperience;
    private TextView databaseEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration);
        mConfigFragments = new ArrayList<>();

        ImageButton refreshDatabase = findViewById(R.id.refresh_database);
        databaseEmpty = findViewById(R.id.database_empty);

        Button validateConfig = findViewById(R.id.validate_config);

        //Get the Fragment Container view
        mFcvChoiceLanguage = findViewById(R.id.choice_language);
        mFcvChoiceTeaserVideo = findViewById(R.id.choice_teaser_video);
        mFcvChoiceProductsDisplayed = findViewById(R.id.choice_products_displayed);
        mFcvChoiceOrderProductsDisplayed = findViewById(R.id.choice_order_products_displayed);
        mFcvChoiceAdditionnalVideo = findViewById(R.id.choice_additionnal_video);

        //Get the fragments
        ChoiceDefaultLanguageFragment choiceLanguage = (ChoiceDefaultLanguageFragment) getSupportFragmentManager().findFragmentById(R.id.choice_language);
        ChoiceCompanyVideoFragment choiceTeaserVideo = (ChoiceCompanyVideoFragment) getSupportFragmentManager().findFragmentById(R.id.choice_teaser_video);
        ChoiceProductsDisplayedFragment choiceProductsDisplayed = (ChoiceProductsDisplayedFragment) getSupportFragmentManager().findFragmentById(R.id.choice_products_displayed);
        ChoiceOrderProductsDisplayedFragment choiceOrderProductsDisplayed = (ChoiceOrderProductsDisplayedFragment) getSupportFragmentManager().findFragmentById(R.id.choice_order_products_displayed);
        ChoiceCompanyVideoFragment choiceAdditionnalVideo = (ChoiceCompanyVideoFragment) getSupportFragmentManager().findFragmentById(R.id.choice_additionnal_video);

        //Add to the list of observers
        mConfigFragments.add(choiceLanguage);
        mConfigFragments.add(choiceTeaserVideo);
        mConfigFragments.add(choiceProductsDisplayed);
        mConfigFragments.add(choiceOrderProductsDisplayed);
        mConfigFragments.add(choiceAdditionnalVideo);

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
        refreshDatabase.setOnClickListener(view -> {
            showProgress();
            appDatabaseCopy.clear();
            appDatabase.clear();
            appDatabase.fill();
            AppDatabase.copyDB1ToDB2();
            refreshDisplay();
            onDatabaseReload();
            Toast.makeText(ConfigurationActivity.this, "Database loaded", Toast.LENGTH_SHORT).show();
            hideProgress();
        });

        validateConfig.setOnClickListener(view -> {
            ConfigurationActivity.this.showProgress();
            finish();
            updateDatabase();
            Toast.makeText(ConfigurationActivity.this, "Changes validated", Toast.LENGTH_SHORT).show();
            ConfigurationActivity.this.hideProgress();
        });

        ImageButton btnGoBack = findViewById(R.id.go_back);
        btnGoBack.setOnClickListener(view -> ConfigurationActivity.this.finish());
    }

    @Override
    public void initSecondScreen() {
        Utils.launchActivityOnSecondScreen(ExperiencePreviewActivity.class);
    }

    @Override
    protected void onSecondScreenReady() {
        super.onSecondScreenReady();
        MDSActivitySecondScreen activitySecondScreen = MDSApp.getCurrentSecondScreenAct();
        if(activitySecondScreen instanceof ExperiencePreviewActivity){
            mExperience = ((ExperiencePreviewActivity)activitySecondScreen).getExperience();
        }
    }

    public void refreshDisplay() {
        if (!AppDatabase.isInstance1Filled() || !AppDatabase.isInstance2Filled()) {
            mFcvChoiceLanguage.setVisibility(View.GONE);
            mFcvChoiceTeaserVideo.setVisibility(View.GONE);
            mFcvChoiceProductsDisplayed.setVisibility(View.GONE);
            mFcvChoiceOrderProductsDisplayed.setVisibility(View.GONE);
            mFcvChoiceAdditionnalVideo.setVisibility(View.GONE);
            databaseEmpty.setVisibility(View.VISIBLE);

        } else {
            mFcvChoiceLanguage.setVisibility(View.VISIBLE);
            mFcvChoiceTeaserVideo.setVisibility(View.VISIBLE);
            mFcvChoiceProductsDisplayed.setVisibility(View.VISIBLE);
            mFcvChoiceAdditionnalVideo.setVisibility(View.VISIBLE);
            databaseEmpty.setVisibility(View.GONE);

            WineCuveeDAO wineCuveeDAO = AppDatabase.getInstance2(getApplicationContext()).wineCuveeDAO();
            if(wineCuveeDAO.getDisplayed().size()<=1){
                mFcvChoiceOrderProductsDisplayed.setVisibility(View.GONE);
            } else {
                mFcvChoiceOrderProductsDisplayed.setVisibility(View.VISIBLE);
            }
        }
    }

    public void updateDatabase(){
        for(ConfigFragment configFragment: mConfigFragments){
            configFragment.updateDatabase();
        }
    }

    @Override
    public void onDefaultLanguageModified() {
        for(ConfigObserver configObserver: mConfigFragments){
            configObserver.onDefaultLanguageModified();
        }
        if(mExperience!=null)mExperience.onDefaultLanguageModified();
    }

    @Override
    public void onTeaserModified() {
        for(ConfigObserver configObserver: mConfigFragments){
            configObserver.onTeaserModified();
        }
        if(mExperience!=null)mExperience.onTeaserModified();
    }

    @Override
    public void onProductsModified() {
        refreshDisplay();
        for(ConfigObserver configObserver: mConfigFragments){
            configObserver.onProductsModified();
        }
        if(mExperience!=null)mExperience.onProductsModified();
    }

    @Override
    public void onOrderProductsModified() {
        for(ConfigObserver configObserver: mConfigFragments){
            configObserver.onOrderProductsModified();
        }
        if(mExperience!=null)mExperience.onOrderProductsModified();
    }

    @Override
    public void onAdditionnalVideoModified() {
        for(ConfigObserver configObserver: mConfigFragments){
            configObserver.onAdditionnalVideoModified();
        }
        if(mExperience!=null)mExperience.onAdditionnalVideoModified();
    }

    @Override
    public void onLanguagesModified() {
        for(ConfigObserver configObserver: mConfigFragments){
            configObserver.onLanguagesModified();
        }
        if(mExperience!=null)mExperience.onLanguagesModified();
    }

    @Override
    public void onDatabaseReload() {
        for(ConfigObserver configObserver: mConfigFragments){
            configObserver.onDatabaseReload();
        }
        if(mExperience!=null)mExperience.onDatabaseReload();
    }
}