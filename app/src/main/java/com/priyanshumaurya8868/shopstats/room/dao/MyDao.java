package com.priyanshumaurya8868.shopstats.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.priyanshumaurya8868.shopstats.room.entities.Item;
import com.priyanshumaurya8868.shopstats.room.entities.Period;
import com.priyanshumaurya8868.shopstats.room.relationship.PeriodWithItems;

import java.util.List;

@Dao
public interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPeriod(Period period);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item item);

    @Transaction
    @Query("SELECT * FROM Period WHERE date = :date")
    LiveData<PeriodWithItems> getPeriodAndItemWithDate(String date);

    @Query("SELECT * FROM Period")
    LiveData<List<Period>> getPeriods();

    @Query("SELECT * FROM Item")
    LiveData<List<Item>> getItem();

    @Transaction
    @Query("SELECT * FROM Period")
    LiveData<List<PeriodWithItems>> getAllPeriodAndItem();


}
