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

    @ColumnInfo(name = "ph_rate")
    public float ph_rate;

    @ColumnInfo(name = "alcohol_level")
    public float alcohol_level;

    @ColumnInfo(name = "acidity_rate")
    public float acidity_rate;

    public WineCuvee(int id_wine, float ph_rate, float alcohol_level, float acidity_rate) {
        this.id_wine = id_wine;
        this.ph_rate = ph_rate;
        this.alcohol_level = alcohol_level;
        this.acidity_rate = acidity_rate;
    }
}