package com.mallzhub.shop.ui.mpos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.MPOSFragmentBinding
import com.mallzhub.shop.models.MPOSOrder
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

        if (orderList.isEmpty()) {
            getOrderList()
            mposOrderListAdapter.submitList(orderList)
        } else {
            mposOrderListAdapter.submitList(orderList)
        }

        showHideDataView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mposOrderListAdapter = MPOSOrderListAdapter(appExecutors) {

        }

        viewDataBinding.orderListRecycler.adapter = mposOrderListAdapter

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

    private fun getOrderList() {
        var i = 1
        while (i < 6) {
            orderList.add(MPOSOrder(i, "#${i}6727493$i", "10 June, 2021", "${i*100+i*2}"))
            i++
        }
    }

    companion object {
        var orderList: ArrayList<MPOSOrder> = ArrayList()
    }
}