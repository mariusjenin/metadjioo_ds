package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.IsCompanyVideo;

import java.util.List;

@Dao
public interface IsCompanyVideoDAO {
    @Query("SELECT * FROM IsCompanyVideo WHERE IsCompanyVideo.id_video = :id LIMIT 1")
    IsCompanyVideo get(int id);

    @Insert(onConflict = ABORT)
    void insert(IsCompanyVideo isCompanyVideo);

    @Insert(onConflict = ABORT)
    void insertAll(List<IsCompanyVideo> isCompanyVideos);

    @Query("DELETE FROM IsCompanyVideo")
    void clear();
}
