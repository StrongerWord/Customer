package com.android.customer.room.dao.admin;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.customer.room.entity.admin.LineEntity;
import com.android.customer.room.entity.admin.StationEntity;

import java.util.List;

import io.reactivex.Completable;

// 定义数据访问对象的接口
@Dao
public interface StationDao {

    @Query("SELECT * FROM station WHERE lineName = :name")
    LiveData<List<StationEntity>> queryStationByLineName(String name);
    @Update
    Completable update(StationEntity station);
    @Insert
    Completable insert(StationEntity station);
    @Delete
    Completable delete(StationEntity station);
}
