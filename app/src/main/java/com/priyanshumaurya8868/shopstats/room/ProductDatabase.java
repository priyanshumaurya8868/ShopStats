package com.priyanshumaurya8868.shopstats.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.priyanshumaurya8868.shopstats.room.dao.MyDao;
import com.priyanshumaurya8868.shopstats.room.entities.Item;
import com.priyanshumaurya8868.shopstats.room.entities.Period;

@Database(entities = {Period.class, Item.class}, version = 1)
public abstract class ProductDatabase extends RoomDatabase {
    public abstract MyDao myDao();

    public static volatile ProductDatabase db = null;

    public static synchronized ProductDatabase getDB(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    ProductDatabase.class, "new_product_database.db").build();
        }
        return db;
    }


}

