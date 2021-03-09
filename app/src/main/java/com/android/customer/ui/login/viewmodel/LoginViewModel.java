package com.android.customer.ui.login.viewmodel;

import android.app.Application;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.customer.room.AppDatabase;
import com.android.customer.room.entity.UserEntity;
import com.android.customer.utils.SingleLiveEvent;
import com.hjq.toast.ToastUtils;
import com.zjp.base.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel {

    public MutableLiveData<Boolean> isCountDownFinish = new MutableLiveData<>();

    private final AppDatabase mAppDatabase;
    public MutableLiveData<Boolean> isInsertSuccess = new MutableLiveData<>();
    public MutableLiveData<Boolean> isUpdateSuccess = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mAppDatabase = AppDatabase.getInstance(application);
    }

    /**
     * 查询用户
     * @param account
     * @return
     */
    public LiveData<UserEntity> getCurrentUser(String account) {
        return mAppDatabase.userDao().findByPhone(account);
    }

    /**
     * 查询所有消费者
     * @param isAdmin
     * @return
     */
    public LiveData<List<UserEntity>> queryCustomers(boolean isAdmin) {
        return mAppDatabase.userDao().queryByIsAdmin(isAdmin);
    }

    public void insertUser(UserEntity entity) {
        mAppDatabase.userDao().insert(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                isInsertSuccess.postValue(true);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.e("Runner","数据插入失败 " + e.getMessage());
            }
        });
    }

    /**
     * 修改用户信息
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
                        isUpdateSuccess.setValue(true);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e("Runner","数据更新失败 " + e.getMessage());
                    }
                });
    }

    public void startCountDownTimer() {
        new CountDownTimer(2000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isCountDownFinish.postValue(true);
            }
        }.start();
    }
}
