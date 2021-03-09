package com.android.customer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.customer.room.entity.OrderEntity;
import com.android.customer.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Completable;

// 定义数据访问对象的接口
@Dao
public interface OrderDao {  // 定义成接口

    @Query("SELECT * FROM ticket_order WHERE account = :account")
    LiveData<List<OrderEntity>> findByPhone(String account);

    //Insert注解定义插入User
    @Insert
    Completable insert(OrderEntity user);
    //Delete注解定义删除User
    @Delete
    void delete(UserEntity user);
}
