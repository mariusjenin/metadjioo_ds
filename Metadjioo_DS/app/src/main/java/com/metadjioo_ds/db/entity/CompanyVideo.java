package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "CompanyVideo",
        foreignKeys =
                @ForeignKey(entity = Language.class,
                        parentColumns = "country_code",
                        childColumns = "country_code"))
public class CompanyVideo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_company_video;

    @ColumnInfo(name = "country_code", index = true)
    public final String country_code;

    @ColumnInfo(name = "path_video")
    public final String path_video;

    @ColumnInfo(name = "title_video")
    public final String title_video;

    @ColumnInfo(name = "is_teaser")
    public final boolean is_teaser;

    @ColumnInfo(name = "displayed")
    public final boolean displayed;

    public CompanyVideo(String country_code,String path_video,String title_video, boolean is_teaser, boolean displayed) {
        this.country_code = country_code;
        this.path_video = path_video;
        this.title_video = title_video;
        this.is_teaser = is_teaser;
        this.displayed = displayed;
    }
}