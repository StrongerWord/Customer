package com.android.customer.ui.customer;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.customer.R;
import com.android.customer.common.Constants;
import com.android.customer.databinding.FragmentOrderHistoryBinding;
import com.android.customer.ui.customer.adapter.OrderHistoryAdapter;
import com.android.customer.ui.customer.viewmodel.MainViewModel;
import com.tencent.mmkv.MMKV;
import com.zjp.base.fragment.BaseFragment;

/**
 * 用户订单记录
 */
public class OrderHistoryFragment extends BaseFragment<FragmentOrderHistoryBinding, MainViewModel> {

    private OrderHistoryAdapter adapter = new OrderHistoryAdapter();

    public static OrderHistoryFragment newsInstance() {
        return new OrderHistoryFragment();
    }

    public static OrderHistoryFragment newsInstance(String account) {
        OrderHistoryFragment fragment = new OrderHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("account",account);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_order_history;
    }

    @Override
    protected void initView() {
        super.initView();
        mViewDataBinding.rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        mViewDataBinding.rvOrder.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mViewDataBinding.rvOrder.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        String account = "";
        if(getArguments() == null) {
            MMKV mmkv = MMKV.mmkvWithID(Constants.LOGIN_USER);
            account = mmkv.decodeString(Constants.LOGIN_USER_PHONE);
        }else {
            account = getArguments().getString("account");
        }

        mViewModel.queryOrder(account).observe(this,orders->{
            if(orders != null) {
                adapter.setList(orders);
            }
        });
    }
}
