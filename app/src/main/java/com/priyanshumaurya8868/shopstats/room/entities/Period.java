package com.priyanshumaurya8868.shopstats.room.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Period {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String date;

}
