package com.android.customer.ui.admin.adapter;

import com.android.customer.R;
import com.android.customer.databinding.LayoutItemLineBinding;
import com.android.customer.room.entity.admin.LineEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;

import org.jetbrains.annotations.NotNull;

public class LineAdapter extends BaseQuickAdapter<LineEntity, BaseDataBindingHolder<LayoutItemLineBinding>> {

    public LineAdapter() {
        super(R.layout.layout_item_line);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<LayoutItemLineBinding> helper, LineEntity item) {
        LayoutItemLineBinding lineBinding = helper.getDataBinding();
        if(lineBinding != null) {
            lineBinding.setLine(item);
        }
    }
}
