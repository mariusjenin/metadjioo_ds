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
    public int id_wine;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "tasting_details")
    public String tasting_details;

    @ColumnInfo(name = "food_pairings")
    public String food_pairings;

    @ColumnInfo(name = "ph_rate")
    public float ph_rate;

    @ColumnInfo(name = "alcohol_level")
    public float alcohol_level;

    @ColumnInfo(name = "acidity_rate")
    public float acidity_rate;

    @ColumnInfo(name = "is_displayed")
    public boolean is_displayed;

    @ColumnInfo(name = "url_video")
    public String url_video;

    public WineCuvee(int id_wine, String name, String description, String tasting_details, String food_pairings, float ph_rate, float alcohol_level, float acidity_rate,boolean is_displayed, String url_video) {
        this.id_wine = id_wine;
        this.name = name;
        this.description = description;
        this.tasting_details = tasting_details;
        this.food_pairings = food_pairings;
        this.ph_rate = ph_rate;
        this.alcohol_level = alcohol_level;
        this.acidity_rate = acidity_rate;
        this.is_displayed = is_displayed;
        this.url_video = url_video;
    }
}