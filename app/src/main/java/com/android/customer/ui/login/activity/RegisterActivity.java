package com.android.customer.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.android.customer.R;
import com.android.customer.databinding.ActivityRegisterBinding;
import com.android.customer.room.entity.UserEntity;
import com.android.customer.ui.admin.AdminMainActivity;
import com.android.customer.ui.login.viewmodel.LoginViewModel;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.toast.ToastUtils;
import com.zjp.base.activity.BaseActivity;

import java.util.Objects;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, LoginViewModel> {

    private boolean isAdmin;

    public static void jumpToRegisterActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
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
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
            }
        });

        /**
         * 是否设为管理员，管理员直接注册，不需要完善个人信息
         */
        mViewDataBinding.cxAdmin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                mViewDataBinding.btnLoginCommit.setText("注册");
            }else {
                mViewDataBinding.btnLoginCommit.setText("下一步");
            }
        });

        mViewDataBinding.btnLoginCommit.setOnClickListener(v -> {
            String account = mViewDataBinding.etLoginPhone.getText().toString();
            String pwd = mViewDataBinding.etLoginPassword.getText().toString();
            String rePwd = mViewDataBinding.etLoginAgainPassword.getText().toString();
            isAdmin = mViewDataBinding.cxAdmin.isChecked();
            if(TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(rePwd)) {
                ToastUtils.show("请输入帐号或密码!");
                return;
            }
            if(!Objects.equals(pwd,rePwd)) {
                ToastUtils.show("请输入帐号或密码!");
                return;
            }
            //添加用户
            UserEntity user = new UserEntity();
            user.account = account;
            user.password = pwd;
            user.isAdmin = isAdmin;
            mViewModel.insertUser(user);
        });
    }

    @Override
    protected void initData() {
        super.initData();
        //监听是否插入成功
        mViewModel.isInsertSuccess.observe(this,success -> {
            if(success) {
                if(isAdmin) {
                    //用户为管理员
                    AdminMainActivity.jumpToAdminMainActivity(this);
                }else {
                    //用户为消费者
                    String phone = mViewDataBinding.etLoginPhone.getText().toString();
                    CompleteInfoActivity.jumpToCompleteActivity(this,phone);
                }

            }
        });
    }
}
