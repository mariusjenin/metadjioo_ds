package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.db.entity.WineDatas;

import java.util.List;

@Dao
public interface WineCuveeDatasDAO {
    @Query("SELECT * FROM WineCuveeDatas WHERE WineCuveeDatas.id_wine_cuvee = :id_wine_cuvee and  WineCuveeDatas.country_code = :code LIMIT 1")
    WineCuveeDatas get(int id_wine_cuvee, int code);

    @Insert(onConflict = ABORT)
    void insert(WineCuveeDatas wineCuveeDatas);

    @Insert(onConflict = ABORT)
    void insertAll(List<WineCuveeDatas> wineCuveeDatas);

    @Query("DELETE FROM WineCuveeDatas")
    void clear();
}
