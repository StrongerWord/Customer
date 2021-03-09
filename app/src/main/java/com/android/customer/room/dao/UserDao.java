package com.android.customer.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.android.customer.room.entity.UserEntity;

import java.util.List;

import io.reactivex.Completable;

// 定义数据访问对象的接口
@Dao
public interface UserDao {  // 定义成接口
    // Query注解定义查询, 参数是sql语句
    @Query("SELECT * FROM user")
    LiveData<List<UserEntity>> getAll();

    // 根据phone查询user, :phone这里意思是引用findByPhone方法里参数id。是room定义固定写法：冒号+参数名称
    @Query("SELECT * FROM user WHERE account = :account")
    LiveData<UserEntity> findByPhone(String account);

    @Query("SELECT * FROM user WHERE isAdmin = :isAdmin")
    LiveData<List<UserEntity>> queryByIsAdmin(boolean isAdmin);
    //Update注解定义更新User
    @Update
    Completable update(UserEntity user);
    //Insert注解定义插入User
    @Insert
    Completable insert(UserEntity user);
    //Delete注解定义删除User
    @Delete
    void delete(UserEntity user);
}
