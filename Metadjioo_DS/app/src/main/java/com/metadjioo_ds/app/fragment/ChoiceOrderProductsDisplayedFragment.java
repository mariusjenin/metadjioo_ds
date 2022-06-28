package com.metadjioo_ds.app.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jmedeisis.draglinearlayout.DragLinearLayout;
import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.db.dao.HasCategoryWineVideoDAO;
import com.metadjioo_ds.db.dao.WineCuveeDAO;
import com.metadjioo_ds.db.dao.WineCuveeDatasDAO;
import com.metadjioo_ds.db.entity.Wine;
import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.utils.ImgSaver;
import com.metadjioo_ds.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChoiceOrderProductsDisplayedFragment extends ConfigDirectFragment implements ConfigObserver {
    private DragLinearLayout mDragLinearLayout;
    private LinearLayout mNumberLayout;
    private List<WineCuvee> mWineCuvees;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choice_order_wine_displayed, container, false);
        mWineCuvees = new ArrayList<>();
        mDragLinearLayout = view.findViewById(R.id.drag_layout_wine_displayed);
        mNumberLayout = view.findViewById(R.id.number_list);
        init();
        return view;
    }

    public DragLinearLayout getDragLinearLayout() {
        return mDragLinearLayout;
    }

    @Override
    protected void init() {
        WineCuveeDAO wineCuveeDAO = copyDB.wineCuveeDAO();
        WineCuveeDatasDAO wineCuveeDatasDAO = copyDB.wineCuveeDatasDAO();
        mWineCuvees = wineCuveeDAO.getDisplayed();

        mDragLinearLayout.removeAllViews();
        mNumberLayout.removeAllViews();

        int sizeWineCuvees = mWineCuvees.size();
        for (int i = 0; i < sizeWineCuvees; i++) {
            WineCuvee wineCuvee = mWineCuvees.get(i);
            WineCuveeDatas wineCuveeDatas = wineCuveeDatasDAO.get(wineCuvee.id_wine_cuvee);

            //LayoutParams
            ConstraintLayout.LayoutParams numberItemlayoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, Utils.dpToPx(100));
            numberItemlayoutParams.setMargins(0,Utils.dpToPx(10),0,0);
            ConstraintLayout.LayoutParams cardDraggablelayoutParams = new ConstraintLayout.LayoutParams(numberItemlayoutParams);

            //Number item
            TextView numberItem = new TextView(getContext());
            ViewCompat.setElevation(numberItem, Utils.dpToPx(2));
            numberItem.setTextSize(Utils.spToPx(24));
            numberItem.setTextColor(Color.BLACK);
            numberItem.setTypeface(numberItem.getTypeface(), Typeface.BOLD);
            numberItem.setGravity(Gravity.CENTER);
            numberItem.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.round_white_roundless));
            numberItem.setText(String.valueOf(i));
            numberItem.setLayoutParams(numberItemlayoutParams);

            //Child draggable
            ConstraintLayout childDraggable = (ConstraintLayout) View.inflate(getContext(), R.layout.card_wine_displayed_draggable, null);
            TextView titleWine = childDraggable.findViewById(R.id.title_wine);
            ImageView imgWine = childDraggable.findViewById(R.id.img_wine);
            titleWine.setText(wineCuveeDatas.name);
            Bitmap bmp = new ImgSaver(MDSApp.getContext()).setDirectoryName(wineCuvee.img_directory).setFileName(wineCuvee.img_name).load();
            imgWine.setImageBitmap(bmp);
            childDraggable.setLayoutParams(cardDraggablelayoutParams);

            mNumberLayout.addView(numberItem);
            mDragLinearLayout.addDragView(childDraggable, childDraggable.findViewById(R.id.drag_icon));
        }

        mDragLinearLayout.setOnViewSwapListener(new DragLinearLayout.OnViewSwapListener() {
            @Override
            public void onSwap(View firstView, int firstPosition, View secondView, int secondPosition) {
                WineCuvee firstWineCuvee = mWineCuvees.get(firstPosition);
                WineCuvee secondWineCuvee = mWineCuvees.get(secondPosition);
                mWineCuvees.set(firstPosition,secondWineCuvee);
                mWineCuvees.set(secondPosition,firstWineCuvee);
                orderProductsModified();
                Log.e(""+firstPosition,""+secondPosition);
            }
        });
        orderProductsModified();
    }

    @Override
    public void orderProductsModified() {
        WineCuveeDAO wineCuveeDAO = copyDB.wineCuveeDAO();
        int sizeWineCuvees = mWineCuvees.size();
        for (int i = 0; i < sizeWineCuvees; i++) {
            WineCuvee wineCuvee = mWineCuvees.get(i);
            wineCuveeDAO.updateOrder(i,wineCuvee.id_wine_cuvee);
        }
        mConfigObserver.onOrderProductsModified();
    }


    @Override
    public void updateDatabase() {
        WineCuveeDAO wineCuveeDAO = db.wineCuveeDAO();
        mWineCuvees = copyDB.wineCuveeDAO().getDisplayed();
        int sizeWineCuvees = mWineCuvees.size();
        for (int i = 0; i < sizeWineCuvees; i++) {
            WineCuvee wineCuvee = mWineCuvees.get(i);
            wineCuveeDAO.updateOrder(i,wineCuvee.id_wine_cuvee);
        }
    }

    @Override
    public void onDefaultLanguageModified() {
        init();
    }

    @Override
    public void onTeaserModified() {
        //nothing : can't trigger this
    }

    @Override
    public void onProductsModified() {
        init();
    }

    @Override
    public void onOrderProductsModified() {
        //nothing : can't trigger this
    }

    @Override
    public void onAdditionnalVideoModified() {
        //nothing : can't trigger this
    }

    @Override
    public void onLanguagesModified() {
        //nothing : can't trigger this
    }

    @Override
    public void onDatabaseReload() {
        init();
    }
}