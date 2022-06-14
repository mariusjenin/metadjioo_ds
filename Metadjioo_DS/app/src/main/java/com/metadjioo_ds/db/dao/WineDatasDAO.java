package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.Video;
import com.metadjioo_ds.db.entity.WineDatas;

import java.util.List;

@Dao
public interface WineDatasDAO {
    @Query("SELECT * FROM WineDatas WHERE WineDatas.id_wine = :id_wine and WineDatas.country_code = :code LIMIT 1")
    WineDatas get(int id_wine, int code);

    @Insert(onConflict = ABORT)
    void insert(WineDatas wineDatas);

    @Insert(onConflict = ABORT)
    void insertAll(List<WineDatas> wineDatas);

    @Query("DELETE FROM WineDatas")
    void clear();
}
