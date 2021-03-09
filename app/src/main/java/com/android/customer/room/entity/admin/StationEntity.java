package com.android.customer.room.entity.admin;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 站点
 */
@Entity(tableName = "station")
public class StationEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String stationName;
    public String lineName;
}
