package com.android.customer.ui.customer.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.customer.room.AppDatabase;
import com.android.customer.room.entity.OrderEntity;
import com.android.customer.room.entity.UserEntity;
import com.android.customer.room.entity.admin.LineEntity;
import com.android.customer.room.entity.admin.StationEntity;
import com.hjq.toast.ToastUtils;
import com.zjp.base.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {

    private final AppDatabase mAppDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(application);
    }

    /**
     * 查询所有地铁线路
     * @return
     */
    public LiveData<List<LineEntity>> queryAllLine() {
        return mAppDatabase.lineDao().getAll();
    }

    /**
     * 查询线路的所有站点
     * @param name
     * @return
     */
    public LiveData<List<StationEntity>> queryAllStation(String name) {
        return mAppDatabase.stationDao().queryStationByLineName(name);
    }

    /**
     * 查询用户信息
     * @param account
     * @return
     */
    public LiveData<UserEntity> getCurrentUser(String account) {
        return mAppDatabase.userDao().findByPhone(account);
    }

    /**
     * 更新用户数据
     * @param entity
     */
    public void updateUser(UserEntity entity) {
        mAppDatabase.userDao().update(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Runner","数据更新失败 " + e.getMessage());
                    }
                });
    }

    /**
     * 查询订单
     * @param account
     * @return
     */
    public LiveData<List<OrderEntity>> queryOrder(String account) {
        return mAppDatabase.orderDao().findByPhone(account);
    }

    /**
     * 添加订单
     * @param entity
     */
    public void insertOrder(OrderEntity entity) {
        mAppDatabase.orderDao().insert(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.show("购票成功");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Runner","数据更新失败 " + e.getMessage());
                    }
                });
    }

}
