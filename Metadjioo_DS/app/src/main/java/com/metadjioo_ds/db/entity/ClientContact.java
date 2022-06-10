package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "ClientContact")
public class ClientContact implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_user_contact;

    @ColumnInfo(name = "email")
    public String email;

    public ClientContact(String email) {
        this.email = email;
    }
}