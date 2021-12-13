package com.mallzhub.shop.ui.mpos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.MPOSFragmentBinding
import com.mallzhub.shop.models.order.SalesData
import com.mallzhub.shop.ui.common.BaseFragment

class MPOSFragment : BaseFragment<MPOSFragmentBinding, MPOSViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_mpos
    override val viewModel: MPOSViewModel by viewModels {
        viewModelFactory
    }

    lateinit var mposOrderListAdapter: MPOSOrderListAdapter

    override fun onResume() {
        super.onResume()
        mposOrderListAdapter.submitList(orderList)
        showHideDataView()
        viewModel.getOrderList(1, preferencesHelper.getMerchant().email)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mposOrderListAdapter = MPOSOrderListAdapter(appExecutors) {
            navigateTo(MPOSFragmentDirections.actionMPOSFragmentToMPOSOrderDetailsFragment(it))
        }

        viewDataBinding.orderListRecycler.adapter = mposOrderListAdapter

        viewModel.orderItems.observe(viewLifecycleOwner, Observer {
            orderList = it as ArrayList<SalesData>
            mposOrderListAdapter.submitList(orderList)
            showHideDataView()
        })

        viewDataBinding.btnBarcodeScanner.setOnClickListener {
            navigateTo(MPOSFragmentDirections.actionMPOSFragmentToCreateMPOSOrderFragment())
        }
    }

    private fun showHideDataView() {
        if (orderList.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

    companion object {
        var orderList: ArrayList<SalesData> = ArrayList()
    }
}