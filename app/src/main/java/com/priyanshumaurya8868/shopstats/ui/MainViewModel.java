package com.priyanshumaurya8868.shopstats.ui;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.priyanshumaurya8868.shopstats.room.ProductDatabase;
import com.priyanshumaurya8868.shopstats.room.dao.MyDao;
import com.priyanshumaurya8868.shopstats.room.entities.Item;
import com.priyanshumaurya8868.shopstats.room.entities.Period;
import com.priyanshumaurya8868.shopstats.room.relationship.PeriodWithItems;
import com.priyanshumaurya8868.shopstats.room.repo.ProductRepo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewModel extends AndroidViewModel {

    public  LiveData<List<Period>> getAllPeriods;
    public  LiveData<List<Item>> getAllItems;
    public LiveData<List<PeriodWithItems>> getAllPeriodAndItems;
    private final ProductRepo repo;

    public MainViewModel(Application application) {
        super(application);
       MyDao dao = ProductDatabase.getDB(application).myDao();
        repo=  new ProductRepo(dao);
        getAllItems = dao.getItem();
        getAllPeriods = dao.getPeriods();
        getAllPeriodAndItems =  dao.getAllPeriodAndItem();
    }

    public void insertItems(Item item) {
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(() -> repo.insertItems(item));
    }

    public  void insertPeriods(Period period){
        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(() -> repo.insertPeriods(period));
    }

    public LiveData<PeriodWithItems> getPeriodAndItemsWithDate(String date){
      return   repo.getPeriodAndItemsWithDate(date);
    }
}
