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
import com.android.customer.room.entity.admin.LineEntity;
import com.android.customer.ui.admin.adapter.LineAdapter;
import com.android.customer.ui.admin.viewmodel.AdminMainViewModel;
import com.hjq.bar.OnTitleBarListener;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zjp.base.activity.BaseActivity;

public class LineActivity extends BaseActivity<ActivityLineBinding, AdminMainViewModel> {

    private final LineAdapter lineAdapter =  new LineAdapter();;

    public static void jumpToLineActivity(Context context) {
        Intent intent = new Intent(context,LineActivity.class);
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
        mViewDataBinding.rvLines.setLayoutManager(new LinearLayoutManager(this));
        mViewDataBinding.rvLines.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mViewDataBinding.rvLines.setAdapter(lineAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mViewModel.queryAllLine().observe(this, lineAdapter::setList);
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
                showAddLineDialog(null);
            }
        });

        lineAdapter.setOnItemClickListener((adapter, view, position) -> {
            StationActivity.jumpToStationActivity(this,lineAdapter.getItem(position).lineName);
        });

        lineAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showSingleChoiceDialog(lineAdapter.getItem(position));
            return false;
        });
    }

    private void showSingleChoiceDialog(LineEntity line) {
        final String[] items = new String[]{"修改线路名称", "删除线路"};
        new QMUIDialog.MenuDialogBuilder(this)
                .setSkinManager(QMUISkinManager.defaultInstance(getApplicationContext()))
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0) {
                            showAddLineDialog(line);
                        }else {
                            mViewModel.deleteLine(line);
                        }
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }

    private void showAddLineDialog(LineEntity line) {
        QMUIDialog.EditTextDialogBuilder builder =
                new QMUIDialog.EditTextDialogBuilder(LineActivity.this);
        builder.setTitle("添加地铁线路")
                .setSkinManager(QMUISkinManager.defaultInstance(getApplicationContext()))
                .setPlaceholder("请输入地铁名称")
                .setDefaultText(line == null?"":line.lineName)
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
                        if(line != null) {
                            line.lineName = text;
                            mViewModel.updateLine(line);
                        }else {
                            LineEntity line = new LineEntity();
                            line.lineName = text;
                            mViewModel.insertLine(line);
                        }
                        dialog.dismiss();
                    }
                })
                .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
    }
}
