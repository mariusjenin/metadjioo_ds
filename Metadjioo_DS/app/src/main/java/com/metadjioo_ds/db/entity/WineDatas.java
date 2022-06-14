package com.metadjioo_ds.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "WineDatas",
        foreignKeys = {
                @ForeignKey(entity = Wine.class,
                        parentColumns = "id_wine",
                        childColumns = "id_wine"),
                @ForeignKey(entity = Language.class,
                        parentColumns = "country_code",
                        childColumns = "country_code")},
        primaryKeys = {"id_wine", "country_code"})
public class WineDatas implements Serializable {
    @ColumnInfo(name = "id_wine", index = true)
    public int id_wine;

    @NonNull
    @ColumnInfo(name = "country_code", index = true)
    public String country_code;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "story")
    public String story;

    @ColumnInfo(name = "vineyard")
    public String vineyard;

    @ColumnInfo(name = "grape_variety")
    public String grape_variety;

    public WineDatas(int id_wine, String country_code, String name, String description, String story, String vineyard, String grape_variety) {
        this.id_wine = id_wine;
        this.country_code = country_code;
        this.name = name;
        this.description = description;
        this.story = story;
        this.vineyard = vineyard;
        this.grape_variety = grape_variety;
    }
}