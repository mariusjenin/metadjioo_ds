package com.metadjioo_ds.app.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.ConfigObserver;
import com.metadjioo_ds.db.dao.CompanyVideoDAO;
import com.metadjioo_ds.db.entity.CompanyVideo;

public class CardCompanyVideoFragment extends ConfigObservableFragment implements ConfigObserver {
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
        init();

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    teaserModified();
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
        onTeaserModified();
    }

    @Override
    public void updateDatabase() {
        CompanyVideoDAO companyVideoDAO = db.companyVideoDAO();
        companyVideoDAO.updateDisplayed(true,mIdCompanyVideo);
    }

    @Override
    public void teaserModified() {
        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
        CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);
        companyVideoDAO.resetDisplayed(false);
        companyVideoDAO.updateDisplayed(true,companyVideo.id_company_video);
        mConfigObserver.onTeaserModified();
    }

    @Override
    public void onDefaultLanguageModified() {
        //nothing : not affected
    }

    @Override
    public void onTeaserModified() {
        CompanyVideoDAO companyVideoDAO = copyDB.companyVideoDAO();
        CompanyVideo companyVideo = companyVideoDAO.get(mIdCompanyVideo);

        radioButton.setText(companyVideo.title_video);
        radioButton.setChecked(companyVideo.displayed);
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
