package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.Wine;
import com.metadjioo_ds.db.entity.WineCuveeDatas;

import java.util.List;

@Dao
public interface WineDAO {
    @Query("SELECT * FROM Wine WHERE Wine.id_wine = :id LIMIT 1")
    Wine get(int id);

    @Query("SELECT * FROM Wine")
    List<Wine> getAll();

    @Insert(onConflict = ABORT)
    void insert(Wine wine);

    @Insert(onConflict = ABORT)
    List<Long> insertAll(List<Wine> wines);

    @Query("DELETE FROM Wine")
    void clear();
}
