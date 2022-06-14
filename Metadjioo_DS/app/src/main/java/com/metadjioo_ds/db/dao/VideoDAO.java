package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.Video;

import java.util.List;

@Dao
public interface VideoDAO {
    @Query("SELECT * FROM Video WHERE Video.id_video = :id LIMIT 1")
    Video get(int id);

    @Insert(onConflict = ABORT)
    void insert(Video video);

    @Insert(onConflict = ABORT)
    List<Long> insertAll(List<Video> videos);

    @Query("DELETE FROM Video")
    void clear();
}
