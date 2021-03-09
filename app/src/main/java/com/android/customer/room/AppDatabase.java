package com.android.customer.room;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.android.customer.room.dao.OrderDao;
import com.android.customer.room.dao.UserDao;
import com.android.customer.room.dao.admin.LineDao;
import com.android.customer.room.dao.admin.StationDao;
import com.android.customer.room.entity.OrderEntity;
import com.android.customer.room.entity.UserEntity;
import com.android.customer.room.entity.admin.LineEntity;
import com.android.customer.room.entity.admin.StationEntity;

import org.junit.runner.manipulation.Orderer;

/**
 * 数据库处理
 */
//使用Database注解，定义entities类, entities参数是一个class[]，version是数据库的版本号
@Database(entities = {UserEntity.class, LineEntity.class, StationEntity.class, OrderEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract LineDao lineDao();
    public abstract StationDao stationDao();
    public abstract OrderDao orderDao();

    private static final String DB_NAME = "room_customer";
    private static volatile AppDatabase sInstance;

    //通常定义一个单例持有AppDatabase引用
    //DB_NAME是数据库文件名称
    public static AppDatabase getInstance(Application app){
        if(sInstance == null){
            synchronized (AppDatabase.class){
                if(sInstance == null){
                    sInstance = Room.databaseBuilder(app,
                            AppDatabase.class, DB_NAME).build();
                }
            }
        }
        return sInstance;
    }

}

