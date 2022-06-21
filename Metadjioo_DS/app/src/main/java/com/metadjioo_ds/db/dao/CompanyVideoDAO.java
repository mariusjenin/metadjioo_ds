package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.CompanyVideo;

import java.util.List;

@Dao
public interface CompanyVideoDAO {
    @Query("SELECT CompanyVideo.* FROM CompanyVideo " +
            "inner join Language on Language.country_code= CompanyVideo.country_code " +
            "WHERE CompanyVideo.is_teaser = :is_teaser and " +
            "(Language.lang_selected = 1 or Language.lang_default = 1) " +
            "ORDER BY Language.lang_selected DESC LIMIT 1")
    CompanyVideo getDisplayed(boolean is_teaser);

    @Insert(onConflict = ABORT)
    void insert(CompanyVideo companyVideo);

    @Insert(onConflict = ABORT)
    void insertAll(List<CompanyVideo> companyVideoList);

    @Query("DELETE FROM CompanyVideo")
    void clear();
}
