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
import androidx.fragment.app.FragmentTransaction;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.app.activity.MDSActivity;
import com.metadjioo_ds.app.activity.MDSActivitySecondScreen;
import com.metadjioo_ds.app.activity.used.second_screen.VideoDataSheetActivity;
import com.metadjioo_ds.app.adapter.ImageArrayAdapter;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.dao.LanguageDAO;
import com.metadjioo_ds.db.entity.CompanyVideo;
import com.metadjioo_ds.db.entity.HasCategoryWineVideo;
import com.metadjioo_ds.db.entity.Language;

import java.util.ArrayList;
import java.util.List;

public class ExperienceFragment extends Fragment implements ConfigObserver {

    public static final int SCROLL_DELTA = 300;
    public static final int MAX_CARDS_BEFORE_SCROLL = 6;

    protected MDSActivity mActivity;
    private Spinner mSpinnerLanguage;
    private boolean mLanguageChanging;
    private HorizontalScrollView mScrollView;
    private ImageButton mBtnScrollLeft;
    private ImageButton mBtnScrollRight;
    private TextView mAdditionnalVideoTitle;
    private ImageButton mBtnStartAdditionnalVideo;
    private final List<CardWineFragment> mCardWineFragments;
    private final boolean mIsOnMainScreen;
    private AppDatabase mDatabase;

    public ExperienceFragment(MDSActivity act, AppDatabase db, boolean ioms){
        mActivity = act;
        mDatabase = db;
        mIsOnMainScreen = ioms;
        mLanguageChanging = false;
        mCardWineFragments = new ArrayList<>();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experience, container, false);
        mScrollView = view.findViewById(R.id.experience_scroll_view);
        mBtnScrollLeft = view.findViewById(R.id.scroll_left);
        mBtnScrollRight = view.findViewById(R.id.scroll_right);
        mSpinnerLanguage = view.findViewById(R.id.spinner_language);
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
        mSpinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!mLanguageChanging){
                    LanguageDAO languageDAO = mDatabase.languageDAO();
                    Language language = (Language) mSpinnerLanguage.getSelectedItem();
                    languageDAO.resetSelected(false);
                    languageDAO.updateSelected(true,language.country_code);
                    updateWineCards();
                    updateAdditionnalVideo();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        updateLanguage();
        initWineCards();
        updateAdditionnalVideo();
    }

    private void scrollRight() {
        int new_x = mScrollView.getScrollX() + ExperienceFragment.SCROLL_DELTA;
        mScrollView.scrollTo(new_x, mScrollView.getScrollY());
    }

    private void scrollLeft() {
        int new_x = mScrollView.getScrollX() - ExperienceFragment.SCROLL_DELTA;
        mScrollView.scrollTo(new_x, mScrollView.getScrollY());
    }

    private void updateLanguage(){
        mLanguageChanging = true;
        ImageArrayAdapter adapter = new ImageArrayAdapter(this.getContext(), mDatabase.languageDAO().getAllDisplayed(),R.layout.language_item_spinner_experience);
        mSpinnerLanguage.setAdapter(adapter);

        LanguageDAO languageDAO = mDatabase.languageDAO();
        Language languageSelected = languageDAO.getSelectedDefault();

        mSpinnerLanguage.setSelection(adapter.getPosition(languageSelected));
        mLanguageChanging = false;
    }

    private void initWineCards() {
        mActivity.showProgress();
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();

        //CLEAR FRAGMENTS
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for(int i = 0; i < mCardWineFragments.size();i++){
            fragmentTransaction.remove(mCardWineFragments.get(i));
        }
        fragmentTransaction.commit();
        mCardWineFragments.clear();
        //RETRIEVE DATA DISPLAYED
        List<HasCategoryWineVideo> hasCategoryWineVideos = mDatabase.hasCategoryWineVideoDAO().getDisplayed();
        //CREATE FRAGMENTS
        mBtnScrollRight.setEnabled(hasCategoryWineVideos.size() > MAX_CARDS_BEFORE_SCROLL);
        mBtnScrollLeft.setEnabled(hasCategoryWineVideos.size() > MAX_CARDS_BEFORE_SCROLL);
        MDSActivitySecondScreen activitySecondScreen = MDSApp.getCurrentSecondScreenAct();
        for (int i = 0; i < hasCategoryWineVideos.size(); i++) {
            HasCategoryWineVideo hasCategoryWineVideo = hasCategoryWineVideos.get(i);
            CardWineFragment cardWine;
            if(mIsOnMainScreen && activitySecondScreen instanceof VideoDataSheetActivity) {
                cardWine = new CardWineFragment(mDatabase, (VideoDataSheetActivity) activitySecondScreen, hasCategoryWineVideo.id_wine_cuvee, hasCategoryWineVideo.id_category_video);
            } else {
                cardWine = new CardWineFragment(mDatabase, hasCategoryWineVideo.id_wine_cuvee, hasCategoryWineVideo.id_category_video);
            }
            mCardWineFragments.add(cardWine);
            fragmentManager.beginTransaction().add(R.id.wine_layout, cardWine, null).commit();
        }
        //Additional video
        mActivity.hideProgress();
    }

    private void updateWineCards() {
        //RETRIEVE DATA DISPLAYED
        List<HasCategoryWineVideo> hasCategoryWineVideos = mDatabase.hasCategoryWineVideoDAO().getDisplayed();  //TODO order
        int sizeHCWV = hasCategoryWineVideos.size();
        if(sizeHCWV != mCardWineFragments.size()){
            initWineCards();
        } else {
            mActivity.showProgress();
            //CREATE FRAGMENTS
            for (int i = 0; i <sizeHCWV; i++) {
                HasCategoryWineVideo hasCategoryWineVideo = hasCategoryWineVideos.get(i);
                CardWineFragment cardWine = mCardWineFragments.get(i);
                cardWine.setIDs(hasCategoryWineVideo.id_wine_cuvee, hasCategoryWineVideo.id_category_video);
                cardWine.refreshDisplay();
            }
            mActivity.hideProgress();
        }
    }

    private void updateAdditionnalVideo() {
        //Get the video
        CompanyVideo companyVideo = mDatabase.companyVideoDAO().getDisplayed(false);
        mAdditionnalVideoTitle.setText(companyVideo.title_video);

        MDSActivitySecondScreen activitySecondScreen = MDSApp.getCurrentSecondScreenAct();
        if(mIsOnMainScreen && activitySecondScreen instanceof VideoDataSheetActivity) {
            mBtnStartAdditionnalVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((VideoDataSheetActivity) activitySecondScreen).setVideo(companyVideo.path_video, false);
                }
            });
        }
    }

    @Override
    public void onDefaultLanguageModified() {
        updateLanguage();
        updateWineCards();
    }

    @Override
    public void onTeaserModified() {
        //nothing : nothing has to be done
    }

    @Override
    public void onProductsModified() {
        initWineCards();
    }

    @Override
    public void onOrderProductsModified() {
        updateWineCards();
    }

    @Override
    public void onAdditionnalVideoModified() {
        updateAdditionnalVideo();
    }

    @Override
    public void onLanguagesModified() {
        updateLanguage();
    }

    @Override
    public void onDatabaseReload() {
        init();
    }
}