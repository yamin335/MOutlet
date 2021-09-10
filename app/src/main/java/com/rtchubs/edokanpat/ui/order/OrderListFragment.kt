package com.rtchubs.edokanpat.ui.order

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.AllProductsFragmentBinding
import com.rtchubs.edokanpat.databinding.OrderListFragmentBinding
import com.rtchubs.edokanpat.models.add_product.ItemAddProduct
import com.rtchubs.edokanpat.models.order.Order
import com.rtchubs.edokanpat.ui.LogoutHandlerCallback
import com.rtchubs.edokanpat.ui.NavDrawerHandlerCallback
import com.rtchubs.edokanpat.ui.add_product.AllProductListAdapter
import com.rtchubs.edokanpat.ui.add_product.AllProductViewModel
import com.rtchubs.edokanpat.ui.add_product.AllProductsFragmentDirections
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.util.AppConstants.orderCancelled
import com.rtchubs.edokanpat.util.AppConstants.orderDelivered
import com.rtchubs.edokanpat.util.AppConstants.orderPicked
import com.rtchubs.edokanpat.util.AppConstants.orderProcessing
import com.rtchubs.edokanpat.util.AppConstants.orderShipped

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
    }

    companion object {
        var orderList = ArrayList<Order>()
    }
}