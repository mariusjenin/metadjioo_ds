package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.db.entity.WineDatas;

import java.util.List;

@Dao
public interface WineCuveeDatasDAO {
@Query("SELECT WineCuveeDatas.* From WineCuveeDatas " +
        "INNER JOIN Language on WineCuveeDatas.country_code = Language.country_code " +
        "WHERE WineCuveeDatas.id_wine_cuvee = :id_wine_cuvee and (Language.lang_selected = 1 or Language.lang_default = 1) " +
        "ORDER BY Language.lang_selected DESC LIMIT 1")
    WineCuveeDatas get(int id_wine_cuvee);

    @Query("SELECT * FROM WineCuveeDatas")
    List<WineCuveeDatas> getAll();

    @Insert(onConflict = ABORT)
    void insert(WineCuveeDatas wineCuveeDatas);

    @Insert(onConflict = ABORT)
    void insertAll(List<WineCuveeDatas> wineCuveeDatas);

    @Query("DELETE FROM WineCuveeDatas")
    void clear();
}
