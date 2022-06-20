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
//    @Query("SELECT * From (SELECT WineCuveeDatas.* , 0 sort_order FROM WineCuveeDatas " +
//            "WHERE WineCuveeDatas.id_wine_cuvee = :id_wine_cuvee and WineCuveeDatas.country_code = :code " +
//            "UNION " +
//            "SELECT WineCuveeDatas.* , 0 sort_order FROM WineCuveeDatas " +
//            "INNER JOIN Language on WineCuveeDatas.country_code = Language.country_code " +
//            "WHERE WineCuveeDatas.id_wine_cuvee = :id_wine_cuvee and Language.lang_selected = 1) " +
//            "LIMIT 1")
@Query("SELECT WineCuveeDatas.* From WineCuveeDatas " +
        "INNER JOIN Language on WineCuveeDatas.country_code = Language.country_code " +
        "WHERE WineCuveeDatas.id_wine_cuvee = :id_wine_cuvee and (Language.lang_selected = 1 or Language.lang_default = 1) " +
        "ORDER BY Language.lang_selected DESC LIMIT 1")
    WineCuveeDatas get(int id_wine_cuvee);

    @Insert(onConflict = ABORT)
    void insert(WineCuveeDatas wineCuveeDatas);

    @Insert(onConflict = ABORT)
    void insertAll(List<WineCuveeDatas> wineCuveeDatas);

    @Query("DELETE FROM WineCuveeDatas")
    void clear();
}
