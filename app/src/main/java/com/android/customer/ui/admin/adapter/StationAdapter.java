package com.android.customer.ui.admin.adapter;

import com.android.customer.R;
import com.android.customer.databinding.LayoutItemStationBinding;
import com.android.customer.room.entity.admin.StationEntity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;

import org.jetbrains.annotations.NotNull;

public class StationAdapter extends BaseQuickAdapter<StationEntity, BaseDataBindingHolder<LayoutItemStationBinding>> {

    public StationAdapter() {
        super(R.layout.layout_item_station);
    }


    @Override
    protected void convert(@NotNull BaseDataBindingHolder<LayoutItemStationBinding> helper, StationEntity item) {
        LayoutItemStationBinding lineBinding = helper.getDataBinding();
        if(lineBinding != null) {
            lineBinding.setStation(item);
        }
    }
}
