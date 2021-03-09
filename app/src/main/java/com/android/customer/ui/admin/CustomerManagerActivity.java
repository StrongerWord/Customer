package com.android.customer.ui.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.customer.R;
import com.android.customer.databinding.ActivityCustomerManagerBinding;
import com.android.customer.ui.admin.adapter.CustomerAdapter;
import com.android.customer.ui.customer.MainActivity;
import com.android.customer.ui.login.viewmodel.LoginViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.hjq.bar.OnTitleBarListener;
import com.zjp.base.activity.BaseActivity;

public class CustomerManagerActivity extends BaseActivity<ActivityCustomerManagerBinding, LoginViewModel> {

    private CustomerAdapter adapter = new CustomerAdapter();

    public static void jumpToCustomerManagerActivity(Context context) {
        Intent intent = new Intent(context, CustomerManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_manager;
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

        mViewDataBinding.rvCustomer.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBinding.rvCustomer.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mViewDataBinding.rvCustomer.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            CustomerDetailActivity.jumpToCustomerDetailActivity(this,adapter.getItem(position).account);
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.queryCustomers(false).observe(this,users->{
            if(users != null) {
                adapter.setList(users);
            }
        });
    }
}
