package com.metadjioo_ds.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.db.dao.HasCategoryWineVideoDAO;
import com.metadjioo_ds.db.dao.WineCuveeDAO;
import com.metadjioo_ds.db.entity.WineCuvee;

import java.util.ArrayList;
import java.util.List;

public class ChoiceProductsDisplayedFragment extends ConfigDirectFragment implements ConfigObserver {
    private TableLayout mTableLayoutWineDisplayed;
    private List<WineConfigTupleFragment> mWineConfigTupleFragments;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choice_wine_displayed, container, false);
        mWineConfigTupleFragments = new ArrayList<>();
        mTableLayoutWineDisplayed = view.findViewById(R.id.table_layout_wine_displayed);
        init();
        return view;
    }

    @Override
    protected void init() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for(int i = 0; i < mWineConfigTupleFragments.size();i++){
            fragmentTransaction.remove(mWineConfigTupleFragments.get(i));
        }
        fragmentTransaction.commit();
        mWineConfigTupleFragments.clear();

        //DAO
        WineCuveeDAO wineCuveeDAO = copyDB.wineCuveeDAO();
        HasCategoryWineVideoDAO hasCategoryWineVideoDAO = copyDB.hasCategoryWineVideoDAO();

        List<WineCuvee> wineCuveesConfigurable = wineCuveeDAO.getAllConfigurable();
        List<WineCuvee> wineCuveesNonConfigurable = wineCuveeDAO.getAllNonConfigurable();
        int sizeWineCuveesConfigurable = wineCuveesConfigurable.size();
        int sizeWineCuveesNonConfigurable = wineCuveesNonConfigurable.size();

        for(int i = 0 ; i < sizeWineCuveesNonConfigurable;i++){
            hasCategoryWineVideoDAO.updateDisplayed(false,wineCuveesNonConfigurable.get(i).id_wine_cuvee);
            wineCuveeDAO.updateOrder(-1,wineCuveesNonConfigurable.get(i).id_wine_cuvee);
        }

        //Create elements and add them to the lines
        for(int i = 0 ; i < sizeWineCuveesConfigurable;i++){
            WineConfigTupleFragment wineConfigTupleFragment = new WineConfigTupleFragment(wineCuveesConfigurable.get(i).id_wine_cuvee);
            wineConfigTupleFragment.setConfigObserver(this);
            mWineConfigTupleFragments.add(wineConfigTupleFragment);
            fragmentManager.beginTransaction().add(mTableLayoutWineDisplayed.getId(), wineConfigTupleFragment, null).commit();
        }
    }

    public void productsModified() {
        mConfigObserver.onProductsModified();
    }

    @Override
    public void updateDatabase() {
        int nbFragments = mWineConfigTupleFragments.size();
        for(int i = 0 ; i < nbFragments; i++){
            mWineConfigTupleFragments.get(i).updateDatabase();
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
        int nbFragments = mWineConfigTupleFragments.size();
        for(int i = 0 ; i < nbFragments; i++){
            mWineConfigTupleFragments.get(i).onProductsModified();
        }
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