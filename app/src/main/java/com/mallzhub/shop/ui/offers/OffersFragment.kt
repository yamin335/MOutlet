package com.mallzhub.shop.ui.offers

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.OffersFragmentBinding
import com.mallzhub.shop.ui.common.BaseFragment

class OffersFragment : BaseFragment<OffersFragmentBinding, OffersViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_offers
    override val viewModel: OffersViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var offersListAdapter: OffersListAdapter

    override fun onResume() {
        super.onResume()
        viewModel.getAllOfferList(1, preferencesHelper.getMerchant().token)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerToolbar(viewDataBinding.toolbar)

        viewDataBinding.addOffer.setOnClickListener {
            navigateTo(OffersFragmentDirections.actionOffersFragmentToCreateOfferFragment())
        }

        viewModel.offerProductList.observe(viewLifecycleOwner, Observer { list ->
            if (list.isEmpty()) {
                viewDataBinding.offerProductsRecycler.visibility = View.GONE
                viewDataBinding.emptyView.visibility = View.VISIBLE
            } else {
                viewDataBinding.offerProductsRecycler.visibility = View.VISIBLE
                viewDataBinding.emptyView.visibility = View.GONE

                val merchantWiseProducts = list.filter { it.merchant_id == preferencesHelper.merchantId }

                offersListAdapter.submitList(merchantWiseProducts)
            }
        })

        offersListAdapter = OffersListAdapter(appExecutors) {
            viewModel.getProductDetails(it.product_id).observe(viewLifecycleOwner, Observer { product ->
                navigateTo(OffersFragmentDirections.actionOffersFragmentToProductDetailsNavGraph(product, it.discount_percent ?: 0))
            })
        }

        viewDataBinding.offerProductsRecycler.adapter = offersListAdapter
    }
}