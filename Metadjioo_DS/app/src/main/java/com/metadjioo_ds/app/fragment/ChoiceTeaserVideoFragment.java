package com.metadjioo_ds.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

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

public class ChoiceTeaserVideoFragment extends ConfigObservableFragment implements ConfigObserver {

    private RadioGroup radioGroupTeaser;
    private List<CardCompanyVideoFragment> cardCompanyVideoFragments;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choice_teaser_video, container, false);
        cardCompanyVideoFragments = new ArrayList<>();
        radioGroupTeaser = view.findViewById(R.id.radio_group_teaser);
        init();
        return view;
    }

    @Override
    protected void init() {
        Context context = getContext();

        //Init
        radioGroupTeaser.removeAllViews();
        cardCompanyVideoFragments.clear();

        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
        List<CompanyVideo> companyTeasers = companyVideoDAO.getTeasers();
        int teasersSize = companyTeasers.size();
        int nbByLine = Utils.organize(teasersSize,4);
        int nbLine = (int) Math.ceil(teasersSize/(float)nbByLine);

        //Create lines
        LinearLayout[] linearLayouts = new LinearLayout[nbLine];
        for(int i = 0 ; i < nbLine;i++){
            linearLayouts[i] = new LinearLayout(context);
            linearLayouts[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            linearLayouts[i].setOrientation(LinearLayout.HORIZONTAL);
            linearLayouts[i].setId(Utils.generateViewId());
        }

        //Add lines
        for(int i = 0 ; i < nbLine;i++){
            radioGroupTeaser.addView(linearLayouts[i]);
        }

        //Create elements and add them to the lines
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        for(int i = 0 ; i < teasersSize;i++){
            int indexLayout = i/nbByLine;

            LinearLayout linearLayout = (LinearLayout) radioGroupTeaser.getChildAt(indexLayout);

            CardCompanyVideoFragment cardCompanyVideo = new CardCompanyVideoFragment(companyTeasers.get(i).id_company_video);
            cardCompanyVideoFragments.add(cardCompanyVideo);
            cardCompanyVideo.setConfigObserver(this);
            fragmentManager.beginTransaction().add(linearLayout.getId(), cardCompanyVideo, null).commit();
        }
    }

    @Override
    public void teaserModified() {
        mConfigObserver.onTeaserModified();
    }

    @Override
    public void updateDatabase() {
        int nbTeasers = cardCompanyVideoFragments.size();
        for(int i = 0 ; i < nbTeasers; i++){
            cardCompanyVideoFragments.get(i).updateDatabase();
        }
    }

    @Override
    public void onDefaultLanguageModified() {
        //nothing : not affected
    }

    @Override
    public void onTeaserModified() {
        int nbTeasers = cardCompanyVideoFragments.size();
        for(int i = 0 ; i < nbTeasers; i++){
            cardCompanyVideoFragments.get(i).onTeaserModified();
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
        //nothing : not affected
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