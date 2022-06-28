package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "CategoryWineVideo")
public class CategoryWineVideo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_category_video;

    @ColumnInfo(name = "name")
    public final String name;

    public CategoryWineVideo(String name) {
        this.name = name;
    }
}