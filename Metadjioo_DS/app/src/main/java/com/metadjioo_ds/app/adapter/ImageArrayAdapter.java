package com.metadjioo_ds.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.metadjioo_ds.R;
import com.metadjioo_ds.db.entity.Language;
import com.metadjioo_ds.utils.ImgSaver;

import java.util.List;

public class ImageArrayAdapter extends ArrayAdapter<Language> {
    private final List<Language> languages;
    private final LayoutInflater inflter;
    private final int layout;

    public ImageArrayAdapter(Context context, List<Language> languages, int l) {
        super(context, android.R.layout.simple_spinner_item, languages);
        this.languages = languages;
        inflter = (LayoutInflater.from(getContext()));
        layout = l;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getImageForPosition(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position);
    }

    @Override
    public int getPosition(@Nullable Language item) {
        for(Language l: languages){
            assert item != null;
            if(item.country_code.equals(l.country_code)){
                return super.getPosition(l);
            }
        }
        return -1;
    }

    private View getImageForPosition(int position) {
        Language l = languages.get(position);
        Bitmap bmp = new ImgSaver(getContext()).setDirectoryName(l.img_directory).setFileName(l.img_name).load();

        @SuppressLint("InflateParams") View view = inflter.inflate(layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.flag_img);
        imageView.setImageBitmap(bmp);
        return view;
    }


}