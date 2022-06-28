package com.metadjioo_ds.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.WineDatas;

import java.util.List;

@Dao
public interface WineDatasDAO {
    @Query("SELECT WineDatas.* From WineDatas " +
            "INNER JOIN Language on WineDatas.country_code = Language.country_code " +
            "WHERE WineDatas.id_wine = :id_wine and (Language.lang_selected = 1 or Language.lang_default = 1) " +
            "ORDER BY Language.lang_selected DESC LIMIT 1")
    WineDatas get(int id_wine);

    @Query("SELECT * FROM WineDatas")
    List<WineDatas> getAll();

    @Insert()
    void insert(WineDatas wineDatas);

    @Insert()
    void insertAll(List<WineDatas> wineDatas);

    @Query("DELETE FROM WineDatas")
    void clear();
}
