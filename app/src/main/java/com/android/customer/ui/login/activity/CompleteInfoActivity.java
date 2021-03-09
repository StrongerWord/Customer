package com.android.customer.ui.login.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.android.customer.ui.customer.MainActivity;
import com.android.customer.R;
import com.android.customer.common.Constants;
import com.android.customer.databinding.ActivityCompleteInfoBinding;
import com.android.customer.room.entity.UserEntity;
import com.android.customer.ui.admin.AdminMainActivity;
import com.android.customer.ui.login.viewmodel.LoginViewModel;
import com.hjq.toast.ToastUtils;
import com.tencent.mmkv.MMKV;
import com.zjp.base.activity.BaseActivity;

public class CompleteInfoActivity extends BaseActivity<ActivityCompleteInfoBinding, LoginViewModel> {

    private String account;
    private boolean hasJump = false;

    public static void jumpToCompleteActivity(Context context, String account) {
        Intent intent = new Intent(context, CompleteInfoActivity.class);
        intent.putExtra("account", account);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_complete_info;
    }

    @Override
    protected void initView() {
        super.initView();
        onClick();
    }

    @Override
    protected void initData() {
        super.initData();
        account = getIntent().getStringExtra("account");

        mViewModel.isUpdateSuccess.observe(this, result -> {
            if (result&&!hasJump) {
                hasJump = true;
                MMKV mmkv = MMKV.mmkvWithID(Constants.LOGIN_USER);
                mmkv.encode(Constants.LOGIN_USER_PHONE, account);
                MainActivity.jumpToMainActivity(this);
                finish();
            }
        });
    }

    private void onClick() {
        mViewDataBinding.btnCommit.setOnClickListener(v -> {
            String name = mViewDataBinding.etName.getText().toString();
            String sex = mViewDataBinding.etSex.getText().toString();
            String age = mViewDataBinding.etAge.getText().toString();
            String idNumber = mViewDataBinding.etIdNumber.getText().toString();
            String birthday = mViewDataBinding.etBirthday.getText().toString();
            String address = mViewDataBinding.etHomeAddress.getText().toString();
            String phone = mViewDataBinding.etPhone.getText().toString();

            if (TextUtils.isEmpty(name)|| TextUtils.isEmpty(age) || TextUtils.isEmpty(idNumber) || TextUtils.isEmpty(phone)) {
                ToastUtils.show("有必填内容未填写");
                return;
            }

            mViewModel.getCurrentUser(account).observe(this, user -> {
                if (user != null) {
                    user.name = name;
                    user.age = Integer.parseInt(age);
                    user.sex = sex;
                    user.idNumber = idNumber;
                    user.birthDay = birthday;
                    user.homeTown = address;
                    user.phone = phone;
                    user.isCompleteInfo = true;
                    mViewModel.updateUser(user);
                }
            });

        });
    }
}
