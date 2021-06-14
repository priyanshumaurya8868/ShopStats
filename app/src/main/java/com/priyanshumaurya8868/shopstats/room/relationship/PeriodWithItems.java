package com.priyanshumaurya8868.shopstats.room.relationship;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.priyanshumaurya8868.shopstats.room.entities.Item;
import com.priyanshumaurya8868.shopstats.room.entities.Period;

import java.util.List;

public class PeriodWithItems {
    @Embedded
   public Period period;
    @Relation(
            parentColumn = "date",
            entityColumn = "date"
    )public List<Item> items;
}
