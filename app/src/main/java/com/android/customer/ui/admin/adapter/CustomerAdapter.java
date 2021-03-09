package com.android.customer.ui.admin.adapter;

import com.android.customer.R;
import com.android.customer.databinding.LayoutItemCustomerBinding;
import com.android.customer.databinding.LayoutItemLineBinding;
import com.android.customer.room.entity.UserEntity;
import com.android.customer.room.entity.admin.LineEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;

import org.jetbrains.annotations.NotNull;

public class CustomerAdapter extends BaseQuickAdapter<UserEntity, BaseDataBindingHolder<LayoutItemCustomerBinding>> {

    public CustomerAdapter() {
        super(R.layout.layout_item_customer);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<LayoutItemCustomerBinding> helper, UserEntity item) {
        LayoutItemCustomerBinding lineBinding = helper.getDataBinding();
        if(lineBinding != null) {
            lineBinding.setUser(item);
        }
    }
}
