package com.metadjioo_ds.app.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.metadjioo_ds.MDSApp;
import com.metadjioo_ds.R;
import com.metadjioo_ds.app.presentation.VideoDataSheetPresentation;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.dao.CompanyVideoDAO;
import com.metadjioo_ds.db.entity.CompanyVideo;
import com.metadjioo_ds.db.entity.Language;
import com.metadjioo_ds.db.entity.Wine;
import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.db.entity.WineDatas;
import com.metadjioo_ds.db.entity.WineVideo;
import com.metadjioo_ds.utils.ImgSaver;

import java.util.Objects;

public class CardCompanyVideoFragment extends ConfigFragment {
    private final int mIdCompanyVideo;
    private View mView;
    private ImageView imgTeaser;
    private RadioButton radioButton;

    public CardCompanyVideoFragment(int idCompanyVideo) {
        mIdCompanyVideo = idCompanyVideo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.card_company_video, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioButton = mView.findViewById(R.id.radio_button);
        imgTeaser = mView.findViewById(R.id.img_company_video);
        refreshDisplayOnReload();

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
                    CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);
                    companyVideoDAO.resetDisplayed(false);
                    companyVideoDAO.updateDisplayed(true,companyVideo.id_company_video);
                    mConfigObserver.onTeaserModified();
                }
            }
        });
    }

    @Override
    public void updateDatabase() {
        CompanyVideoDAO companyVideoDAO = db.companyVideoDAO();
        companyVideoDAO.updateDisplayed(true,mIdCompanyVideo);
    }

    @Override
    public void refreshDisplayOnDefaultLanguageModified() {
        //nothing : can't trigger this
    }

    @Override
    public void refreshDisplayOnTeaserModified() {
        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
        CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);

        radioButton.setText(companyVideo.title_video);
        radioButton.setChecked(companyVideo.displayed);
    }

    @Override
    public void refreshDisplayOnProductsModified() {
        //nothing : can't trigger this
    }

    @Override
    public void refreshDisplayOnOrderProductsModified() {
        //nothing : can't trigger this
    }

    @Override
    public void refreshDisplayOnLanguagesModified() {
        //nothing : can't trigger this
    }

    @Override
    public void refreshDisplayOnReload() {
        //Entity
        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
        CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);

        //bitmap
        //TODO use a video dowloaded locally to create the thumbnail
//        Bitmap bm = ThumbnailUtils.createVideoThumbnail(companyVideo.path_video, MediaStore.Video.Thumbnails.MICRO_KIND);
        Bitmap bm = BitmapFactory.decodeResource(requireContext().getResources(), R.drawable.black);

        imgTeaser.setImageBitmap(bm);
        refreshDisplayOnTeaserModified();
    }
}
