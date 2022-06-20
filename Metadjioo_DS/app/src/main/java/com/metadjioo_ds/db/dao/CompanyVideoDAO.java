package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.CompanyVideo;

import java.util.List;

@Dao
public interface CompanyVideoDAO {
    @Query("SELECT * FROM CompanyVideo WHERE CompanyVideo.id_company_video = :id LIMIT 1")
    CompanyVideo get(int id);

    @Insert(onConflict = ABORT)
    void insert(CompanyVideo companyVideo);

    @Insert(onConflict = ABORT)
    void insertAll(List<CompanyVideo> companyVideoList);

    @Query("DELETE FROM CompanyVideo")
    void clear();
}
