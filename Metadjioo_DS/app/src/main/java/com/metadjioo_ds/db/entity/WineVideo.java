package com.metadjioo_ds.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "WineVideo",
        foreignKeys = {
                @ForeignKey(entity = WineCuvee.class,
                        parentColumns = "id_wine_cuvee",
                        childColumns = "id_wine_cuvee"),
                @ForeignKey(entity = CategoryWineVideo.class,
                        parentColumns = "id_category_video",
                        childColumns = "id_category_video"),
                @ForeignKey(entity = Language.class,
                        parentColumns = "country_code",
                        childColumns = "country_code")},
        primaryKeys = {"id_wine_cuvee", "id_category_video", "country_code"})
public class WineVideo implements Serializable {
    @ColumnInfo(name = "id_wine_cuvee", index = true)
    public int id_wine_cuvee;

    @ColumnInfo(name = "id_category_video", index = true)
    public int id_category_video;

    @NonNull
    @ColumnInfo(name = "country_code", index = true)
    public String country_code;

    @ColumnInfo(name = "path_video")
    public String path_video;

    @ColumnInfo(name = "title_video")
    public String title_video;

    public WineVideo(int id_wine_cuvee, int id_category_video, @NonNull String country_code, String path_video, String title_video) {
        this.id_wine_cuvee = id_wine_cuvee;
        this.id_category_video = id_category_video;
        this.country_code = country_code;
        this.path_video = path_video;
        this.title_video = title_video;
    }
}