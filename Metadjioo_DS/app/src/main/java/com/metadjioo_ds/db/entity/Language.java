package com.metadjioo_ds.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Language")
public class Language implements Serializable {
    @NonNull
    @PrimaryKey
    public final String country_code;

    @ColumnInfo(name = "country_name")
    public final String country_name;

    @ColumnInfo(name = "lang_default")
    public final boolean lang_default;

    @ColumnInfo(name = "lang_selected")
    public final boolean lang_selected;

    @ColumnInfo(name = "lang_displayed")
    public final boolean lang_displayed;

    @ColumnInfo(name = "img_directory")
    public final String img_directory;

    @ColumnInfo(name = "img_name")
    public final String img_name;

    public Language(@NonNull String country_code, String country_name, boolean lang_default, boolean lang_selected, boolean lang_displayed, String img_directory, String img_name) {
        this.country_code = country_code;
        this.country_name = country_name;
        this.lang_default = lang_default;
        this.lang_selected = lang_selected;
        this.lang_displayed = lang_displayed;
        this.img_directory = img_directory;
        this.img_name = img_name;
    }
}