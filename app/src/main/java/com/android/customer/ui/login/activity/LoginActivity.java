package com.android.customer.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;


import com.android.customer.ui.customer.MainActivity;
import com.android.customer.R;
import com.android.customer.common.Constants;
import com.android.customer.databinding.ActivityLoginBinding;
import com.android.customer.ui.admin.AdminMainActivity;
import com.android.customer.ui.login.viewmodel.LoginViewModel;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;
import com.zjp.base.activity.BaseActivity;

import java.util.Objects;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    public static void jumpToLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    protected void initView() {
        super.initView();
        mViewDataBinding.titlebar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                RegisterActivity.jumpToRegisterActivity(LoginActivity.this);
            }
        });

        mViewDataBinding.btnLoginCommit.setOnClickListener(v -> {
            String inputPhone = mViewDataBinding.etLoginPhone.getText().toString();
            String inputPwd = mViewDataBinding.etLoginPassword.getText().toString();
            if(TextUtils.isEmpty(inputPhone) || TextUtils.isEmpty(inputPwd)) {
                ToastUtils.show("请输入帐号或密码!");
                return;
            }
            mViewModel.getCurrentUser(inputPhone)
                    .observe(this,userEntity -> {
                        if(userEntity == null) {
                            ToastUtils.show("帐号不存在！");
                        }else {
                            if(Objects.equals(userEntity.password,inputPwd)) {
                                loginSuccess(userEntity.account);
                            }else {
                                ToastUtils.show("帐号或密码错误！");
                            }
                        }
                    });
        });
    }

    private void loginSuccess(String account) {
        mViewModel.getCurrentUser(account).observe(this, user -> {
            if (user != null) {
                MMKV mmkv = MMKV.mmkvWithID(Constants.LOGIN_USER);
                mmkv.encode(Constants.LOGIN_USER_PHONE,account);
                if(user.isAdmin) {
                    AdminMainActivity.jumpToAdminMainActivity(this);
                }else {
                    if(user.isCompleteInfo) {
                        MainActivity.jumpToMainActivity(this);
                    }else {
                        CompleteInfoActivity.jumpToCompleteActivity(this,user.account);
                    }
                }
                finish();
            }
        });
    }
}
