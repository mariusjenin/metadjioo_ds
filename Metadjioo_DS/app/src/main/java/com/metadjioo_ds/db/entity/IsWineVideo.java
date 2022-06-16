package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "IsWineVideo",
        foreignKeys = {
                @ForeignKey(entity = WineCuvee.class,
                        parentColumns = "id_wine_cuvee",
                        childColumns = "id_wine_cuvee"),
                @ForeignKey(entity = Video.class,
                        parentColumns = "id_video",
                        childColumns = "id_video")},
        primaryKeys = {"id_video", "id_wine_cuvee"})
public class IsWineVideo implements Serializable {
    @ColumnInfo(name = "id_video",index = true)
    public int id_video;

    @ColumnInfo(name = "id_wine_cuvee", index = true)
    public int id_wine_cuvee;

    @ColumnInfo(name = "displayed")
    public boolean displayed;

    public IsWineVideo(int id_video, int id_wine_cuvee, boolean displayed) {
        this.id_video = id_video;
        this.id_wine_cuvee = id_wine_cuvee;
        this.displayed = displayed;
    }
}