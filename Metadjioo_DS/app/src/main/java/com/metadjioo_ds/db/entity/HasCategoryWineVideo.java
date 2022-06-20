package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.metadjioo_ds.db.dao.CategoryWineVideoDAO;

import java.io.Serializable;

@Entity(tableName = "HasCategoryWineVideo",
        foreignKeys = {
                @ForeignKey(entity = WineCuvee.class,
                        parentColumns = "id_wine_cuvee",
                        childColumns = "id_wine_cuvee"),
                @ForeignKey(entity = CategoryWineVideo.class,
                        parentColumns = "id_category_video",
                        childColumns = "id_category_video")},
        primaryKeys = {"id_wine_cuvee","id_category_video"})
public class HasCategoryWineVideo implements Serializable {

    @ColumnInfo(name = "id_wine_cuvee", index = true)
    public int id_wine_cuvee;

    @ColumnInfo(name = "id_category_video")
    public int id_category_video;

    @ColumnInfo(name = "displayed")
    public boolean displayed;

    public HasCategoryWineVideo(int id_wine_cuvee, int id_category_video, boolean displayed) {
        this.id_wine_cuvee = id_wine_cuvee;
        this.id_category_video = id_category_video;
        this.displayed = displayed;
    }
}