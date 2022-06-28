package com.metadjioo_ds.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.metadjioo_ds.R;
import com.metadjioo_ds.db.dao.CategoryWineVideoDAO;
import com.metadjioo_ds.db.dao.HasCategoryWineVideoDAO;
import com.metadjioo_ds.db.dao.CompanyVideoDAO;
import com.metadjioo_ds.db.dao.WineVideoDAO;
import com.metadjioo_ds.db.dao.LanguageDAO;
import com.metadjioo_ds.db.dao.UserContactDAO;
import com.metadjioo_ds.db.dao.WineCuveeDAO;
import com.metadjioo_ds.db.dao.WineCuveeDatasDAO;
import com.metadjioo_ds.db.dao.WineDAO;
import com.metadjioo_ds.db.dao.WineDatasDAO;
import com.metadjioo_ds.db.entity.CategoryWineVideo;
import com.metadjioo_ds.db.entity.CompanyVideo;
import com.metadjioo_ds.db.entity.HasCategoryWineVideo;
import com.metadjioo_ds.db.entity.WineVideo;
import com.metadjioo_ds.db.entity.Language;
import com.metadjioo_ds.db.entity.UserContact;
import com.metadjioo_ds.db.entity.Wine;
import com.metadjioo_ds.db.entity.WineCuvee;
import com.metadjioo_ds.db.entity.WineCuveeDatas;
import com.metadjioo_ds.db.entity.WineDatas;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class that manage the local database in the whole app
 */
@Database(entities = {
        CategoryWineVideo.class,
        HasCategoryWineVideo.class,
        CompanyVideo.class,
        WineVideo.class,
        Language.class,
        UserContact.class,
        Wine.class,
        WineCuvee.class,
        WineCuveeDatas.class,
        WineDatas.class,
}, version = 5, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    @SuppressLint("StaticFieldLeak")
    private static AppDatabase INSTANCE1 = null;
    @SuppressLint("StaticFieldLeak")
    private static AppDatabase INSTANCE2 = null;

    public abstract HasCategoryWineVideoDAO hasCategoryWineVideoDAO();

    public abstract CategoryWineVideoDAO categoryWineVideoDAO();

    public abstract CompanyVideoDAO companyVideoDAO();

    public abstract WineVideoDAO wineVideoDAO();

    public abstract LanguageDAO languageDAO();

    public abstract UserContactDAO userContactDAO();

    public abstract WineDAO wineDAO();

    public abstract WineCuveeDAO wineCuveeDAO();

    public abstract WineCuveeDatasDAO wineCuveeDatasDAO();

    public abstract WineDatasDAO wineDatasDAO();

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static AppDatabase getInstance1(Context applicationContext) {
        if (INSTANCE1 == null) {
            context = applicationContext;
            INSTANCE1 = Room.databaseBuilder(applicationContext, AppDatabase.class, "db1").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE1;
    }
    public static AppDatabase getInstance2(Context applicationContext) {
        if (INSTANCE2 == null) {
            context = applicationContext;
            INSTANCE2 = Room.databaseBuilder(applicationContext, AppDatabase.class, "db2").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE2;
    }

    public static boolean isInstance1Filled(){
        return !INSTANCE1.languageDAO().getAll().isEmpty();
    }

    public static boolean isInstance2Filled(){
        return !INSTANCE2.languageDAO().getAll().isEmpty();
    }

    public static void copyDB1ToDB2(){
        INSTANCE2.languageDAO().insertAll(INSTANCE1.languageDAO().getAll());
        INSTANCE2.wineDAO().insertAll(INSTANCE1.wineDAO().getAll());
        INSTANCE2.wineCuveeDAO().insertAll(INSTANCE1.wineCuveeDAO().getAll());
        INSTANCE2.wineDatasDAO().insertAll(INSTANCE1.wineDatasDAO().getAll());
        INSTANCE2.wineCuveeDatasDAO().insertAll(INSTANCE1.wineCuveeDatasDAO().getAll());
        INSTANCE2.categoryWineVideoDAO().insertAll(INSTANCE1.categoryWineVideoDAO().getAll());
        INSTANCE2.wineVideoDAO().insertAll(INSTANCE1.wineVideoDAO().getAll());
        INSTANCE2.companyVideoDAO().insertAll(INSTANCE1.companyVideoDAO().getAll());
        INSTANCE2.hasCategoryWineVideoDAO().insertAll(INSTANCE1.hasCategoryWineVideoDAO().getAll());
    }

    public void clear() {
        hasCategoryWineVideoDAO().clear();
        companyVideoDAO().clear();
        wineVideoDAO().clear();
        categoryWineVideoDAO().clear();
        wineCuveeDatasDAO().clear();
        wineDatasDAO().clear();
        wineCuveeDAO().clear();
        wineDAO().clear();
        languageDAO().clear();
    }

    public void fill() {
        //TODO Remplace this whole function by a call to a distant database

        //LANGUAGE
        LanguageDAO languageDAO = languageDAO();
        ArrayList<Language> languages = new ArrayList<>();
        Bitmap fr = BitmapFactory.decodeResource(context.getResources(), R.drawable.icons8_france_96);
        Bitmap en = BitmapFactory.decodeResource(context.getResources(), R.drawable.icons8_great_britain_96);
        Bitmap cn = BitmapFactory.decodeResource(context.getResources(), R.drawable.icons8_china_96);
        Bitmap kr = BitmapFactory.decodeResource(context.getResources(), R.drawable.icons8_south_korea_96);
        languages.add(languageDAO.createLanguage(context, "FR", "France", false, false, true, "language", "fr", fr));
        languages.add(languageDAO.createLanguage(context, "CN", "China", false, false, true, "language", "cn", cn));
        languages.add(languageDAO.createLanguage(context, "KR", "Korea", false, true, true, "language", "kr", kr));
        languages.add(languageDAO.createLanguage(context, "EN", "England", true, false, true, "language", "en", en));
        languageDAO.insertAll(languages);

        //WINE
        WineDAO wineDAO = wineDAO();
        ArrayList<Wine> wines = new ArrayList<>();
        int size_wines = 4;
        for (int i = 0; i < size_wines; i++) {
            wines.add(new Wine());
        }
        List<Long> id_wines = wineDAO.insertAll(wines);

        //WINE CUVEE
        WineCuveeDAO wineCuveeDAO = wineCuveeDAO();
        ArrayList<WineCuvee> wineCuvees = new ArrayList<>();
        Bitmap wineBottle1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wine_bottle_1);
        Bitmap wineBottle2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wine_bottle_2);
        Bitmap wineBottle3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wine_bottle_3);
        Bitmap wineBottle4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.wine_bottle_4);
        wineCuvees.add(wineCuveeDAO.createWineCuvee(context, id_wines.get(0).intValue(), 2.5f, 10.4f, 3.3f, "companyName", "cuvee1", wineBottle1,0));
        wineCuvees.add(wineCuveeDAO.createWineCuvee(context, id_wines.get(1).intValue(), 3f, 9.6f, 3.41f, "companyName", "cuvee2", wineBottle2,1));
        wineCuvees.add(wineCuveeDAO.createWineCuvee(context, id_wines.get(2).intValue(), 3.5f, 13.2f, 2.9f, "companyName", "cuvee3", wineBottle3,2));
        wineCuvees.add(wineCuveeDAO.createWineCuvee(context, id_wines.get(3).intValue(), 4f, 14f, 4.1f, "companyName", "cuvee4", wineBottle4,3));
        List<Long> id_wine_cuvees = wineCuveeDAO.insertAll(wineCuvees);

        //WINE DATAS
        WineDatasDAO wineDatasDAO = wineDatasDAO();
        ArrayList<WineDatas> wineDatas = new ArrayList<>();
        wineDatas.add(new WineDatas(id_wines.get(0).intValue(), "FR", "Wine 1 FR", "Description Wine 1 FR", "Story Wine 1 FR", "Vineyard Wine 1 FR", "Grape variety Wine 1 FR"));
        wineDatas.add(new WineDatas(id_wines.get(0).intValue(), "CN", "Wine 1 CN", "Description Wine 1 CN", "Story Wine 1 CN", "Vineyard Wine 1 CN", "Grape variety Wine 1 FR"));
        wineDatas.add(new WineDatas(id_wines.get(0).intValue(), "EN", "Wine 1 EN", "Description Wine 1 EN", "Story Wine 1 EN", "Vineyard Wine 1 EN", "Grape variety Wine 1 EN"));
        wineDatas.add(new WineDatas(id_wines.get(1).intValue(), "CN", "Wine 2 CN", "Description Wine 2 CN", "Story Wine 2 CN", "Vineyard Wine 2 CN", "Grape variety Wine 2 CN"));
        wineDatas.add(new WineDatas(id_wines.get(1).intValue(), "KR", "Wine 2 KR", "Description Wine 2 KR", "Story Wine 2 KR", "Vineyard Wine 2 KR", "Grape variety Wine 2 KR"));
        wineDatas.add(new WineDatas(id_wines.get(1).intValue(), "EN", "Wine 2 EN", "Description Wine 2 EN", "Story Wine 2 EN", "Vineyard Wine 2 EN", "Grape variety Wine 2 EN"));
        wineDatas.add(new WineDatas(id_wines.get(2).intValue(), "CN", "Wine 3 CN", "Description Wine 3 CN", "Story Wine 3 CN", "Vineyard Wine 3 CN", "Grape variety Wine 3 CN"));
        wineDatas.add(new WineDatas(id_wines.get(2).intValue(), "EN", "Wine 3 EN", "Description Wine 3 EN", "Story Wine 3 EN", "Vineyard Wine 3 EN", "Grape variety Wine 3 EN"));
        wineDatas.add(new WineDatas(id_wines.get(3).intValue(), "FR", "Wine 4 FR", "Description Wine 4 FR", "Story Wine 4 FR", "Vineyard Wine 4 FR", "Grape variety Wine 4 FR"));
        wineDatas.add(new WineDatas(id_wines.get(3).intValue(), "EN", "Wine 4 EN", "Description Wine 4 EN", "Story Wine 4 EN", "Vineyard Wine 4 EN", "Grape variety Wine 1 EN"));
        wineDatasDAO.insertAll(wineDatas);

        //WINE CUVEE DATAS
        WineCuveeDatasDAO wineCuveeDatasDAO = wineCuveeDatasDAO();
        ArrayList<WineCuveeDatas> wineCuveeDatas = new ArrayList<>();
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(0).intValue(), "FR", "Wine 1 Cuvee 1 FR", "Description Wine 1 Cuvee 1 FR", "Tasting details Wine 1 Cuvee 1 FR", "food pairings Wine 1 Cuvee 1 FR"));
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(0).intValue(), "CN", "Wine 1 Cuvee 1 CN", "Description Wine 1 Cuvee 1 CN", "Tasting details Wine 1 Cuvee 1 CN", "food pairings Wine 1 Cuvee 1 CN"));
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(0).intValue(), "EN", "Wine 1 Cuvee 1 EN", "Description Wine 1 Cuvee 1 EN", "Tasting details Wine 1 Cuvee 1 EN", "food pairings Wine 1 Cuvee 1 EN"));
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(1).intValue(), "CN", "Wine 2 Cuvee 1 CN", "Description Wine 2 Cuvee 1 CN", "Tasting details Wine 2 Cuvee 1 CN", "food pairings Wine 2 Cuvee 1 CN"));
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(1).intValue(), "KR", "Wine 2 Cuvee 1 KR", "Description Wine 2 Cuvee 1 KR", "Tasting details Wine 2 Cuvee 1 KR", "food pairings Wine 2 Cuvee 1 KR"));
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(1).intValue(), "EN", "Wine 2 Cuvee 1 EN", "Description Wine 2 Cuvee 1 EN", "Tasting details Wine 2 Cuvee 1 EN", "food pairings Wine 2 Cuvee 1 EN"));
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(2).intValue(), "CN", "Wine 3 Cuvee 1 CN", "Description Wine 3 Cuvee 1 CN", "Tasting details Wine 3 Cuvee 1 CN", "food pairings Wine 3 Cuvee 1 CN"));
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(2).intValue(), "EN", "Wine 3 Cuvee 1 EN", "Description Wine 3 Cuvee 1 EN", "Tasting details Wine 3 Cuvee 1 EN", "food pairings Wine 3 Cuvee 1 EN"));
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(3).intValue(), "FR", "Wine 4 Cuvee 1 FR", "Description Wine 4 Cuvee 1 FR", "Tasting details Wine 4 Cuvee 1 FR", "food pairings Wine 4 Cuvee 1 FR"));
        wineCuveeDatas.add(new WineCuveeDatas(id_wine_cuvees.get(3).intValue(), "EN", "Wine 4 Cuvee 1 EN", "Description Wine 4 Cuvee 1 EN", "Tasting details Wine 4 Cuvee 1 EN", "food pairings Wine 4 Cuvee 1 EN"));
        wineCuveeDatasDAO.insertAll(wineCuveeDatas);

        //CATEGORY WINE VIDEO
        CategoryWineVideoDAO categoryWineVideoDAO = categoryWineVideoDAO();
        ArrayList<CategoryWineVideo> categoriesWineVideo = new ArrayList<>();
        categoriesWineVideo.add(new CategoryWineVideo("Event pro 1"));
        categoriesWineVideo.add(new CategoryWineVideo("Event pro 2"));
        categoriesWineVideo.add(new CategoryWineVideo("Event public 1"));
        categoriesWineVideo.add(new CategoryWineVideo("Event public 2"));
        List<Long> id_categories_video = categoryWineVideoDAO.insertAll(categoriesWineVideo);

        //WINE VIDEO
        WineVideoDAO wineVideoDAO = wineVideoDAO();
        ArrayList<WineVideo> wineVideos = new ArrayList<>();
        wineVideos.add(new WineVideo(id_wine_cuvees.get(0).intValue(), id_categories_video.get(1).intValue(),"FR", "path/video/wine/1/cuvee/1/categ/1/fr", "Video Wine 1 Cuvee 1 Categ 1 fr"));
        wineVideos.add(new WineVideo( id_wine_cuvees.get(0).intValue(), id_categories_video.get(1).intValue(),"CN", "path/video/wine/1/cuvee/1/categ/1/cn", "Video Wine 1 Cuvee 1 Categ 1 cn"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(0).intValue(), id_categories_video.get(1).intValue(),"EN", "path/video/wine/1/cuvee/1/categ/1/en", "Video Wine 1 Cuvee 1 Categ 1 en"));
        wineVideos.add(new WineVideo( id_wine_cuvees.get(1).intValue(), id_categories_video.get(1).intValue(),"CN", "path/video/wine/2/cuvee/1/categ/1/cn", "Video Wine 2 Cuvee 1 Categ 1 cn"));
        wineVideos.add(new WineVideo( id_wine_cuvees.get(1).intValue(), id_categories_video.get(1).intValue(),"KR", "path/video/wine/2/cuvee/1/categ/1/kr", "Video Wine 2 Cuvee 1 Categ 1 kr"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(1).intValue(), id_categories_video.get(1).intValue(),"EN", "path/video/wine/2/cuvee/1/categ/1/en", "Video Wine 2 Cuvee 1 Categ 1 en"));
        wineVideos.add(new WineVideo( id_wine_cuvees.get(2).intValue(), id_categories_video.get(1).intValue(),"CN", "path/video/wine/3/cuvee/1/categ/1/cn", "Video Wine 3 Cuvee 1 Categ 1 cn"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(2).intValue(), id_categories_video.get(1).intValue(),"EN", "path/video/wine/3/cuvee/1/categ/1/en", "Video Wine 3 Cuvee 1 Categ 1 en"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(3).intValue(), id_categories_video.get(1).intValue(),"FR", "path/video/wine/4/cuvee/1/categ/1/fr", "Video Wine 4 Cuvee 1 Categ 1 fr"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(3).intValue(), id_categories_video.get(1).intValue(),"EN", "path/video/wine/4/cuvee/1/categ/1/en", "Video Wine 4 Cuvee 1 Categ 1 en"));

        wineVideos.add(new WineVideo(id_wine_cuvees.get(0).intValue(), id_categories_video.get(2).intValue(),"FR", "path/video/wine/1/cuvee/1/categ/2/fr", "Video Wine 1 Cuvee 1 Categ 2 fr"));
        wineVideos.add(new WineVideo( id_wine_cuvees.get(0).intValue(), id_categories_video.get(2).intValue(),"CN", "path/video/wine/1/cuvee/1/categ/2/cn", "Video Wine 1 Cuvee 1 Categ 2 cn"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(0).intValue(), id_categories_video.get(2).intValue(),"EN", "path/video/wine/1/cuvee/1/categ/2/en", "Video Wine 1 Cuvee 1 Categ 2 en"));
        wineVideos.add(new WineVideo( id_wine_cuvees.get(1).intValue(), id_categories_video.get(2).intValue(),"CN", "path/video/wine/2/cuvee/1/categ/2/cn", "Video Wine 2 Cuvee 1 Categ 2 cn"));
        wineVideos.add(new WineVideo( id_wine_cuvees.get(1).intValue(), id_categories_video.get(2).intValue(),"KR", "path/video/wine/2/cuvee/1/categ/2/kr", "Video Wine 2 Cuvee 1 Categ 2 kr"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(1).intValue(), id_categories_video.get(2).intValue(),"EN", "path/video/wine/2/cuvee/1/categ/2/en", "Video Wine 2 Cuvee 1 Categ 2 en"));
        wineVideos.add(new WineVideo( id_wine_cuvees.get(2).intValue(), id_categories_video.get(2).intValue(),"CN", "path/video/wine/3/cuvee/1/categ/2/cn", "Video Wine 3 Cuvee 1 Categ 2 cn"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(2).intValue(), id_categories_video.get(2).intValue(),"EN", "path/video/wine/3/cuvee/1/categ/2/en", "Video Wine 3 Cuvee 1 Categ 2 en"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(3).intValue(), id_categories_video.get(2).intValue(),"FR", "path/video/wine/4/cuvee/1/categ/2/fr", "Video Wine 4 Cuvee 1 Categ 2 fr"));
        wineVideos.add(new WineVideo(id_wine_cuvees.get(3).intValue(), id_categories_video.get(2).intValue(),"EN", "path/video/wine/4/cuvee/1/categ/2/en", "Video Wine 4 Cuvee 1 Categ 2 en"));
        wineVideoDAO.insertAll(wineVideos);


        //COMPANY VIDEO
        CompanyVideoDAO companyVideoDAO = companyVideoDAO();
        ArrayList<CompanyVideo> companyVideos = new ArrayList<>();
        companyVideos.add(new CompanyVideo("EN", "path/video/teaser/1/en", "Video teaser 1 en", true, false));
        companyVideos.add(new CompanyVideo("CN", "path/video/teaser/1/cn", "Video teaser 1 cn", true, true));
        companyVideos.add(new CompanyVideo("EN", "path/video/teaser/2/en", "Video teaser 2 en", true, false));
        companyVideos.add(new CompanyVideo("CN", "path/video/teaser/2/cn", "Video teaser 2 cn", true, false));
        companyVideos.add(new CompanyVideo("EN", "path/video/global/3/en", "Video global 3 en", false, false));
        companyVideos.add(new CompanyVideo("CN", "path/video/global/3/cn", "Video global 3 cn", false, false));
        companyVideos.add(new CompanyVideo("EN", "path/video/global/4/en", "Video global 4 en", false, true));
        companyVideos.add(new CompanyVideo("CN", "path/video/global/4/cn", "Video global 4 cn", false, false));
        companyVideoDAO.insertAll(companyVideos);

        //HAS CATEGORY WINE VIDEO
        HasCategoryWineVideoDAO hasCategoryWineVideoDAO = hasCategoryWineVideoDAO();
        ArrayList<HasCategoryWineVideo> hasCategoryWineVideos = new ArrayList<>();
        hasCategoryWineVideos.add(new HasCategoryWineVideo(id_wine_cuvees.get(0).intValue(), id_categories_video.get(1).intValue(), true));
        hasCategoryWineVideos.add(new HasCategoryWineVideo(id_wine_cuvees.get(0).intValue(), id_categories_video.get(2).intValue(), false));
        hasCategoryWineVideos.add(new HasCategoryWineVideo(id_wine_cuvees.get(1).intValue(), id_categories_video.get(1).intValue(), false));
        hasCategoryWineVideos.add(new HasCategoryWineVideo(id_wine_cuvees.get(1).intValue(), id_categories_video.get(2).intValue(), true));
        hasCategoryWineVideos.add(new HasCategoryWineVideo(id_wine_cuvees.get(2).intValue(), id_categories_video.get(1).intValue(), false));
        hasCategoryWineVideos.add(new HasCategoryWineVideo(id_wine_cuvees.get(2).intValue(), id_categories_video.get(2).intValue(), true));
        hasCategoryWineVideos.add(new HasCategoryWineVideo(id_wine_cuvees.get(3).intValue(), id_categories_video.get(1).intValue(), true));
        hasCategoryWineVideos.add(new HasCategoryWineVideo(id_wine_cuvees.get(3).intValue(), id_categories_video.get(2).intValue(), false));
        hasCategoryWineVideoDAO.insertAll(hasCategoryWineVideos);

    }


}
