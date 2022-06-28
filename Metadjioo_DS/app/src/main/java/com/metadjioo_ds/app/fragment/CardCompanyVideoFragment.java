package com.metadjioo_ds.app.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.db.dao.CompanyVideoDAO;
import com.metadjioo_ds.db.entity.CompanyVideo;

public class CardCompanyVideoFragment extends ConfigIndirectFragment implements ConfigObserver {
    private final int mIdCompanyVideo;
    private View mView;
    private ImageView imgTeaser;
    private RadioButton radioButton;
    private final boolean mIsTeaser;

    public CardCompanyVideoFragment(int idCompanyVideo, boolean isTeaser) {
        mIdCompanyVideo = idCompanyVideo;
        mIsTeaser = isTeaser;
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
        init();

        radioButton.setOnCheckedChangeListener((compoundButton, checked) -> {
            if(checked){
                if(mIsTeaser){
                    teaserModified();
                }else{
                    additionnalVideoModified();
                }
            }
        });
    }

    @Override
    protected void init() {
        //Entity
        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
        CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);

        //bitmap
        //TODO use a video dowloaded locally to create the thumbnail
//        Bitmap bm = ThumbnailUtils.createVideoThumbnail(companyVideo.path_video, MediaStore.Video.Thumbnails.MICRO_KIND);
        Bitmap bm = BitmapFactory.decodeResource(requireContext().getResources(), R.drawable.black);

        imgTeaser.setImageBitmap(bm);
        onCompanyVideoModified();
    }

    @Override
    public void updateDatabase() {
        CompanyVideoDAO companyVideoDAO = db.companyVideoDAO();
        companyVideoDAO.updateDisplayed(radioButton.isChecked(),mIdCompanyVideo);
    }

    public void companyVideoModified(){
        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
        CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);
        companyVideoDAO.resetDisplayed(false,mIsTeaser);
        companyVideoDAO.updateDisplayed(true,companyVideo.id_company_video);
        if(mIsTeaser){
            mConfigObserver.teaserModified();
        }else{
            mConfigObserver.additionnalVideoModified();
        }
    }

    @Override
    public void teaserModified() {
        companyVideoModified();
    }

    @Override
    public void additionnalVideoModified() {
        companyVideoModified();
    }


    @Override
    public void onDefaultLanguageModified() {
        //nothing : not affected
    }

    @Override
    public void onTeaserModified() {
        onCompanyVideoModified();
    }

    @Override
    public void onProductsModified() {
        //nothing : not affected
    }

    @Override
    public void onOrderProductsModified() {
        //nothing : not affected
    }

    @Override
    public void onAdditionnalVideoModified() {
        onCompanyVideoModified();
    }

    public void onCompanyVideoModified() {
        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
        CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);

        radioButton.setText(companyVideo.title_video);
        radioButton.setChecked(companyVideo.displayed);
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
