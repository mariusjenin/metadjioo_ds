package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Wine")
public class Wine implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_wine;

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

    public Wine(String name, String description, String story, String vineyard, String grape_variety) {
        this.name = name;
        this.description = description;
        this.story = story;
        this.vineyard = vineyard;
        this.grape_variety = grape_variety;
    }
}