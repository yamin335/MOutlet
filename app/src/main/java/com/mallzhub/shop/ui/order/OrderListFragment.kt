package com.mallzhub.shop.ui.order

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.OrderListFragmentBinding
import com.mallzhub.shop.models.order.Order
import com.mallzhub.shop.ui.NavDrawerHandlerCallback
import com.mallzhub.shop.ui.common.BaseFragment
import com.mallzhub.shop.util.AppConstants.orderCancelled
import com.mallzhub.shop.util.AppConstants.orderDelivered
import com.mallzhub.shop.util.AppConstants.orderPicked
import com.mallzhub.shop.util.AppConstants.orderProcessing
import com.mallzhub.shop.util.AppConstants.orderShipped

class OrderListFragment : BaseFragment<OrderListFragmentBinding, OrderViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_order_list
    override val viewModel: OrderViewModel by viewModels {
        viewModelFactory
    }

    lateinit var orderListAdapter: OrderListAdapter

    private var drawerListener: NavDrawerHandlerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavDrawerHandlerCallback) {
            drawerListener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        drawerListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        if (orderList.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
            orderListAdapter.submitList(orderList)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.appLogo.setOnClickListener {
            drawerListener?.toggleNavDrawer()
        }

        orderListAdapter = OrderListAdapter(appExecutors) {
            OrderTrackHistoryFragment.title = it.invoiceId
            navigateTo(OrderListFragmentDirections.actionTransactionFragmentToOrderTrackHistoryFragment())
        }

        viewDataBinding.orderRecycler.adapter = orderListAdapter

        orderList.add(Order(0, "#STF93847562", "12-09-2012, 10:23PM", orderProcessing))
        orderList.add(Order(1, "#STF93847562", "12-09-2012, 10:23PM", orderPicked))
        orderList.add(Order(2, "#STF93847562", "12-09-2012, 10:23PM", orderShipped))
        orderList.add(Order(3, "#STF93847562", "12-09-2012, 10:23PM", orderDelivered))
        orderList.add(Order(4, "#STF93847562", "12-09-2012, 10:23PM", orderCancelled))
        orderList.add(Order(5, "#STF93847562", "12-09-2012, 10:23PM", orderProcessing))
        orderList.add(Order(6, "#STF93847562", "12-09-2012, 10:23PM", orderPicked))

        viewDataBinding.btnCreateOrder.setOnClickListener {
            navigateTo(OrderListFragmentDirections.actionOrderFragmentToCreateOrderFragment())
        }
    }

    companion object {
        var orderList = ArrayList<Order>()
    }
}