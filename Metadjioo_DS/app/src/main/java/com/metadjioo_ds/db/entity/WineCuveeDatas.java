package com.metadjioo_ds.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.io.Serializable;

@Entity(tableName = "WineCuveeDatas",
        foreignKeys = {
                @ForeignKey(entity = WineCuvee.class,
                        parentColumns = "id_wine_cuvee",
                        childColumns = "id_wine_cuvee"),
                @ForeignKey(entity = Language.class,
                        parentColumns = "country_code",
                        childColumns = "country_code")},
        primaryKeys = {"id_wine_cuvee", "country_code"})
public class WineCuveeDatas implements Serializable {
    @ColumnInfo(name = "id_wine_cuvee", index = true)
    public final int id_wine_cuvee;

    @NonNull
    @ColumnInfo(name = "country_code", index = true)
    public final String country_code;

    @ColumnInfo(name = "name")
    public final String name;

    @ColumnInfo(name = "description")
    public final String description;

    @ColumnInfo(name = "tasting_details")
    public final String tasting_details;

    @ColumnInfo(name = "food_pairings")
    public final String food_pairings;

    public WineCuveeDatas(int id_wine_cuvee, @NonNull String country_code, String name, String description, String tasting_details, String food_pairings) {
        this.id_wine_cuvee = id_wine_cuvee;
        this.country_code = country_code;
        this.name = name;
        this.description = description;
        this.tasting_details = tasting_details;
        this.food_pairings = food_pairings;
    }
}