package com.metadjioo_ds.app.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.MDSActivity;
import com.metadjioo_ds.app.adapter.ImageArrayAdapter;
import com.metadjioo_ds.app.presentation.MDSPresentation;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.dao.LanguageDAO;
import com.metadjioo_ds.db.entity.IsWineVideo;
import com.metadjioo_ds.db.entity.Language;

import java.util.List;

public class ExperienceFragment extends Fragment {

    public static final int SCROLL_DELTA = 300;
    public static final int MAX_CARDS_BEFORE_SCROLL = 6;

    protected MDSPresentation mPresentation;
    protected MDSActivity mActivity;
    private HorizontalScrollView mscrollView;
    private ImageButton mbtnScrollLeft;
    private ImageButton mbtnScrollRight;
    private LinearLayout mlistWineCardsLayout;

    public ExperienceFragment(MDSActivity act, MDSPresentation pres){
        mActivity = act;
        mPresentation = pres;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experience, container, false);
        mscrollView = view.findViewById(R.id.experience_scroll_view);
        mbtnScrollLeft = view.findViewById(R.id.scroll_left);
        mbtnScrollRight = view.findViewById(R.id.scroll_right);
        Spinner spinnerLanguage = view.findViewById(R.id.spinner_language);
        mbtnScrollLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollLeft();
            }
        });

        mbtnScrollRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollRight();
            }
        });
        mscrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        mbtnScrollRight.setEnabled(mscrollView.canScrollHorizontally(1));
                        mbtnScrollLeft.setEnabled(mscrollView.canScrollHorizontally(-1));
                    }
                });
        ImageArrayAdapter adapter = new ImageArrayAdapter(this.getContext(), AppDatabase.getInstance(this.getContext()).languageDAO().getAll());
        spinnerLanguage.setAdapter(adapter);

        LanguageDAO languageDAO = AppDatabase.getInstance(getContext()).languageDAO();
        Language languageSelected = languageDAO.getSelectedDefault();
        Log.e("",""+adapter.getPosition(languageSelected));
        spinnerLanguage.setSelection(adapter.getPosition(languageSelected));
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Language language = (Language) spinnerLanguage.getSelectedItem();
                languageDAO.resetSelected(false);
                languageDAO.updateSelected(true,language.country_code);
                updateWineCards();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });
        return view;
    }

    public void updateWineCards() {
        mActivity.showProgress();
        Activity act = requireActivity();
        //CLEAR LAYOUT
        LinearLayout wineLayout = act.findViewById(R.id.wine_layout);
        wineLayout.removeAllViews();
        //RETRIEVE WINE DISPLAYED
        List<IsWineVideo> isWineVideos = AppDatabase.getInstance(this.getContext()).isWineVideoDAO().getDisplayed();
        //CREATE FRAGMENTS
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        mbtnScrollRight.setEnabled(isWineVideos.size() > MAX_CARDS_BEFORE_SCROLL);
        mbtnScrollLeft.setEnabled(isWineVideos.size() > MAX_CARDS_BEFORE_SCROLL);
        for (int i = 0; i < isWineVideos.size(); i++) {
            IsWineVideo isWineVideo = isWineVideos.get(i);
            CardWineFragment cardWine = new CardWineFragment(isWineVideo.id_wine_cuvee, isWineVideo.id_video);
            fragmentManager.beginTransaction().add(R.id.wine_layout, cardWine, null).commit();
        }
        mActivity.hideProgress();
    }

    private void scrollRight() {
        int new_x = mscrollView.getScrollX() + ExperienceFragment.SCROLL_DELTA;
        mscrollView.scrollTo(new_x, mscrollView.getScrollY());
    }

    private void scrollLeft() {
        int new_x = mscrollView.getScrollX() - ExperienceFragment.SCROLL_DELTA;
        mscrollView.scrollTo(new_x, mscrollView.getScrollY());
    }
}