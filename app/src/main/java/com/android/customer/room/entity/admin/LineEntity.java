package com.android.customer.room.entity.admin;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 地铁线路
 */
@Entity(tableName = "line")
public class LineEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String lineName;
}
