package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.metadjioo_ds.db.entity.CategoryWineVideo;
import com.metadjioo_ds.db.entity.CompanyVideo;
import com.metadjioo_ds.db.entity.UserContact;

import java.util.List;

@Dao
public interface CompanyVideoDAO {
    @Query("SELECT * FROM CompanyVideo WHERE CompanyVideo.id_company_video = :id LIMIT 1")
    CompanyVideo get(int id);

    @Query("SELECT CompanyVideo.* FROM CompanyVideo " +
            "inner join Language on Language.country_code= CompanyVideo.country_code " +
            "WHERE CompanyVideo.is_teaser = :is_teaser and " +
            "(Language.lang_selected = 1 or Language.lang_default = 1) " +
            "ORDER BY Language.lang_selected DESC LIMIT 1")
    CompanyVideo getDisplayed(boolean is_teaser);

    @Query("UPDATE CompanyVideo SET displayed = :displayed")
    void resetDisplayed(boolean displayed);

    @Query("UPDATE CompanyVideo SET displayed = :displayed WHERE CompanyVideo.id_company_video =:id;")
    void updateDisplayed(boolean displayed, int id);

    @Query("SELECT * FROM CompanyVideo WHERE CompanyVideo.is_teaser = 1")
    List<CompanyVideo> getTeasers();

    @Query("SELECT * FROM CompanyVideo WHERE CompanyVideo.is_teaser = 0")
    List<CompanyVideo> getAdditionalVideos();

    @Query("SELECT * FROM CompanyVideo")
    List<CompanyVideo> getAll();

    @Insert(onConflict = ABORT)
    void insert(CompanyVideo companyVideo);

    @Insert(onConflict = ABORT)
    void insertAll(List<CompanyVideo> companyVideoList);

    @Query("DELETE FROM CompanyVideo")
    void clear();
}
