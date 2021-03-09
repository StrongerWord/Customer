package com.android.customer.ui.customer;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.android.customer.R;
import com.android.customer.common.Constants;
import com.android.customer.databinding.ActivityRechargeBinding;
import com.android.customer.ui.customer.adapter.RechargeAdapter;
import com.android.customer.ui.login.viewmodel.LoginViewModel;
import com.android.customer.utils.BigDecimalUtils;
import com.android.customer.view.RecycleGridDivider;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.toast.ToastUtils;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.tencent.mmkv.MMKV;
import com.zjp.base.activity.BaseActivity;

import java.util.Arrays;

public class RechargeActivity extends BaseActivity<ActivityRechargeBinding, LoginViewModel> {

    private RechargeAdapter adapter = new RechargeAdapter();
    private String[] prices = {"5", "10", "20", "30", "50", "100"};
    private boolean hasRecharge = false;

    public static void jumpToRechargeActivity(Context context) {
        Intent intent = new Intent(context, RechargeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {
        super.initView();
        mViewDataBinding.rvPrice.setLayoutManager(new GridLayoutManager(this, 3));
        mViewDataBinding.rvPrice.addItemDecoration(new RecycleGridDivider(10));
        mViewDataBinding.rvPrice.setItemAnimator(null);
        mViewDataBinding.rvPrice.setAdapter(adapter);
        adapter.setList(Arrays.asList(prices));

        onClick();
    }

    private void onClick() {
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

        mViewDataBinding.tvRecharge.setOnClickListener(v -> {
            showMessagePositiveDialog();
        });

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            adapter.select(position);
            hasRecharge = false;
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.isUpdateSuccess.observe(this,result->{
            if(result) {
                ToastUtils.show("充值成功");
                finish();
            }
        });
    }

    private void showMessagePositiveDialog() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("")
                .setMessage("确定要充值吗？")
                .setSkinManager(QMUISkinManager.defaultInstance(getApplicationContext()))
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "充值", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        MMKV mmkv = MMKV.mmkvWithID(Constants.LOGIN_USER);
                        String account = mmkv.decodeString(Constants.LOGIN_USER_PHONE);
                        mViewModel.getCurrentUser(account).observe(RechargeActivity.this, user -> {
                            if (user != null && !hasRecharge) {
                                hasRecharge = true;
                                String selectPrice = prices[adapter.getSelectIndex()];
                                double balance = user.balance;
                                user.balance = balance + Double.parseDouble(selectPrice);
                                mViewModel.updateUser(user);
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

}
