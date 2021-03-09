package com.android.customer.ui.customer.adapter;

import com.android.customer.R;
import com.android.customer.databinding.LayoutItemOrderHistoryBinding;
import com.android.customer.room.entity.OrderEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;

import org.jetbrains.annotations.NotNull;

public class OrderHistoryAdapter extends BaseQuickAdapter<OrderEntity, BaseDataBindingHolder<LayoutItemOrderHistoryBinding>> {

    public OrderHistoryAdapter() {
        super(R.layout.layout_item_order_history);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<LayoutItemOrderHistoryBinding> helper, OrderEntity orderEntity) {
        LayoutItemOrderHistoryBinding binding = helper.getDataBinding();
        if(binding != null) {
            binding.setOrder(orderEntity);
        }
    }
}
