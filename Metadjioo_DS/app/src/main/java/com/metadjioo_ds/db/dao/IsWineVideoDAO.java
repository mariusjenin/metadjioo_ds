package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.IsWineVideo;

import java.util.List;

@Dao
public interface IsWineVideoDAO {
    @Query("SELECT * FROM IsWineVideo WHERE IsWineVideo.id_video = :id_video and IsWineVideo.id_wine_cuvee = :id_wine_cuvee LIMIT 1")
    IsWineVideo get(int id_video, int id_wine_cuvee);

    @Query("SELECT * FROM IsWineVideo WHERE IsWineVideo.displayed = 1")
    List<IsWineVideo> getDisplayed();

    @Insert(onConflict = ABORT)
    void insert(IsWineVideo isWineVideo);

    @Insert(onConflict = ABORT)
    void insertAll(List<IsWineVideo> isWineVideos);

    @Query("DELETE FROM IsWineVideo")
    void clear();
}
