package com.rtchubs.edokanpat.ui.products

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.SelectProductFragmentBinding
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.models.login.Merchant
import com.rtchubs.edokanpat.ui.add_product.AllProductListAdapter
import com.rtchubs.edokanpat.ui.common.BaseFragment

class SelectProductFragment : BaseFragment<SelectProductFragmentBinding, SelectProductViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_select_product
    override val viewModel: SelectProductViewModel by viewModels {
        viewModelFactory
    }

    companion object {
        var selectedProduct: Product? = null
        private var allProducts: List<Product> = ArrayList()
    }

    lateinit var allProductListAdapter: AllProductListAdapter

    lateinit var merchant: Merchant
    //lateinit var searchView: SearchView

    override fun onResume() {
        super.onResume()
        if (allProducts.isEmpty()) {
            viewModel.getProductList(preferencesHelper.merchantId.toString())
        } else {
            allProductListAdapter.submitList(allProducts)
            showHideDataView()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        merchant = preferencesHelper.getMerchant()

        allProductListAdapter = AllProductListAdapter (
            appExecutors
        ) { item ->
            selectedProduct = item
            navController.popBackStack()
        }

        viewDataBinding.productRecycler.adapter = allProductListAdapter

        viewModel.productListResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer { products ->
            products?.let {
                allProducts = it.data ?: ArrayList()
                showHideDataView()
                allProductListAdapter.submitList(allProducts)
            }
        })

    }

    private fun showHideDataView() {
        if (allProducts.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.search_bar, menu)
//
//        // Associate searchable configuration with the SearchView
//        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        searchView = menu.findItem(R.id.action_search).actionView as SearchView
//        searchView.setSearchableInfo(
//            searchManager.getSearchableInfo(requireActivity().componentName)
//        )
//        searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text).setBackgroundResource(R.drawable.abc_textfield_search_default_mtrl_alpha)
//        searchView.maxWidth = Int.MAX_VALUE
//
//        // listening to search query text change
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // filter recycler view when query submitted
//                allCustomersListAdapter.filter.filter(query)
//                return false
//            }
//
//            override fun onQueryTextChange(query: String?): Boolean {
//                // filter recycler view when text is changed
//                allCustomersListAdapter.filter.filter(query)
//                return false
//            }
//        })
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }
            R.id.action_search -> {
                return true
            }
        }
        return true
    }
}