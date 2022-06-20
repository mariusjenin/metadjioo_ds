package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.WineVideo;

import java.util.List;

@Dao
public interface WineVideoDAO {
    @Query("SELECT * FROM WineVideo " +
            "INNER JOIN Language on WineVideo.country_code = Language.country_code " +
            "WHERE WineVideo.id_wine_cuvee = :id_wine_cuvee and " +
            "WineVideo.id_category_video = :id_category and " +
            "(Language.lang_selected = 1 or Language.lang_default = 1) " +
            "ORDER BY Language.lang_selected DESC LIMIT 1")
    WineVideo get(int id_wine_cuvee, int id_category);

    @Query("SELECT WineVideo.* FROM WineVideo " +
            "inner join HasCategoryWineVideo on HasCategoryWineVideo.id_category_video = WineVideo.id_category_video and HasCategoryWineVideo.id_wine_cuvee = WineVideo.id_wine_cuvee " +
            "WHERE HasCategoryWineVideo.displayed = 1")
    List<WineVideo> getDisplayed();

    @Insert(onConflict = ABORT)
    void insert(WineVideo wineVideo);

    @Insert(onConflict = ABORT)
    void insertAll(List<WineVideo> wineVideos);

    @Query("DELETE FROM WineVideo")
    void clear();
}
