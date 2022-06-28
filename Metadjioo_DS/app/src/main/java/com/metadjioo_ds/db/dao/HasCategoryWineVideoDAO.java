package com.metadjioo_ds.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.HasCategoryWineVideo;

import java.util.List;

@Dao
public interface HasCategoryWineVideoDAO {
    @Query("SELECT * FROM HasCategoryWineVideo WHERE HasCategoryWineVideo.id_wine_cuvee = :id_wine_cuvee and HasCategoryWineVideo.id_category_video = :id_category_video LIMIT 1")
    HasCategoryWineVideo get(int id_wine_cuvee, int id_category_video);

    @Query("SELECT * FROM HasCategoryWineVideo")
    List<HasCategoryWineVideo> getAll();

    @Query("SELECT HasCategoryWineVideo.* FROM HasCategoryWineVideo " +
            "INNER JOIN WineCuvee on HasCategoryWineVideo.id_wine_cuvee = WineCuvee.id_wine_cuvee " +
            "WHERE HasCategoryWineVideo.displayed = 1 and WineCuvee.order_display >= 0 order by WineCuvee.order_display ASC")
    List<HasCategoryWineVideo> getDisplayed();

    @Query("UPDATE HasCategoryWineVideo SET displayed = :isDisplayed WHERE HasCategoryWineVideo.id_wine_cuvee =:idWineCuvee;")
    void updateDisplayed(boolean isDisplayed, int idWineCuvee);

    @Query("UPDATE HasCategoryWineVideo SET displayed = :isDisplayed WHERE HasCategoryWineVideo.id_wine_cuvee =:idWineCuvee and HasCategoryWineVideo.id_category_video =:idCateg;")
    void updateDisplayed(boolean isDisplayed, int idWineCuvee, int idCateg);

    @Insert()
    void insert(HasCategoryWineVideo hasCategoryWineVideo);

    @Insert()
    void insertAll(List<HasCategoryWineVideo> hasCategoryWineVideos);

    @Query("DELETE FROM HasCategoryWineVideo")
    void clear();
}
