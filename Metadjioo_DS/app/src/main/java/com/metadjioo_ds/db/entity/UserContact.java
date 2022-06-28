package com.metadjioo_ds.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "UserContact")
public class UserContact implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_user_contact;

    @ColumnInfo(name = "email")
    public final String email;

    public UserContact(String email) {
        this.email = email;
    }
}