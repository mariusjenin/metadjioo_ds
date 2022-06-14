package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Wine")
public class Wine implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_wine;

    public Wine() {}
}