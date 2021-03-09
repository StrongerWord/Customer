package com.android.customer.ui.customer;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.android.customer.R;
import com.android.customer.common.Constants;
import com.android.customer.databinding.ActivityMainBinding;
import com.android.customer.ui.admin.LineActivity;
import com.android.customer.ui.login.activity.LoginActivity;
import com.android.customer.ui.login.viewmodel.LoginViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hjq.bar.OnTitleBarListener;
import com.tencent.mmkv.MMKV;
import com.zjp.base.activity.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, LoginViewModel> {

    public static void jumpToMainActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
                MMKV mmkv = MMKV.mmkvWithID(Constants.LOGIN_USER);
                mmkv.encode(Constants.LOGIN_USER_PHONE, "");
                LoginActivity.jumpToLoginActivity(MainActivity.this);
                finish();
            }
        });

        mViewDataBinding.tvPay.setOnClickListener(v -> {
            RechargeActivity.jumpToRechargeActivity(this);
        });

        mViewDataBinding.btnBuyTicket.setOnClickListener(v ->
                BuyTicketActivity.jumpToBuyTicketActivity(this));

        mViewDataBinding.btnOrderManager.setOnClickListener(v -> {
            OrderHistoryActivity.jumpToOrderHistoryActivity(this);
        });
    }

    @Override
    protected void initData() {
        super.initData();
        MMKV mmkv = MMKV.mmkvWithID(Constants.LOGIN_USER);
        String account = mmkv.decodeString(Constants.LOGIN_USER_PHONE);
        mViewModel.getCurrentUser(account).observe(this, user -> {
            if (user != null) {
                mViewDataBinding.tvName.setText(user.name);
                mViewDataBinding.tvBalance.setText("" + user.balance);
            }
        });
    }
}