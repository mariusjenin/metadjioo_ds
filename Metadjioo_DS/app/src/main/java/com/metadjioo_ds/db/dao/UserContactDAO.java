package com.metadjioo_ds.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.UserContact;

import java.util.List;

@Dao
public interface UserContactDAO {
    @Query("SELECT * FROM UserContact WHERE UserContact.id_user_contact = :id LIMIT 1")
    UserContact get(int id);

    @Query("SELECT * FROM UserContact")
    List<UserContact> getAll();

    @Insert()
    long insert(UserContact userContact);

    @Insert()
    void insertAll(List<UserContact> userContacts);

    @Query("DELETE FROM UserContact")
    void clear();
}
