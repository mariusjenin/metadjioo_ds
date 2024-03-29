package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "WineCuvee",
        foreignKeys = @ForeignKey(entity = Wine.class,
                parentColumns = "id_wine",
                childColumns = "id_wine"))
public class WineCuvee implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_wine_cuvee;

    @ColumnInfo(name = "id_wine", index = true)
    public final int id_wine;

    @ColumnInfo(name = "ph_rate")
    public final float ph_rate;

    @ColumnInfo(name = "alcohol_level")
    public final float alcohol_level;

    @ColumnInfo(name = "acidity_rate")
    public final float acidity_rate;

    @ColumnInfo(name = "img_directory")
    public final String img_directory;

    @ColumnInfo(name = "img_name")
    public final String img_name;

    @ColumnInfo(name = "order_display")
    public final int order_display;

    public WineCuvee(int id_wine, float ph_rate, float alcohol_level, float acidity_rate, String img_directory, String img_name,int order_display) {
        this.id_wine = id_wine;
        this.ph_rate = ph_rate;
        this.alcohol_level = alcohol_level;
        this.acidity_rate = acidity_rate;
        this.img_directory = img_directory;
        this.img_name = img_name;
        this.order_display = order_display;
    }
}