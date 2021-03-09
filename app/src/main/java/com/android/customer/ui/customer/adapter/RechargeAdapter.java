package com.android.customer.ui.customer.adapter;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.android.customer.R;
import com.android.customer.databinding.LayoutItemRechargeBinding;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;

import org.jetbrains.annotations.NotNull;

public class RechargeAdapter extends BaseQuickAdapter<String, BaseDataBindingHolder<LayoutItemRechargeBinding>> {

    private int index = 0;

    public RechargeAdapter() {
        super(R.layout.layout_item_recharge);
    }

    @Override
    protected void convert(@NotNull BaseDataBindingHolder<LayoutItemRechargeBinding> helper, String s) {
        LayoutItemRechargeBinding binding = helper.getDataBinding();
        if(binding == null) {
            return;
        }
        if(helper.getAdapterPosition() == index) {
            binding.vCard.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
            binding.tvPrice.setTextColor(ContextCompat.getColor(getContext(),R.color.white));
        }else {
            binding.vCard.setCardBackgroundColor(ContextCompat.getColor(getContext(),R.color.white));
            binding.tvPrice.setTextColor(Color.parseColor("#333333"));
        }
        binding.tvPrice.setText(s +"元");
    }

    public void select(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return index;
    }
}
