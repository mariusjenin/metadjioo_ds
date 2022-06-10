package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.ClientContact;

import java.util.List;

@Dao
public interface ClientContactDAO {
    @Query("SELECT * FROM ClientContact WHERE ClientContact.id_user_contact = :id LIMIT 1")
    ClientContact get(int id);

    @Insert(onConflict = ABORT)
    long insert(ClientContact userContact);

    @Insert(onConflict = ABORT)
    void insertAll(List<ClientContact> userContacts);

    @Query("DELETE FROM ClientContact")
    void clear();
}
