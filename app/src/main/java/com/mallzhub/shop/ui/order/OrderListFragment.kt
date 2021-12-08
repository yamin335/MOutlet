package com.mallzhub.shop.ui.order

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.OrderListFragmentBinding
import com.mallzhub.shop.models.order.SalesData
import com.mallzhub.shop.ui.NavDrawerHandlerCallback
import com.mallzhub.shop.ui.common.BaseFragment

class OrderListFragment : BaseFragment<OrderListFragmentBinding, OrderViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_order_list
    override val viewModel: OrderViewModel by viewModels {
        viewModelFactory
    }

    lateinit var orderListAdapter: OrderListAdapter

    override fun onResume() {
        super.onResume()
        orderListAdapter.submitList(orderList)
        visibleGoneEmptyView()
        viewModel.getOrderList(1, preferencesHelper.getMerchant().email)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(viewDataBinding.toolbar)

        orderListAdapter = OrderListAdapter(appExecutors) {
            navigateTo(OrderListFragmentDirections.actionTransactionFragmentToOrderTrackHistoryFragment(it))
        }

        viewDataBinding.orderRecycler.adapter = orderListAdapter

        viewModel.orderItems.observe(viewLifecycleOwner, Observer {
            orderList = it as ArrayList<SalesData>
            orderListAdapter.submitList(orderList)
            visibleGoneEmptyView()
        })

        viewDataBinding.btnCreateOrder.setOnClickListener {
            navigateTo(OrderListFragmentDirections.actionOrderFragmentToCreateOrderFragment())
        }
    }

    private fun visibleGoneEmptyView() {
        if (orderList.isEmpty()) {
            viewDataBinding.orderRecycler.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.orderRecycler.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

    companion object {
        var orderList = ArrayList<SalesData>()
    }
}