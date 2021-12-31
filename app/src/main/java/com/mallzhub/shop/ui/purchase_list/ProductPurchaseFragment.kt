package com.mallzhub.shop.ui.purchase_list

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.api.ApiCallStatus
import com.mallzhub.shop.databinding.PurchaseListFragmentBinding
import com.mallzhub.shop.models.order.SalesData
import com.mallzhub.shop.ui.NavDrawerHandlerCallback
import com.mallzhub.shop.ui.common.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductPurchaseFragment : BaseFragment<PurchaseListFragmentBinding, PurchaseListFragmentViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_purchase_list
    override val viewModel: PurchaseListFragmentViewModel by viewModels {
        viewModelFactory
    }

    lateinit var productPurchaseListAdapter: ProductPurchaseListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        productPurchaseListAdapter = ProductPurchaseListAdapter {

        }

        viewDataBinding.purchaseListRecycler.adapter = productPurchaseListAdapter

        viewModel.purchaseList.observe(viewLifecycleOwner, Observer { purchases ->
            if (!purchases.isNullOrEmpty()) {
                viewModel.apiCallStatus.postValue(ApiCallStatus.LOADING)
                productPurchaseListAdapter.clearData()
                CoroutineScope(Dispatchers.Main.immediate).launch {
                    delay(1000)
                    purchases.forEachIndexed { index, purchase ->
                        productPurchaseListAdapter.addItemToList(purchase, index)
                    }
                    viewModel.apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                }
            }
            visibleGoneEmptyView()
        })

        if (viewModel.purchaseList.value.isNullOrEmpty()) {
            viewModel.getPurchaseList(1,preferencesHelper.getMerchant().email ?: "")
        }
    }

    private fun visibleGoneEmptyView() {
        if (viewModel.purchaseList.value.isNullOrEmpty()) {
            viewDataBinding.purchaseListRecycler.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.purchaseListRecycler.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }
}