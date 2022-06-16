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
    //    @Query("SELECT * From (SELECT * , 0 sort_order FROM WineDatas " +
//            "WHERE WineDatas.id_wine = :id_wine and WineDatas.country_code = :code " +
//            "UNION " +
//            "SELECT * , 0 sort_order FROM WineDatas " +
//            "WHERE WineDatas.id_wine = :id_wine and WineDatas.country_code = :default_code) " +
//            "LIMIT 1")
    @Query("SELECT * From WineDatas " +
            "INNER JOIN Language on WineDatas.country_code = Language.country_code " +
            "WHERE WineDatas.id_wine = :id_wine and (Language.lang_selected = 1 or Language.lang_default = 1) " +
            "ORDER BY Language.lang_selected DESC, Language.lang_default DESC LIMIT 1")
    WineDatas get(int id_wine);

    @Insert(onConflict = ABORT)
    void insert(WineDatas wineDatas);

    @Insert(onConflict = ABORT)
    void insertAll(List<WineDatas> wineDatas);

    @Query("DELETE FROM WineDatas")
    void clear();
}
