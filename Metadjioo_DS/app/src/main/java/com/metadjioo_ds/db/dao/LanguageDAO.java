package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.Language;
import com.metadjioo_ds.db.entity.Video;

import java.util.List;

@Dao
public interface LanguageDAO {
    @Query("SELECT * FROM Language WHERE Language.country_code = :code LIMIT 1")
    Language get(int code);

    @Insert(onConflict = ABORT)
    void insert(Language language);

    @Insert(onConflict = ABORT)
    List<Long>  insertAll(List<Language> languages);

    @Query("DELETE FROM Language")
    void clear();
}
