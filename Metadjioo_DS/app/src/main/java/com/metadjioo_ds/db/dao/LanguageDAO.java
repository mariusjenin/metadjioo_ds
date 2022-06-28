package com.metadjioo_ds.db.dao;

import static androidx.room.OnConflictStrategy.ABORT;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Transaction;

import com.metadjioo_ds.db.entity.HasCategoryWineVideo;
import com.metadjioo_ds.db.entity.Language;
import com.metadjioo_ds.utils.ImgSaver;

import java.util.List;

@Dao
public interface LanguageDAO {
    @Query("SELECT * FROM Language WHERE Language.country_code = :code LIMIT 1")
    Language get(String code);

    @Query("SELECT * FROM Language")
    List<Language> getAll();

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT Language.* , count(*) as n_lang FROM Language " +
            "inner join WineCuveeDatas on Language.country_code = WineCuveeDatas.country_code " +
            "inner join WineVideo on Language.country_code = WineVideo.country_code and WineCuveeDatas.country_code = WineVideo.country_code " +
            "WHERE Language.lang_displayed = 1 " +
            "group by Language.country_code order by n_lang DESC")
    List<Language> getAllDisplayed();

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT Language.* , count(*) as n_lang FROM Language " +
            "inner join WineCuveeDatas on Language.country_code = WineCuveeDatas.country_code " +
            "inner join WineVideo on Language.country_code = WineVideo.country_code and WineCuveeDatas.country_code = WineVideo.country_code " +
            "group by Language.country_code order by n_lang DESC")
    List<Language> getAllDefaultSettable();

    @Query("SELECT * FROM Language WHERE Language.lang_displayed = 1 and (Language.lang_selected = 1 or Language.lang_default = 1) order by lang_default DESC LIMIT 1")
    Language getSelectedDefault();

    @Query("SELECT Distinct Language.* FROM Language " +
            "inner join WineCuveeDatas on Language.country_code = WineCuveeDatas.country_code " +
            "inner join WineVideo on Language.country_code = WineVideo.country_code and WineCuveeDatas.country_code = WineVideo.country_code " +
            "where WineCuveeDatas.id_wine_cuvee = :id_wine_cuvee and WineVideo.id_wine_cuvee = :id_wine_cuvee")
    List<Language> getFromWineCuvee(int id_wine_cuvee);

    @Query("UPDATE Language SET lang_selected = :is_selected")
    void resetSelected(boolean is_selected);

    @Query("UPDATE Language SET lang_default = :is_default")
    void resetDefault(boolean is_default);

    @Query("UPDATE Language SET lang_selected = :is_displayed")
    void resetDisplayed(boolean is_displayed);

    @Transaction
    default Language createLanguage(Context context, String country_code, String country_name, boolean lang_default, boolean lang_selected, boolean lang_displayed, String img_directory, String img_name, Bitmap bmp) {
        Language language = new Language(country_code, country_name, lang_default, lang_selected, lang_displayed, img_directory, img_name);
        new ImgSaver(context).setDirectoryName(img_directory).setFileName(img_name).save(bmp);
        return language;
    }

    @Query("UPDATE Language SET lang_selected = :is_selected WHERE Language.country_code =:code;")
    void updateSelected(boolean is_selected, String code);

    @Query("UPDATE Language SET lang_default = :is_default WHERE Language.country_code =:code")
    void updateDefault(boolean is_default, String code);

    @Query("UPDATE Language SET lang_displayed = :is_displayed WHERE Language.country_code =:code")
    void updateDisplayed(boolean is_displayed, String code);

    @Insert(onConflict = ABORT)
    void insert(Language language);

    @Insert(onConflict = ABORT)
    void insertAll(List<Language> languages);

    @Query("DELETE FROM Language")
    void clear();
}
