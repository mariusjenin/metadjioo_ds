package com.metadjioo_ds.app.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.dao.CategoryWineVideoDAO;
import com.metadjioo_ds.db.dao.HasCategoryWineVideoDAO;
import com.metadjioo_ds.db.dao.LanguageDAO;
import com.metadjioo_ds.db.dao.WineCuveeDAO;
import com.metadjioo_ds.db.dao.WineCuveeDatasDAO;
import com.metadjioo_ds.db.entity.CategoryWineVideo;
import com.metadjioo_ds.db.entity.Language;
import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.utils.ImgSaver;

import java.util.ArrayList;
import java.util.List;

public class WineConfigTupleFragment extends ConfigIndirectFragment implements ConfigObserver {
    private final int mIdWineCuvee;
    private CategoryWineVideo mCategoryWineVideo;
    private View mView;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch mSwitchDisplayed;

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

        init();
    }

    @Override
    protected void init() {
        mSwitchDisplayed = mView.findViewById(R.id.wine_displayed);
        ImageView imgWine = mView.findViewById(R.id.wine_img);
        TextView titleWine = mView.findViewById(R.id.wine_title);
        LinearLayout linearLayoutCategories = mView.findViewById(R.id.radio_group_type_video);
        LinearLayout linearLayoutCountryFlags = mView.findViewById(R.id.language_list);
        List<RadioButton> radioButtons = new ArrayList<>();

        //DAO
        CategoryWineVideoDAO categoryWineVideoDAO = copyDB.categoryWineVideoDAO();
        HasCategoryWineVideoDAO hasCategoryWineVideoDAO = copyDB.hasCategoryWineVideoDAO();
        WineCuveeDAO wineCuveeDAO = copyDB.wineCuveeDAO();
        WineCuveeDatasDAO wineCuveeDatasDAO = copyDB.wineCuveeDatasDAO();
        LanguageDAO languageDAO = copyDB.languageDAO();

        //Entity
        WineCuvee wineCuvee = wineCuveeDAO.get(mIdWineCuvee);
        WineCuveeDatas wineCuveeDatas = wineCuveeDatasDAO.get(mIdWineCuvee);

        //Switch
        boolean displayed = wineCuveeDAO.get(mIdWineCuvee).order_display >= 0;
        mSwitchDisplayed.setChecked(displayed);
        mSwitchDisplayed.setOnClickListener(view -> productsModified());

        //bitmap
        Bitmap bm = new ImgSaver(MDSApp.getContext()).setDirectoryName(wineCuvee.img_directory).setFileName(wineCuvee.img_name).load();
        imgWine.setImageBitmap(bm);

        //title
        titleWine.setText(wineCuveeDatas.name);

        //Categories
        List<CategoryWineVideo> categoryWineVideos = categoryWineVideoDAO.getWithWineCuvee(mIdWineCuvee);

        linearLayoutCategories.removeAllViews();
        linearLayoutCategories.removeAllViewsInLayout();

        int sizeCategories = categoryWineVideos.size();

        int indexSelected = 0;
        for (int i = 0; i < sizeCategories; i++) {
            if (hasCategoryWineVideoDAO.get(mIdWineCuvee, categoryWineVideos.get(i).id_category_video).displayed) {
                indexSelected = i;
                break;
            }
        }
        mCategoryWineVideo = categoryWineVideos.get(indexSelected);

        for (int i = 0; i < sizeCategories; i++) {
            CategoryWineVideo categoryWineVideo = categoryWineVideos.get(i);
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setEnabled(displayed);
            radioButton.setText(categoryWineVideo.name);
            radioButton.setChecked(i == indexSelected);
            radioButton.setOnCheckedChangeListener((compoundButton, b) -> {
                if (b) {
                    mCategoryWineVideo = categoryWineVideo;
                    productsModified();
                }
            });
            radioButtons.add(radioButton);
        }

        int sizeRadioButtons = radioButtons.size();
        for (int i = 0; i < sizeRadioButtons; i++) {
            linearLayoutCategories.addView(radioButtons.get(i));
        }

        //Flags
        linearLayoutCountryFlags.removeAllViews();
        List<Language> languages = languageDAO.getFromWineCuvee(mIdWineCuvee);
        int sizeLanguages = languages.size();
        for (int i = 0; i < sizeLanguages; i++) {
            Language language = languages.get(i);
            View child = getLayoutInflater().inflate(R.layout.language_list_item, linearLayoutCountryFlags,false);
            linearLayoutCountryFlags.addView(child);
            TextView textView = child.findViewById(R.id.country_code);
            textView.setText(language.country_code);
            Bitmap bmp = new ImgSaver(getContext()).setDirectoryName(language.img_directory).setFileName(language.img_name).load();
            ImageView imageView = child.findViewById(R.id.country_flag);
            imageView.setImageBitmap(bmp);
        }
    }

    private void updateDatabaseAux(AppDatabase database){
        HasCategoryWineVideoDAO hasCategoryWineVideoDAO = database.hasCategoryWineVideoDAO();
        WineCuveeDAO wineCuveeDAO = database.wineCuveeDAO();
        wineCuveeDAO.updateOrder(mSwitchDisplayed.isChecked()? Math.max(wineCuveeDAO.get(mIdWineCuvee).order_display, 0) :-1,mIdWineCuvee);
        hasCategoryWineVideoDAO.updateDisplayed(false, mIdWineCuvee);
        hasCategoryWineVideoDAO.updateDisplayed(true, mIdWineCuvee, mCategoryWineVideo.id_category_video);
    }

    @Override
    public void updateDatabase() {
        updateDatabaseAux(db);
        mConfigObserver.onProductsModified();
    }

    public void productsModified() {
        updateDatabaseAux(copyDB);
        mConfigObserver.productsModified();
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
       init();
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
