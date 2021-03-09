package com.android.customer.ui.customer;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.android.customer.R;
import com.android.customer.databinding.ActivityOrderHistoryBinding;
import com.android.customer.ui.customer.viewmodel.MainViewModel;
import com.hjq.bar.OnTitleBarListener;
import com.zjp.base.activity.BaseActivity;

public class OrderHistoryActivity extends BaseActivity<ActivityOrderHistoryBinding, MainViewModel> {

    public static void jumpToOrderHistoryActivity(Context context) {
        Intent intent = new Intent(context, OrderHistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_history;
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fm_container,OrderHistoryFragment.newsInstance()).commitAllowingStateLoss();
    }

    @Override
    protected void initData() {
        super.initData();

    }
}
