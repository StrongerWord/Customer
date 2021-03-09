package com.android.customer.ui.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.android.customer.R;
import com.android.customer.common.Constants;
import com.android.customer.databinding.ActivityCustomerDetailBinding;
import com.android.customer.ui.customer.OrderHistoryFragment;
import com.android.customer.ui.customer.viewmodel.MainViewModel;
import com.hjq.bar.OnTitleBarListener;
import com.tencent.mmkv.MMKV;
import com.zjp.base.activity.BaseActivity;

/**
 * 用户详情页
 */
public class CustomerDetailActivity extends BaseActivity<ActivityCustomerDetailBinding, MainViewModel> {


    public static void jumpToCustomerDetailActivity(Context context,String account) {
        Intent intent = new Intent(context, CustomerDetailActivity.class);
        intent.putExtra("account",account);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_detail;
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
    }

    @Override
    protected void initData() {
        super.initData();
        String account = getIntent().getStringExtra("account");
        mViewModel.getCurrentUser(account).observe(this,user->{
            if(user != null) {
                mViewDataBinding.setUser(user);
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fm_container,
                        OrderHistoryFragment.newsInstance(account)).commitAllowingStateLoss();
    }
}
