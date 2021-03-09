package com.android.customer.room.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 订单
 */
@Entity(tableName = "ticket_order")
public class OrderEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String account;
    public String startLine;
    public String startStation;
    public String endLine;
    public String endStation;
    public double ticketPrice;
}
