package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.WineCuvee;

import java.util.List;

@Dao
public interface WineCuveeDAO {
    @Query("SELECT * FROM WineCuvee WHERE WineCuvee.id_wine_cuvee = :id LIMIT 1")
    WineCuvee get(int id);

    @Insert(onConflict = ABORT)
    void insert(WineCuvee wineCuvee);

    @Insert(onConflict = ABORT)
    List<Long> insertAll(List<WineCuvee> wineCuvees);

    @Query("DELETE FROM WineCuvee")
    void clear();
}