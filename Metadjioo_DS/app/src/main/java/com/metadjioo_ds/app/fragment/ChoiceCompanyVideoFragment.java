package com.metadjioo_ds.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.db.dao.CompanyVideoDAO;
import com.metadjioo_ds.db.entity.CompanyVideo;
import com.metadjioo_ds.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ChoiceCompanyVideoFragment extends ConfigDirectFragment implements ConfigObserver {

    private RadioGroup mRadioGroupVideo;
    private TextView mTitleChoiceCompanyVideo;
    private List<CardCompanyVideoFragment> mCardCompanyVideoFragments;
    private boolean mIsTeaser;

    public void setIsTeaser(boolean isTeaser) {
        this.mIsTeaser = isTeaser;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choice_company_video, container, false);
        mCardCompanyVideoFragments = new ArrayList<>();
        mRadioGroupVideo = view.findViewById(R.id.radio_group_company_video);
        mTitleChoiceCompanyVideo = view.findViewById(R.id.title_choice_company_video);
        init();
        return view;
    }

    @Override
    protected void init() {
        Context context = getContext();

        //Title
        if(mIsTeaser){
            mTitleChoiceCompanyVideo.setText(getResources().getText(R.string.choice_teaser_video));
        } else {
            mTitleChoiceCompanyVideo.setText(getResources().getText(R.string.choice_additionnal_video));
        }

        //Init
        mRadioGroupVideo.removeAllViews();

        mCardCompanyVideoFragments.clear();

        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
        List<CompanyVideo> companyVideos = companyVideoDAO.getWithType(mIsTeaser);
        int sizeCompanyVideo = companyVideos.size();
        int nbByLine = Utils.organize(sizeCompanyVideo,4);
        int nbLine = (int) Math.ceil(sizeCompanyVideo /(float)nbByLine);

        //Create lines
        LinearLayout[] linearLayouts = new LinearLayout[nbLine];
        for(int i = 0 ; i < nbLine;i++){
            linearLayouts[i] = new LinearLayout(context);
            linearLayouts[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            linearLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            linearLayouts[i].setId(View.generateViewId());
        }

        //Add lines
        for(int i = 0 ; i < nbLine;i++){
            mRadioGroupVideo.addView(linearLayouts[i]);
        }

        //Create elements and add them to the lines
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        for(int i = 0; i < sizeCompanyVideo; i++){
            int indexLayout = i/nbByLine;

            LinearLayout linearLayout = (LinearLayout) mRadioGroupVideo.getChildAt(indexLayout);

            CardCompanyVideoFragment cardCompanyVideo = new CardCompanyVideoFragment(companyVideos.get(i).id_company_video,mIsTeaser);
            mCardCompanyVideoFragments.add(cardCompanyVideo);
            cardCompanyVideo.setConfigObserver(this);
            fragmentManager.beginTransaction().add(linearLayout.getId(), cardCompanyVideo, null).commit();
        }
    }

    @Override
    public void teaserModified() {
        mConfigObserver.onTeaserModified();
    }

    @Override
    public void additionnalVideoModified() {
        mConfigObserver.onAdditionnalVideoModified();
    }

    @Override
    public void updateDatabase() {
        int nbTeasers = mCardCompanyVideoFragments.size();
        for(int i = 0 ; i < nbTeasers; i++){
            mCardCompanyVideoFragments.get(i).updateDatabase();
        }
    }

    @Override
    public void onDefaultLanguageModified() {
        //nothing : not affected
    }

    @Override
    public void onTeaserModified() {
        Log.e("ChoiceCompany","onTeaserModified");
        if(mIsTeaser){
            int nbTeasers = mCardCompanyVideoFragments.size();
            for(int i = 0 ; i < nbTeasers; i++){
                mCardCompanyVideoFragments.get(i).onTeaserModified();
            }
        }
    }

    @Override
    public void onProductsModified() {
        //nothing : not affected
    }

    @Override
    public void onOrderProductsModified() {
        //nothing : not affected
    }

    @Override
    public void onAdditionnalVideoModified() {
        if(!mIsTeaser) {
            int nbTeasers = mCardCompanyVideoFragments.size();
            for (int i = 0; i < nbTeasers; i++) {
                mCardCompanyVideoFragments.get(i).onAdditionnalVideoModified();
            }
        }
    }

    @Override
    public void onLanguagesModified() {
        //nothing : not affected
    }

    @Override
    public void onDatabaseReload() {
        init();
    }
}