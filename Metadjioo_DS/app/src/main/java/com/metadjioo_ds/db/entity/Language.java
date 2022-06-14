package com.metadjioo_ds.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Language")
public class Language implements Serializable {
    @NonNull
    @PrimaryKey
    public String country_code;

    @ColumnInfo(name = "country_name")
    public String country_name;

    @ColumnInfo(name = "country_flag", typeAffinity = ColumnInfo.BLOB)
    public byte[] country_flag;

    public Language(@NonNull String country_code, String country_name, byte[] country_flag) {
        this.country_code = country_code;
        this.country_name = country_name;
        this.country_flag = country_flag;
    }
}