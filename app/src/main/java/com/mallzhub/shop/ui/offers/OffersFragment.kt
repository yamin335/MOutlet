package com.mallzhub.shop.ui.offers

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.OffersFragmentBinding
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.ui.NavDrawerHandlerCallback
import com.mallzhub.shop.ui.common.BaseFragment
import okhttp3.internal.userAgent

class OffersFragment : BaseFragment<OffersFragmentBinding, OffersViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_offers
    override val viewModel: OffersViewModel by viewModels {
        viewModelFactory
    }

    private var drawerListener: NavDrawerHandlerCallback? = null

    private lateinit var offersListAdapter: OffersListAdapter

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
        viewModel.getAllOfferList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.appLogo.setOnClickListener {
            drawerListener?.toggleNavDrawer()
        }

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