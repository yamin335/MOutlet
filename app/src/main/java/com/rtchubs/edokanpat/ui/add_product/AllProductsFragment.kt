package com.rtchubs.edokanpat.ui.add_product

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.AllProductsFragmentBinding
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.models.add_product.ItemAddProduct
import com.rtchubs.edokanpat.ui.NavDrawerHandlerCallback
import com.rtchubs.edokanpat.ui.common.BaseFragment

class AllProductsFragment : BaseFragment<AllProductsFragmentBinding, AllProductViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_all_products
    override val viewModel: AllProductViewModel by viewModels {
        viewModelFactory
    }

    lateinit var allProductListAdapter: AllProductListAdapter

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
        viewModel.getProductList(preferencesHelper.merchantId.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.appLogo.setOnClickListener {
            drawerListener?.toggleNavDrawer()
        }

        allProductListAdapter = AllProductListAdapter(
            appExecutors
        ) { item ->
            //navController.navigate(FavoriteFragmentDirections.actionFavoriteFragmentToProductDetailsFragment2(item))
        }

        viewDataBinding.allProductsRecycler.adapter = allProductListAdapter

        viewDataBinding.addProduct.setOnClickListener {
            navigateTo(AllProductsFragmentDirections.actionAllProductsFragmentToAddProductFragment())
        }

        viewModel.productListResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                it.data?.let { products ->
                    allProductsList = products
                    showHideDataView()
                    allProductListAdapter.submitList(allProductsList)
                }
            }
        })
    }

    private fun showHideDataView() {
        if (allProductsList.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

    companion object {
        var allProductsList: List<Product> = ArrayList()
        var id = 0
    }
}