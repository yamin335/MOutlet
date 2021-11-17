package com.mallzhub.shop.ui.transactions

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.shop.R
import com.mallzhub.shop.BR
import com.mallzhub.shop.databinding.TransactionsFragmentBinding
import com.mallzhub.shop.models.order.SalesData
import com.mallzhub.shop.ui.common.BaseFragment

class TransactionsFragment : BaseFragment<TransactionsFragmentBinding, TransactionsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_transactions
    override val viewModel: TransactionsViewModel by viewModels {
        viewModelFactory
    }

    lateinit var transactionsListAdapter: TransactionsListAdapter

    override fun onResume() {
        super.onResume()
        if (orderList.isEmpty()) {
            viewModel.getOrderList(1, preferencesHelper.getMerchant().email)
        } else {
            transactionsListAdapter.submitList(orderList)
        }

        visibleGoneEmptyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        transactionsListAdapter = TransactionsListAdapter(appExecutors) {
            navigateTo(TransactionsFragmentDirections.actionTransactionsFragmentToTransactionDetailsFragment(it))
        }

        viewDataBinding.transactionsRecycler.adapter = transactionsListAdapter

        viewModel.orderItems.observe(viewLifecycleOwner, Observer {
            orderList = it as ArrayList<SalesData>
            transactionsListAdapter.submitList(orderList)
            visibleGoneEmptyView()
        })
    }

    private fun visibleGoneEmptyView() {
        if (orderList.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

    companion object {
        var orderList = ArrayList<SalesData>()
    }
}