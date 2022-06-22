package com.metadjioo_ds.app.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.metadjioo_ds.R;
import com.metadjioo_ds.app.adapter.ImageArrayAdapter;
import com.metadjioo_ds.db.AppDatabase;
import com.metadjioo_ds.db.dao.LanguageDAO;
import com.metadjioo_ds.db.entity.Language;

public class ChoiceDefaultLanguageFragment extends ConfigFragment {

    private Spinner spinnerLanguage;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choice_default_language, container, false);
        spinnerLanguage = view.findViewById(R.id.spinner_language);
        refreshDisplayOnReload();
        LanguageDAO languageDAO = copyDB.languageDAO();

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Language language = (Language) spinnerLanguage.getSelectedItem();
                languageDAO.resetDefault(false);
                languageDAO.updateDefault(true,language.country_code);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });
        return view;
    }

    @Override
    public void updateDatabase() {
        LanguageDAO languageDAO = db.languageDAO();
        Language language = copyDB.languageDAO().getSelectedDefault();
        languageDAO.resetDefault(false);
        languageDAO.updateDefault(true,language.country_code);
    }

    @Override
    public void refreshDisplayOnDefaultLanguageModified() {
        //nothing : can't trigger this
    }

    @Override
    public void refreshDisplayOnTeaserModified() {
        //nothing : can't trigger this
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
        ImageArrayAdapter adapter = new ImageArrayAdapter(this.getContext(), AppDatabase.getInstance1(this.getContext()).languageDAO().getAllDefaultSettable(),R.layout.language_item_spinner_config);
        spinnerLanguage.setAdapter(adapter);

        LanguageDAO languageDAO = copyDB.languageDAO();
        Language defaultLanguage = languageDAO.getSelectedDefault();
        if(defaultLanguage != null){
            spinnerLanguage.setSelection(adapter.getPosition(defaultLanguage));
        }
    }
}