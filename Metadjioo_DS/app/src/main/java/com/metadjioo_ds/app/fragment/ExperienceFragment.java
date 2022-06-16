package com.metadjioo_ds.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.presentation.MDSPresentation;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.entity.IsWineVideo;
import com.metadjioo_ds.db.entity.WineCuvee;

import java.util.List;

public class ExperienceFragment extends Fragment {

    public static final int SCROLL_DELTA = 300;
    public static final int MAX_CARDS_BEFORE_SCROLL = 6;

    protected MDSPresentation mPresentation;
    private HorizontalScrollView scrollView;
    private ImageButton btnScrollLeft;
    private ImageButton btnScrollRight;
    private LinearLayout listWineCardsLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.experience, container, false);
        scrollView = view.findViewById(R.id.experience_scroll_view);
        btnScrollLeft = view.findViewById(R.id.scroll_left);
        btnScrollRight = view.findViewById(R.id.scroll_right);
        btnScrollLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollLeft();
            }
        });

        btnScrollRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollRight();
            }
        });
        scrollView.getViewTreeObserver()
                .addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        btnScrollRight.setEnabled(scrollView.canScrollHorizontally(1));
                        btnScrollLeft.setEnabled(scrollView.canScrollHorizontally(-1));
                    }
                });
        return view;
    }

    public void updateWineCards(){
        Activity act = requireActivity();
        //CLEAR LAYOUT
        LinearLayout wineLayout =  act.findViewById(R.id.wine_layout);
        wineLayout.removeAllViews();
        //RETRIEVE WINE CUVEES
        List<IsWineVideo> isWineVideos = AppDatabase.getInstance(this.getContext()).isWineVideoDAO().getDisplayed();
        //CREATE FRAGMENTS
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        btnScrollRight.setEnabled(isWineVideos.size()>MAX_CARDS_BEFORE_SCROLL);
        btnScrollLeft.setEnabled(isWineVideos.size()>MAX_CARDS_BEFORE_SCROLL);
        for(int i = 0 ; i < isWineVideos.size();i++){
            CardWineFragment cardWine = new CardWineFragment(isWineVideos.get(i));
            fragmentManager.beginTransaction().add(R.id.wine_layout, cardWine,null).commit();
        }
//        for(int i = 0 ; i < 2;i++){
//            CardWineFragment cardWine = new CardWineFragment(isWineVideos.get(i));
//            fragmentManager.beginTransaction().add(R.id.wine_layout, cardWine,null).commit();
//        }

    }

    private void scrollRight(){
        int new_x = scrollView.getScrollX()+ExperienceFragment.SCROLL_DELTA;
        scrollView.scrollTo(new_x, scrollView.getScrollY());
    }

    private void scrollLeft(){
        int new_x = scrollView.getScrollX()-ExperienceFragment.SCROLL_DELTA;
        scrollView.scrollTo(new_x, scrollView.getScrollY());
    }
}