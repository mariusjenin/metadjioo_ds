package com.metadjioo_ds.app.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivity;
import com.metadjioo_ds.app.adapter.ImageArrayAdapter;
import com.metadjioo_ds.app.presentation.VideoDataSheetPresentation;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.dao.LanguageDAO;
import com.metadjioo_ds.db.entity.CompanyVideo;
import com.metadjioo_ds.db.entity.HasCategoryWineVideo;
import com.metadjioo_ds.db.entity.Language;

import java.util.ArrayList;
import java.util.List;

public class ExperienceFragment extends Fragment {

    public static final int SCROLL_DELTA = 300;
    public static final int MAX_CARDS_BEFORE_SCROLL = 6;

    protected VideoDataSheetPresentation mPresentation;
    protected MDSActivity mActivity;
    private HorizontalScrollView mScrollView;
    private ImageButton mBtnScrollLeft;
    private ImageButton mBtnScrollRight;
    private TextView mAdditionnalVideoTitle;
    private ImageButton mBtnStartAdditionnalVideo;
    private final List<CardWineFragment> cardWineFragments;

    public ExperienceFragment(MDSActivity act){
        mActivity = act;
        cardWineFragments = new ArrayList<>();
    }

    public void setPresentation( VideoDataSheetPresentation pres){
        mPresentation = pres;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experience, container, false);
        mScrollView = view.findViewById(R.id.experience_scroll_view);
        mBtnScrollLeft = view.findViewById(R.id.scroll_left);
        mBtnScrollRight = view.findViewById(R.id.scroll_right);
        Spinner spinnerLanguage = view.findViewById(R.id.spinner_language);
        mAdditionnalVideoTitle = view.findViewById(R.id.additionnal_video_title);
        mBtnStartAdditionnalVideo = view.findViewById(R.id.start_additionnal_video);
        mBtnScrollLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollLeft();
            }
        });

        mBtnScrollRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollRight();
            }
        });
        mScrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        mBtnScrollRight.setEnabled(mScrollView.canScrollHorizontally(1));
                        mBtnScrollLeft.setEnabled(mScrollView.canScrollHorizontally(-1));
                    }
                });
        ImageArrayAdapter adapter = new ImageArrayAdapter(this.getContext(), AppDatabase.getInstance1(this.getContext()).languageDAO().getAllDisplayed(),R.layout.language_item_spinner_experience);
        spinnerLanguage.setAdapter(adapter);

        LanguageDAO languageDAO = AppDatabase.getInstance1(getContext()).languageDAO();
        Language languageSelected = languageDAO.getSelectedDefault();

        spinnerLanguage.setSelection(adapter.getPosition(languageSelected));
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Language language = (Language) spinnerLanguage.getSelectedItem();
                languageDAO.resetSelected(false);
                languageDAO.updateSelected(true,language.country_code);
                updateWineCards();
                updateAdditionnalVideo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWineCards();
    }

    public void initWineCards() {
        mActivity.showProgress();
        Activity act = requireActivity();
        //CLEAR LAYOUT
        LinearLayout wineLayout = act.findViewById(R.id.wine_layout);
        wineLayout.removeAllViews();
        cardWineFragments.clear();
        //RETRIEVE DATA DISPLAYED
        List<HasCategoryWineVideo> hasCategoryWineVideos = AppDatabase.getInstance1(this.getContext()).hasCategoryWineVideoDAO().getDisplayed();
        //CREATE FRAGMENTS
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        mBtnScrollRight.setEnabled(hasCategoryWineVideos.size() > MAX_CARDS_BEFORE_SCROLL);
        mBtnScrollLeft.setEnabled(hasCategoryWineVideos.size() > MAX_CARDS_BEFORE_SCROLL);
        for (int i = 0; i < hasCategoryWineVideos.size(); i++) {
            HasCategoryWineVideo hasCategoryWineVideo = hasCategoryWineVideos.get(i);
            CardWineFragment cardWine;
            if(mPresentation == null){
                cardWine = new CardWineFragment(hasCategoryWineVideo.id_wine_cuvee, hasCategoryWineVideo.id_category_video);
            } else {
                cardWine = new CardWineFragment(mPresentation,hasCategoryWineVideo.id_wine_cuvee, hasCategoryWineVideo.id_category_video);
            }
            cardWineFragments.add(cardWine);
            fragmentManager.beginTransaction().add(R.id.wine_layout, cardWine, null).commit();
        }
        //Additional video
        mActivity.hideProgress();
    }

    public void updateWineCards() {
        mActivity.showProgress();
        //RETRIEVE DATA DISPLAYED
        List<HasCategoryWineVideo> hasCategoryWineVideos = AppDatabase.getInstance1(this.getContext()).hasCategoryWineVideoDAO().getDisplayed();
        //CREATE FRAGMENTS
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        for (int i = 0; i < hasCategoryWineVideos.size(); i++) {
            HasCategoryWineVideo hasCategoryWineVideo = hasCategoryWineVideos.get(i);
            CardWineFragment cardWine = cardWineFragments.get(i);
            cardWine.updateDisplay(hasCategoryWineVideo.id_wine_cuvee, hasCategoryWineVideo.id_category_video);
//            fragmentManager.beginTransaction().add(R.id.wine_layout, cardWine, null).commit();
        }
        //Additional video
        mActivity.hideProgress();
    }

    public void updateAdditionnalVideo() {
        //Get the video
        CompanyVideo companyVideo = AppDatabase.getInstance1(this.getContext()).companyVideoDAO().getDisplayed(false);
        mAdditionnalVideoTitle.setText(companyVideo.title_video);

        if(mPresentation != null) {
            mBtnStartAdditionnalVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresentation.setVideo(companyVideo.path_video, false);
                }
            });
        }
    }

    private void scrollRight() {
        int new_x = mScrollView.getScrollX() + ExperienceFragment.SCROLL_DELTA;
        mScrollView.scrollTo(new_x, mScrollView.getScrollY());
    }

    private void scrollLeft() {
        int new_x = mScrollView.getScrollX() - ExperienceFragment.SCROLL_DELTA;
        mScrollView.scrollTo(new_x, mScrollView.getScrollY());
    }
}