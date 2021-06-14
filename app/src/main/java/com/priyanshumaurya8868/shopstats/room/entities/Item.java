package com.priyanshumaurya8868.shopstats.room.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Item implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public float unitOfPrice;
    public String unitType;
    public float totalQuantity;
    public float pricePerUnit;
    public float totalBill;
    public boolean isPurchased;
    public String date;
    public  String time;
}
