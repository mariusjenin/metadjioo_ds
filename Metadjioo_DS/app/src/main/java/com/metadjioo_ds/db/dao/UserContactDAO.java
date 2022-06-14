package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.UserContact;

import java.util.List;

@Dao
public interface UserContactDAO {
    @Query("SELECT * FROM UserContact WHERE UserContact.id_user_contact = :id LIMIT 1")
    UserContact get(int id);

    @Insert(onConflict = ABORT)
    long insert(UserContact userContact);

    @Insert(onConflict = ABORT)
    void insertAll(List<UserContact> userContacts);

    @Query("DELETE FROM UserContact")
    void clear();
}
