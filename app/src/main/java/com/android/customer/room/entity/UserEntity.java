package com.android.customer.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 用户信息
 */
@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String account;
    public String password;
    public boolean isAdmin;//是否为管理员
    public boolean isCompleteInfo;//是否完善资料

    //用户资料
    public String name;
    public String sex;
    public int age;
    public String idNumber;
    public String birthDay;
    public String homeTown;
    public String phone;
    public double balance;
}
