package com.metadjioo_ds.db.dao;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.utils.ImgSaver;

import java.util.List;

@Dao
public interface WineCuveeDAO {
    @Query("SELECT * FROM WineCuvee WHERE WineCuvee.id_wine_cuvee = :id LIMIT 1")
    WineCuvee get(int id);

    @Query("SELECT * FROM WineCuvee")
    List<WineCuvee> getAll();


    @Query("SELECT WineCuvee.* FROM WineCuvee WHERE WineCuvee.order_display >= 0 order by WineCuvee.order_display ASC")
    List<WineCuvee> getDisplayed();

    @Query("SELECT WineCuvee.* FROM WineCuvee " +
            "inner join WineCuveeDatas on WineCuvee.id_wine_cuvee = WineCuveeDatas.id_wine_cuvee " +
            "inner join WineVideo on WineCuvee.id_wine_cuvee = WineVideo.id_wine_cuvee " +
            "inner join WineDatas on WineCuvee.id_wine = WineDatas.id_wine " +
            "inner join Language on WineCuveeDatas.country_code = Language.country_code and " +
            "WineVideo.country_code = Language.country_code and " +
            "WineDatas.country_code = Language.country_code and Language.lang_default = 1 " +
            "group by WineCuvee.id_wine_cuvee order by WineCuveeDatas.name ASC")
    List<WineCuvee> getAllConfigurable();

    @Query("SELECT WineCuvee.* FROM WineCuvee  where WineCuvee.id_wine_cuvee not in (Select WineCuvee.id_wine_cuvee FROM WineCuvee " +
            "inner join WineCuveeDatas on WineCuvee.id_wine_cuvee = WineCuveeDatas.id_wine_cuvee " +
            "inner join WineVideo on WineCuvee.id_wine_cuvee = WineVideo.id_wine_cuvee " +
            "inner join WineDatas on WineCuvee.id_wine = WineDatas.id_wine " +
            "inner join Language on WineCuveeDatas.country_code = Language.country_code and " +
            "WineVideo.country_code = Language.country_code and " +
            "WineDatas.country_code = Language.country_code and Language.lang_default = 1 " +
            "group by WineCuvee.id_wine_cuvee order by WineCuveeDatas.name ASC)")
    List<WineCuvee> getAllNonConfigurable();


    @Query("UPDATE WineCuvee SET order_display = :order;")
    void resetOrder(int order);

    @Query("UPDATE WineCuvee SET order_display = :order WHERE WineCuvee.id_wine_cuvee =:idWineCuvee;")
    void updateOrder(int order, int idWineCuvee);

    @Transaction
    default WineCuvee createWineCuvee(Context context, int id_wine, float ph_rate, float alcohol_level, float acidity_rate, String img_directory, String img_name, Bitmap bmp, int order_display){
        WineCuvee wc = new WineCuvee(id_wine, ph_rate, alcohol_level, acidity_rate, img_directory, img_name,order_display);
        new ImgSaver(context).setDirectoryName(img_directory).setFileName(img_name).save(bmp);
        return wc;
    }

    @Insert()
    void insert(WineCuvee wineCuvee);

    @Insert()
    List<Long> insertAll(List<WineCuvee> wineCuvees);

    @Query("DELETE FROM WineCuvee")
    void clear();
}