package com.priyanshumaurya8868.shopstats.room.repo;

import androidx.lifecycle.LiveData;

import com.priyanshumaurya8868.shopstats.room.dao.MyDao;
import com.priyanshumaurya8868.shopstats.room.entities.Item;
import com.priyanshumaurya8868.shopstats.room.entities.Period;
import com.priyanshumaurya8868.shopstats.room.relationship.PeriodWithItems;

public class ProductRepo {
    MyDao dao;

    public ProductRepo(MyDao dao) {
        this.dao = dao;
    }

    public LiveData<PeriodWithItems> getPeriodAndItemsWithDate(String date){
       return dao.getPeriodAndItemWithDate(date);
    }
    public void insertPeriods(Period p){
        dao.insertPeriod(p);
    }
    public void  insertItems(Item i){
        dao.insertItem(i);
    }
}
