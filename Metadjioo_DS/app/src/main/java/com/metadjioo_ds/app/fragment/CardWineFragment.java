package com.metadjioo_ds.app.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.activity.used.second_screen.VideoDataSheetActivity;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.dao.LanguageDAO;
import com.metadjioo_ds.db.entity.Language;
import com.metadjioo_ds.db.entity.Wine;
import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.db.entity.WineDatas;
import com.metadjioo_ds.db.entity.WineVideo;
import com.metadjioo_ds.utils.ImgSaver;

public class CardWineFragment extends Fragment {
    protected VideoDataSheetActivity mActivitySecondScreen;
    private View mView;
    private int id_wine_cuvee;
    private int id_category;
    private boolean selected;
    private boolean displayVideo;
    private final AppDatabase mDatabase;

    public CardWineFragment(AppDatabase db, VideoDataSheetActivity act, int id_wc, int id_categ) {
        super(R.layout.experience_wine_card);
        mDatabase = db;
        mActivitySecondScreen = act;
        init(id_wc, id_categ);
    }

    public CardWineFragment(AppDatabase db, int id_wc, int id_categ) {
        super(R.layout.experience_wine_card);
        mDatabase = db;
        init(id_wc, id_categ);
    }

    private void init(int id_wc, int id_categ) {
        id_wine_cuvee = id_wc;
        id_category = id_categ;
        selected = false;
        displayVideo = true;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        setIDs(id_wine_cuvee, id_category);
        refreshDisplay();
    }

    public void setIDs(int id_wc, int id_categ) {
        id_wine_cuvee = id_wc;
        id_category = id_categ;
    }

    public void refreshDisplay() {
        if (mView != null) {
            //Entity
            WineCuvee wineCuvee = mDatabase.wineCuveeDAO().get(id_wine_cuvee);
            Wine wine = mDatabase.wineDAO().get(wineCuvee.id_wine);
            WineDatas wineDatas = mDatabase.wineDatasDAO().get(wineCuvee.id_wine);
            WineCuveeDatas wineCuveeDatas = mDatabase.wineCuveeDatasDAO().get(id_wine_cuvee);
            WineVideo video = mDatabase.wineVideoDAO().get(id_wine_cuvee, id_category);
            LanguageDAO languageDAO = mDatabase.languageDAO();

            //view
            ImageButton btnPlay = mView.findViewById(R.id.start_video_wine);
            ImageButton btnInfo = mView.findViewById(R.id.wine_datasheet);
            ImageView datasheetLanguageImgView = mView.findViewById(R.id.datasheet_language_img);
            ImageView wineVideoLanguageImgView = mView.findViewById(R.id.wine_video_language_img);
            ImageView wineImg = mView.findViewById(R.id.wine_img);

            //bitmap
            Bitmap bmpWineImg = new ImgSaver(MDSApp.getContext()).setDirectoryName(wineCuvee.img_directory).setFileName(wineCuvee.img_name).load();
            Language wineVideoLanguage = languageDAO.get(video.country_code);
            Language datasheetLanguage = languageDAO.get(wineCuveeDatas.country_code);
            Bitmap datasheetLanguageImg = new ImgSaver(MDSApp.getContext()).setDirectoryName(datasheetLanguage.img_directory).setFileName(datasheetLanguage.img_name).load();
            Bitmap wineVideoLanguageImg = new ImgSaver(MDSApp.getContext()).setDirectoryName(wineVideoLanguage.img_directory).setFileName(wineVideoLanguage.img_name).load();
            wineImg.setImageBitmap(bmpWineImg);
            datasheetLanguageImgView.setImageBitmap(datasheetLanguageImg);
            wineVideoLanguageImgView.setImageBitmap(wineVideoLanguageImg);
            btnPlay.setOnClickListener(view -> {
                displayVideo = true;
                if (mActivitySecondScreen != null) {
                    mActivitySecondScreen.setCardWineFragment(CardWineFragment.this);
                    mActivitySecondScreen.setVideo(video.path_video, false);
                }
            });
            btnInfo.setOnClickListener(view -> {
                displayVideo = false;
                if (mActivitySecondScreen != null) {
                    mActivitySecondScreen.setCardWineFragment(CardWineFragment.this);
                    mActivitySecondScreen.setDataSheet(wine, wineCuvee, wineDatas, wineCuveeDatas);
                }
            });
            TextView wine_title = mView.findViewById(R.id.wine_title);
            wine_title.setText(wineCuveeDatas.name);

            if (mActivitySecondScreen != null) {
                if (selected) {
                    if (displayVideo) {
                        mActivitySecondScreen.setVideo(video.path_video, false);
                    } else {
                        mActivitySecondScreen.setDataSheet(wine, wineCuvee, wineDatas, wineCuveeDatas);
                    }
                }
            }

        }
    }

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }
}
