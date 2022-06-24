package com.metadjioo_ds.app.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.db.dao.CategoryWineVideoDAO;
import com.metadjioo_ds.db.dao.CompanyVideoDAO;
import com.metadjioo_ds.db.dao.HasCategoryWineVideoDAO;
import com.metadjioo_ds.db.dao.WineCuveeDAO;
import com.metadjioo_ds.db.dao.WineCuveeDatasDAO;
import com.metadjioo_ds.db.entity.CategoryWineVideo;
import com.metadjioo_ds.db.entity.CompanyVideo;
import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.db.entity.WineDatas;
import com.metadjioo_ds.utils.ImgSaver;

import java.util.ArrayList;
import java.util.List;

public class WineConfigTupleFragment extends ConfigObservableFragment implements ConfigObserver {
    private final int mIdWineCuvee;
    private View mView;
    private ImageView mImgWIne;
    private TextView mTitleWine;
    private Switch mSwitchDisplayed;
    private LinearLayout mLinearLayoutCategories;

    public WineConfigTupleFragment(int idWineCuvee) {
        mIdWineCuvee = idWineCuvee;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.wine_config_tuple, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwitchDisplayed = mView.findViewById(R.id.wine_displayed);
        mImgWIne = mView.findViewById(R.id.wine_img);
        mTitleWine = mView.findViewById(R.id.wine_title);
        mLinearLayoutCategories = mView.findViewById(R.id.radio_group_type_video);
        init();

        mSwitchDisplayed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    onProductsModified();
                }
            }
        });
    }

    @Override
    protected void init() {
        //Entity
        WineCuveeDAO wineCuveeDAO = copyDB.wineCuveeDAO();
        WineCuveeDatasDAO wineCuveeDatasDAO = copyDB.wineCuveeDatasDAO();
        WineCuvee wineCuvee = wineCuveeDAO.get(mIdWineCuvee);
        WineCuveeDatas wineCuveeDatas = wineCuveeDatasDAO.get(mIdWineCuvee);
        CategoryWineVideoDAO categoryWineVideoDAO = copyDB.categoryWineVideoDAO();
        List<CategoryWineVideo> categoryWineVideos = categoryWineVideoDAO.getWithWineCuvee(mIdWineCuvee);

        HasCategoryWineVideoDAO hasCategoryWineVideoDAO = copyDB.hasCategoryWineVideoDAO();

        mLinearLayoutCategories.removeAllViews();
        int sizeCategories = categoryWineVideos.size();

        int idSelected = 0;
        for (int i = 0; i < sizeCategories; i++) {
            if(hasCategoryWineVideoDAO.get(mIdWineCuvee,categoryWineVideos.get(i).id_category_video).displayed){
                idSelected = i;
                break;
            }
        }

        List<RadioButton> radioButtons = new ArrayList<>();
        for (int i = 0; i < sizeCategories; i++) {
            CategoryWineVideo categoryWineVideo = categoryWineVideos.get(i);
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(categoryWineVideo.name);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        hasCategoryWineVideoDAO.updateDisplayed(false,mIdWineCuvee);
                        hasCategoryWineVideoDAO.updateDisplayed(true,mIdWineCuvee,categoryWineVideo.id_category_video);
                        mConfigObserver.onTeaserModified();
                    }
                }
            });
            radioButtons.add(radioButton);
        }

        radioButtons.get(idSelected).setChecked(true);

        int sizeRadioButtons = radioButtons.size();
        for (int i = 0; i < sizeRadioButtons; i++) {
            mLinearLayoutCategories.addView(radioButtons.get(i));
        }

        //bitmap
        Bitmap bm = new ImgSaver(MDSApp.getContext()).setDirectoryName(wineCuvee.img_directory).setFileName(wineCuvee.img_name).load();
        mImgWIne.setImageBitmap(bm);
        mTitleWine.setText(wineCuveeDatas.name);

        onTeaserModified();
    }

    @Override
    public void updateDatabase() {
        HasCategoryWineVideoDAO hasCategoryWineVideoDAO = db.hasCategoryWineVideoDAO();
        //TODO
        // hasCategoryWineVideoDAO.updateDisplayed(true, mIdWineCuvee, mIdCateg);
    }

    @Override
    public void productsModified() {
//        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
//        CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);
//        companyVideoDAO.resetDisplayed(false);
//        companyVideoDAO.updateDisplayed(true,companyVideo.id_company_video);
//        mConfigObserver.onTeaserModified();
    }

    @Override
    public void onDefaultLanguageModified() {
        //nothing : not affected
    }

    @Override
    public void onTeaserModified() {
        //nothing : not affected
    }

    @Override
    public void onProductsModified() {
//        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
//        CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);
//
//        radioButton.setText(companyVideo.title_video);
//        radioButton.setChecked(companyVideo.displayed);
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
