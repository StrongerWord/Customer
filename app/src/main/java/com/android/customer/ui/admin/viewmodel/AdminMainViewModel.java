package com.android.customer.ui.admin.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.android.customer.room.AppDatabase;
import com.android.customer.room.entity.admin.LineEntity;
import com.android.customer.room.entity.admin.StationEntity;
import com.hjq.toast.ToastUtils;
import com.zjp.base.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AdminMainViewModel extends BaseViewModel {

    private final AppDatabase mAppDatabase;

    public AdminMainViewModel(@NonNull Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(application);
    }

    /**
     * 添加地铁线路
     * @param line
     */
    public void insertLine(LineEntity line) {
        mAppDatabase.lineDao().insert(line)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.show("添加成功");
//                        queryAllLine();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Runner","数据插入失败 " + e.getMessage());
                    }
                });
    }

    /**
     * 修改地铁线路信息
     * @param line
     */
    public void updateLine(LineEntity line) {
        mAppDatabase.lineDao().update(line)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.show("修改成功");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Runner","修改失败 " + e.getMessage());
                    }
                });
    }

    public LiveData<List<LineEntity>> queryAllLine() {
        return mAppDatabase.lineDao().getAll();
    }

    /**
     * 删除地铁线路
     * @param line
     */
    public void deleteLine(LineEntity line) {
        mAppDatabase.lineDao().delete(line)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.show("删除成功");
//                        queryAllLine();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Runner","删除失败 " + e.getMessage());
                    }
                });
    }

    /**
     * 添加站点
     * @param station
     */
    public void insertStation(StationEntity station) {
        mAppDatabase.stationDao().insert(station)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.show("添加成功");
//                        queryAllLine();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Runner","数据插入失败 " + e.getMessage());
                    }
                });
    }

    /**
     * 更新指定站点数据
     * @param station
     */
    public void updateStation(StationEntity station) {
        mAppDatabase.stationDao().update(station)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.show("修改成功");
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Runner","修改失败 " + e.getMessage());
                    }
                });
    }

    /**
     * 根据线路名称查询站点
     * @param name
     * @return
     */
    public LiveData<List<StationEntity>> queryAllStation(String name) {
        return mAppDatabase.stationDao().queryStationByLineName(name);
    }

    /**
     * 删除指定站点
     * @param station
     */
    public void deleteStation(StationEntity station) {
        mAppDatabase.stationDao().delete(station)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.show("删除成功");
//                        queryAllLine();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Runner","删除失败 " + e.getMessage());
                    }
                });
    }
}
