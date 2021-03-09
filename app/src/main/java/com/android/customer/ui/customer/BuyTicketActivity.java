package com.android.customer.ui.customer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.android.customer.R;
import com.android.customer.common.Constants;
import com.android.customer.databinding.ActivityBuyTicketBinding;
import com.android.customer.room.entity.OrderEntity;
import com.android.customer.room.entity.admin.LineEntity;
import com.android.customer.room.entity.admin.StationEntity;
import com.android.customer.ui.customer.viewmodel.MainViewModel;
import com.android.customer.utils.BigDecimalUtils;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.toast.ToastUtils;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.tencent.mmkv.MMKV;
import com.zjp.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuyTicketActivity extends BaseActivity<ActivityBuyTicketBinding, MainViewModel> {

    public static void jumpToBuyTicketActivity(Context context) {
        Intent intent = new Intent(context, BuyTicketActivity.class);
        context.startActivity(intent);
    }

    private List<String> lineList = new ArrayList<>();
    private List<String> stationList = new ArrayList<>();

    private boolean hasBuy = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_ticket;
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

        mViewDataBinding.tvStartLine.setOnClickListener(v -> showLinesDialog(false));
        mViewDataBinding.tvStartStation.setOnClickListener(v -> {
            String line = mViewDataBinding.tvStartLine.getText().toString();
            queryStation(line,false);
        });

        mViewDataBinding.tvEndLine.setOnClickListener(v -> showLinesDialog(true));
        mViewDataBinding.tvEndStation.setOnClickListener(v -> {
            String line = mViewDataBinding.tvEndLine.getText().toString();
            queryStation(line,true);
        });

        mViewDataBinding.tvBuy.setOnClickListener(v -> {
            String startLine = mViewDataBinding.tvEndLine.getText().toString();
            String endLine = mViewDataBinding.tvEndLine.getText().toString();
            String startStation = mViewDataBinding.tvStartStation.getText().toString();
            String endStation = mViewDataBinding.tvEndStation.getText().toString();

            if(TextUtils.isEmpty(startLine) || TextUtils.isEmpty(endLine) || TextUtils.isEmpty(startStation) || TextUtils.isEmpty(endStation)) {
                ToastUtils.show("请选择站点信息");
                return;
            }

            if(startLine.equals(endLine) && startStation.equals(endStation)) {
                ToastUtils.show("起始站不能和终点站相同");
                return;
            }
            OrderEntity order = new OrderEntity();
            order.startLine = startLine;
            order.startStation = startStation;
            order.endLine = endLine;
            order.endStation = endStation;
            showMessagePositiveDialog(order);
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.queryAllLine().observe(this,lines ->{
            if(lines != null) {
                lineList.clear();
                for (LineEntity line: lines) {
                    lineList.add(line.lineName);
                }
            }
        });
    }

    private void showMessagePositiveDialog(OrderEntity order) {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("")
                .setMessage("确定要购买吗？")
                .setSkinManager(QMUISkinManager.defaultInstance(getApplicationContext()))
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction(0, "购买", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        MMKV mmkv = MMKV.mmkvWithID(Constants.LOGIN_USER);
                        String account = mmkv.decodeString(Constants.LOGIN_USER_PHONE);
                        mViewModel.getCurrentUser(account).observe(BuyTicketActivity.this, user -> {
                            if (user != null && !hasBuy) {
                                double balance = user.balance;
//                                double price = BigDecimalUtils.randomPrice();//随机票价
                                double price = BigDecimalUtils.randomPrice();
                                if(user.balance < price) {
                                    ToastUtils.show("余额不足");
                                    return;
                                }
                                hasBuy = true;
                                user.balance = BigDecimalUtils.sub(balance+"","" + price,2);
                                mViewModel.updateUser(user);
                                order.account = user.account;
                                order.ticketPrice = price;
                                mViewModel.insertOrder(order);
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }


    private void queryStation(String line,boolean isEnd) {
        if("选择线路".equals(line)) {
            ToastUtils.show("请选择地铁线路");
            return;
        }
        mViewModel.queryAllStation(line).observe(this,stations ->{
            if(stations != null) {
                stationList.clear();
                for (StationEntity station: stations) {
                    stationList.add(station.stationName);
                }
                showStationDialog(isEnd);
            }
        });
    }

    private void showStationDialog(boolean isEnd) {
        if(stationList.size() == 0) {
            return;
        }
        new QMUIDialog.MenuDialogBuilder(this)
                .setSkinManager(QMUISkinManager.defaultInstance(getApplicationContext()))
                .addItems(stationList.toArray(new CharSequence[stationList.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isEnd) {
                            mViewDataBinding.tvEndStation.setText(stationList.get(which));
                        }else {
                            mViewDataBinding.tvStartStation.setText(stationList.get(which));
                        }
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    private void showLinesDialog(boolean isEnd) {
        new QMUIDialog.MenuDialogBuilder(this)
                .setSkinManager(QMUISkinManager.defaultInstance(getApplicationContext()))
                .addItems(lineList.toArray(new CharSequence[lineList.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(isEnd) {
                            mViewDataBinding.tvEndLine.setText(lineList.get(which));
                        }else {
                            mViewDataBinding.tvStartLine.setText(lineList.get(which));
                        }
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }
}
