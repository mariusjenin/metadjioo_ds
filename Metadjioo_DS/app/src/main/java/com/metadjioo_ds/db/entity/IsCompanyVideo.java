package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "IsCompanyVideo",
        foreignKeys =
                @ForeignKey(entity = Video.class,
                        parentColumns = "id_video",
                        childColumns = "id_video"),
        primaryKeys = {"id_video"})
public class IsCompanyVideo implements Serializable {
    @ColumnInfo(name = "id_video",index = true)
    public int id_video;

    @ColumnInfo(name = "is_teaser")
    public boolean is_teaser;

    public IsCompanyVideo(int id_video, boolean is_teaser) {
        this.id_video = id_video;
        this.is_teaser = is_teaser;
    }
}