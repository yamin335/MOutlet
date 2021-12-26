package com.mallzhub.shop.ui.stock_product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.StockProductDetailsFragmentBinding
import com.mallzhub.shop.ui.common.BaseFragment

class StockProductsDetailsFragment : BaseFragment<StockProductDetailsFragmentBinding, StockProductDetailsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_stock_products_details
    override val viewModel: StockProductDetailsViewModel by viewModels {
        viewModelFactory
    }

    lateinit var stockProductDetailsListAdapter: StockProductDetailsListAdapter

    val args: StockProductsDetailsFragmentArgs by navArgs()

    override fun onResume() {
        super.onResume()
        viewModel.getStockProductDetails(args.productDetailId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        viewDataBinding.toolbar.title = args.title

        stockProductDetailsListAdapter = StockProductDetailsListAdapter(appExecutors) {
            //navigateTo(OrderListFragmentDirections.actionTransactionFragmentToOrderTrackHistoryFragment(it))
        }

        viewDataBinding.productRecycler.adapter = stockProductDetailsListAdapter

        viewModel.stockProductsList.observe(viewLifecycleOwner, Observer { productList ->
            if (!productList.isNullOrEmpty()) {
                stockProductDetailsListAdapter.submitList(productList)
            }
            visibleGoneEmptyView()
        })
    }

    private fun visibleGoneEmptyView() {
        if (viewModel.stockProductsList.value.isNullOrEmpty()) {
            viewDataBinding.productRecycler.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.productRecycler.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }
}