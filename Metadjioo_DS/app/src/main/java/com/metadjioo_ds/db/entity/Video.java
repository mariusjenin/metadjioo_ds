package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Video",
        foreignKeys = @ForeignKey(entity = Language.class,
                parentColumns = "country_code",
                childColumns = "country_code"))
public class Video implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_video;

    @ColumnInfo(name = "country_code", index = true)
    public String country_code;

    @ColumnInfo(name = "path_video")
    public String path_video;

    @ColumnInfo(name = "title_video")
    public String title_video;

    public Video(String country_code, String path_video, String title_video) {
        this.country_code = country_code;
        this.path_video = path_video;
        this.title_video = title_video;
    }
}