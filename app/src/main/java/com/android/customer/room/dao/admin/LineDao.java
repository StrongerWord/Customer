package com.android.customer.room.dao.admin;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.customer.room.entity.admin.LineEntity;

import java.util.List;

import io.reactivex.Completable;

// 定义数据访问对象的接口
@Dao
public interface LineDao {

    @Query("SELECT * FROM line")
    LiveData<List<LineEntity>> getAll();

    @Query("SELECT * FROM line WHERE lineName = :name")
    LiveData<LineEntity> queryLineByName(String name);
    @Update
    Completable update(LineEntity user);
    @Insert
    Completable insert(LineEntity user);
    @Delete
    Completable delete(LineEntity user);
}
