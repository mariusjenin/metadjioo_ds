package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.CategoryWineVideo;
import com.metadjioo_ds.db.entity.Language;

import java.util.List;

@Dao
public interface CategoryWineVideoDAO {
    @Query("SELECT * FROM CategoryWineVideo WHERE CategoryWineVideo.id_category_video = :id LIMIT 1")
    CategoryWineVideo get(int id);

    @Query("SELECT * FROM CategoryWineVideo")
    List<CategoryWineVideo> getAll();

    @Insert(onConflict = ABORT)
    void insert(CategoryWineVideo categoryWineVideo);

    @Insert(onConflict = ABORT)
    List<Long> insertAll(List<CategoryWineVideo> categoriesWineVideo);

    @Query("DELETE FROM CategoryWineVideo")
    void clear();
}
