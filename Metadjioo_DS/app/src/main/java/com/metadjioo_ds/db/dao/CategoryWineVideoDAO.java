package com.metadjioo_ds.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;

import com.metadjioo_ds.db.entity.CategoryWineVideo;

import java.util.List;

@Dao
public interface CategoryWineVideoDAO {
    @Query("SELECT * FROM CategoryWineVideo WHERE CategoryWineVideo.id_category_video = :id LIMIT 1")
    CategoryWineVideo get(int id);

    @Query("SELECT * FROM CategoryWineVideo")
    List<CategoryWineVideo> getAll();


    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT CategoryWineVideo.* FROM CategoryWineVideo " +
            "inner join WineVideo on CategoryWineVideo.id_category_video = WineVideo.id_category_video " +
            "inner join Language on WineVideo.country_code = Language.country_code " +
            "WHERE Language.lang_default = 1 and WineVideo.id_wine_cuvee = :id_wine_cuvee")
    List<CategoryWineVideo> getWithWineCuvee(int id_wine_cuvee);

    @Insert
    void insert(CategoryWineVideo categoryWineVideo);

    @Insert
    List<Long> insertAll(List<CategoryWineVideo> categoriesWineVideo);

    @Query("DELETE FROM CategoryWineVideo")
    void clear();
}
