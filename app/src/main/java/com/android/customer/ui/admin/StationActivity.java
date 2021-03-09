package com.android.customer.ui.admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.customer.R;
import com.android.customer.databinding.ActivityLineBinding;
import com.android.customer.room.entity.admin.StationEntity;
import com.android.customer.ui.admin.adapter.StationAdapter;
import com.android.customer.ui.admin.viewmodel.AdminMainViewModel;
import com.hjq.bar.OnTitleBarListener;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zjp.base.activity.BaseActivity;

public class StationActivity extends BaseActivity<ActivityLineBinding, AdminMainViewModel> {

    private final StationAdapter stationAdapter =  new StationAdapter();
    private String lineName;

    public static void jumpToStationActivity(Context context,String lineName) {
        Intent intent = new Intent(context,StationActivity.class);
        intent.putExtra("lineName",lineName);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_line;
    }

    @Override
    protected void initView() {
        super.initView();
        onClick();
        mViewDataBinding.titlebar.setTitle("站点管理");
        mViewDataBinding.rvLines.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBinding.rvLines.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mViewDataBinding.rvLines.setAdapter(stationAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        lineName = getIntent().getStringExtra("lineName");
        mViewModel.queryAllStation(lineName).observe(this, stationAdapter::setList);
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
                showAddStationDialog(null);
            }
        });

        stationAdapter.setOnItemClickListener((adapter, view, position) -> {
            showSingleChoiceDialog(stationAdapter.getItem(position));
        });
    }

    private void showSingleChoiceDialog(StationEntity station) {
        final String[] items = new String[]{"修改站点", "删除站点"};
        new QMUIDialog.MenuDialogBuilder(this)
                .setSkinManager(QMUISkinManager.defaultInstance(getApplicationContext()))
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0) {
                            showAddStationDialog(station);
                        }else {
                            mViewModel.deleteStation(station);
                        }
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    private void showAddStationDialog(StationEntity station) {
        QMUIDialog.EditTextDialogBuilder builder =
                new QMUIDialog.EditTextDialogBuilder(StationActivity.this);
        builder.setTitle("添加站点")
                .setSkinManager(QMUISkinManager.defaultInstance(getApplicationContext()))
                .setPlaceholder("请输入站点")
                .setDefaultText(station == null?"":station.stationName)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        EditText vInput = dialog.findViewById(R.id.qmui_dialog_edit_input);
                        if(vInput == null) {
                            return;
                        }
                        String text = vInput.getText().toString();

                        if(TextUtils.isEmpty(text)) {
                            return;
                        }
                        if(station != null) {
                            station.stationName = text;
                            mViewModel.updateStation(station);
                        }else {
                            StationEntity line = new StationEntity();
                            line.lineName = lineName;
                            line.stationName = text;
                            mViewModel.insertStation(line);
                        }
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();

    }
}
