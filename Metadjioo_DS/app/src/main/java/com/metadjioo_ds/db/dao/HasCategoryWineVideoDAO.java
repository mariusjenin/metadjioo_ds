package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;

import com.metadjioo_ds.db.entity.CategoryWineVideo;
import com.metadjioo_ds.db.entity.CompanyVideo;
import com.metadjioo_ds.db.entity.HasCategoryWineVideo;

import java.util.List;

@Dao
public interface HasCategoryWineVideoDAO {
    @Query("SELECT * FROM HasCategoryWineVideo WHERE HasCategoryWineVideo.id_wine_cuvee = :id_wine_cuvee and HasCategoryWineVideo.id_category_video = :id_category_video LIMIT 1")
    HasCategoryWineVideo get(int id_wine_cuvee, int id_category_video);

    @Query("SELECT * FROM HasCategoryWineVideo")
    List<HasCategoryWineVideo> getAll();

    @Query("SELECT * FROM HasCategoryWineVideo WHERE HasCategoryWineVideo.displayed = 1")
    List<HasCategoryWineVideo> getDisplayed();

    @Query("UPDATE HasCategoryWineVideo SET displayed = :isDisplayed WHERE HasCategoryWineVideo.id_wine_cuvee =:idWineCuvee;")
    void updateDisplayed(boolean isDisplayed, int idWineCuvee);

    @Query("UPDATE HasCategoryWineVideo SET displayed = :isDisplayed WHERE HasCategoryWineVideo.id_wine_cuvee =:idWineCuvee and HasCategoryWineVideo.id_category_video =:idCateg;")
    void updateDisplayed(boolean isDisplayed, int idWineCuvee, int idCateg);

    @Insert(onConflict = ABORT)
    void insert(HasCategoryWineVideo hasCategoryWineVideo);

    @Insert(onConflict = ABORT)
    List<Long> insertAll(List<HasCategoryWineVideo> hasCategoryWineVideos);

    @Query("DELETE FROM HasCategoryWineVideo")
    void clear();
}
