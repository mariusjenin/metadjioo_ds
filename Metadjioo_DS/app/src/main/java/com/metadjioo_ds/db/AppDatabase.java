package com.metadjioo_ds.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.metadjioo_ds.db.dao.ClientContactDAO;
import com.metadjioo_ds.db.dao.WineCuveeDAO;
import com.metadjioo_ds.db.dao.WineDAO;
import com.metadjioo_ds.db.entity.ClientContact;
import com.metadjioo_ds.db.entity.Wine;
import com.metadjioo_ds.db.entity.WineCuvee;

/**
 * Singleton class that manage the local database in the whole app
 */
@Database(entities = {
        Wine.class,
        WineCuvee.class,
        ClientContact.class,
},version = 0, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE = null;
    public abstract WineDAO wineDAO();
    public abstract WineCuveeDAO wineCuveeDAO();
    public abstract ClientContactDAO clientContactDAO();

    public static AppDatabase getInstance(Context applicationContext) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(applicationContext,AppDatabase.class,"db").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    public void clear(){
        wineDAO().clear();
        wineCuveeDAO().clear();
    }

//    public void fillUsers(){
//        UserDAO userDAO = userDAO();
//        ArrayList<User> users = new ArrayList<>();
//
//        //Merchant
//        users.add(new User("store@zara.fr", HashUtil.getSHA256SecurePassword("password",""),true,"",""));
//        users.add(new User("store@decathlon.fr", HashUtil.getSHA256SecurePassword("password",""),true,"",""));
//        users.add(new User("store@carrefour.fr", HashUtil.getSHA256SecurePassword("password",""),true,"",""));
//        users.add(new User("store@naturea.fr", HashUtil.getSHA256SecurePassword("password",""),true,"",""));
//        users.add(new User("store@woodbrass.fr", HashUtil.getSHA256SecurePassword("password",""),true,"",""));
//        users.add(new User("store@fnac.fr", HashUtil.getSHA256SecurePassword("password",""),true,"",""));
//
//        //Client
//        users.add(new User("william.puech@lirmm.fr", HashUtil.getSHA256SecurePassword("password",""),false,"William","Puech"));
//
//        userDAO.insertAll(users);
//    }
}
