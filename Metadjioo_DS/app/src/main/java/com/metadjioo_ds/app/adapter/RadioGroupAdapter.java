package com.metadjioo_ds.app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.metadjioo_ds.R;
import com.metadjioo_ds.db.entity.CategoryWineVideo;

import java.util.List;

public class RadioGroupAdapter extends ArrayAdapter<CategoryWineVideo> {

    private final List<CategoryWineVideo> categoryWineVideos;
    private final LayoutInflater inflter;
    private final int layout;

    public RadioGroupAdapter(Context context, List<CategoryWineVideo> categoryWineVideos, int l) {
        super(context, R.layout.radio_button, categoryWineVideos);
        this.categoryWineVideos = categoryWineVideos;
        inflter = (LayoutInflater.from(getContext()));
        layout = l;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getName(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getName(position);
    }

    @Override
    public int getPosition(@Nullable CategoryWineVideo item) {
        for(CategoryWineVideo l: categoryWineVideos){
            assert item != null;
            if(item.id_category_video==l.id_category_video){
                return super.getPosition(l);
            }
        }
        return -1;
    }

    private View getName(int position) {
        CategoryWineVideo categoryWineVideo = categoryWineVideos.get(position);
        @SuppressLint("InflateParams") View view = inflter.inflate(layout, null);
        RadioButton radioButton = view.findViewById(R.id.radio_button);
        radioButton.setText(categoryWineVideo.name);
        return view;
    }
}