package io.turntotech.android.navigation.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.turntotech.android.navigation.model.entity.Company;
import io.turntotech.android.navigation.model.entity.Product;

@Database(entities = {Company.class, Product.class},version = 2)
public abstract class LocalDatabase extends RoomDatabase {


    public static Company selectedCompany = null;

    public abstract DaoAccess daoAccess();

    private static LocalDatabase db = null;


    public static LocalDatabase getAppDatabase(Context context) {

        if(db==null) {
            db = Room.databaseBuilder(context,
                    LocalDatabase.class, "company-database").allowMainThreadQueries().build();

//            db = Room.inMemoryDatabaseBuilder(context,
//                    LocalDatabase.class).build();
        }
        return db;
    }
}
