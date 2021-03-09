package com.android.customer.ui.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.android.customer.R;
import com.android.customer.common.Constants;
import com.android.customer.databinding.ActivityAdminMainBinding;
import com.android.customer.ui.login.activity.LoginActivity;
import com.android.customer.ui.login.viewmodel.LoginViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hjq.bar.OnTitleBarListener;
import com.tencent.mmkv.MMKV;
import com.zjp.base.activity.BaseActivity;

/**
 * 管理员首页
 */
public class AdminMainActivity extends BaseActivity<ActivityAdminMainBinding, LoginViewModel> {

    public static void jumpToAdminMainActivity(Context context) {
        Intent intent = new Intent(context, AdminMainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_admin_main;
    }

    @Override
    protected void initView() {
        super.initView();
        Glide.with(this)
                .load(R.mipmap.icon_avatar)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mViewDataBinding.ivAvatar);

        mViewDataBinding.titlebar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                //退出登录
                MMKV mmkv = MMKV.mmkvWithID(Constants.LOGIN_USER);
                mmkv.encode(Constants.LOGIN_USER_PHONE,"");
                LoginActivity.jumpToLoginActivity(AdminMainActivity.this);
                finish();
            }
        });

        //跳转到线路管理
        mViewDataBinding.btnStationManager.setOnClickListener(v -> {
            LineActivity.jumpToLineActivity(this);
        });

        //跳转到用户管理
        mViewDataBinding.btnCustomerManager.setOnClickListener(v->CustomerManagerActivity.jumpToCustomerManagerActivity(this));
    }

    @Override
    protected void initData() {
        super.initData();
        //查询消费者人员数量
        mViewModel.queryCustomers(false)
                .observe(this,userList -> {
                    if(userList != null && userList.size() > 0) {
                        mViewDataBinding.tvCount.setText(userList.size()+"");
                    }
                });
    }
}