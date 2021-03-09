package com.android.customer.ui.login.activity;

import android.text.TextUtils;


import com.android.customer.ui.customer.MainActivity;
import com.android.customer.R;
import com.android.customer.common.Constants;
import com.android.customer.databinding.ActivitySplashBinding;
import com.android.customer.ui.admin.AdminMainActivity;
import com.android.customer.ui.login.viewmodel.LoginViewModel;
import com.gyf.immersionbar.ImmersionBar;
import com.tencent.mmkv.MMKV;
import com.zjp.base.activity.BaseActivity;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, LoginViewModel> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.startCountDownTimer();
        mViewModel.isCountDownFinish.observe(this, isFinish -> {
            if (isFinish) {
                MMKV mmkv = MMKV.mmkvWithID(Constants.LOGIN_USER);
                String phone = mmkv.decodeString(Constants.LOGIN_USER_PHONE);
                if (TextUtils.isEmpty(phone)) {
                    LoginActivity.jumpToLoginActivity(this);
                } else {
                    mViewModel.getCurrentUser(phone).observe(this, user -> {
                        if (user != null) {
                            if(user.isAdmin) {
                                AdminMainActivity.jumpToAdminMainActivity(this);
                            }else {
                                MainActivity.jumpToMainActivity(this);
                            }
                            finish();
                        }
                    });
                }
            }
        });
    }
}
