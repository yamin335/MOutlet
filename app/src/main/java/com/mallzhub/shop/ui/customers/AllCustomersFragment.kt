package com.mallzhub.shop.ui.customers

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.AllCustomersFragmentBinding
import com.mallzhub.shop.models.customers.Customer
import com.mallzhub.shop.models.login.Merchant
import com.mallzhub.shop.ui.NavDrawerHandlerCallback
import com.mallzhub.shop.ui.common.BaseFragment

class AllCustomersFragment : BaseFragment<AllCustomersFragmentBinding, AllCustomersViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_all_customers
    override val viewModel: AllCustomersViewModel by viewModels {
        viewModelFactory
    }

    lateinit var allCustomersListAdapter: AllCustomersListAdapter

    private var drawerListener: NavDrawerHandlerCallback? = null

    lateinit var merchant: Merchant
    lateinit var searchView: SearchView

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

    override fun onResume() {
        super.onResume()
        //allCustomersListAdapter.submitDataList(allCustomers)
        viewModel.getCustomers(merchant.email ?: "")
        showHideDataView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        merchant = preferencesHelper.getMerchant()

        viewDataBinding.appLogo.setOnClickListener {
            drawerListener?.toggleNavDrawer()
        }

        allCustomersListAdapter = AllCustomersListAdapter (
            appExecutors
        ) { item ->
            navigateTo(AllCustomersFragmentDirections.actionAllCustomersFragmentToAddCustomerFragment(true, item))
        }

        viewDataBinding.allCustomersRecycler.adapter = allCustomersListAdapter

        viewDataBinding.addCustomer.setOnClickListener {
            navigateTo(AllCustomersFragmentDirections.actionAllCustomersFragmentToAddCustomerFragment(false, null))
        }

        viewModel.customerList.observe(viewLifecycleOwner, Observer { customers ->
            customers?.let {
                allCustomers = it
                showHideDataView()
                allCustomersListAdapter.submitDataList(allCustomers)
            }
        })

        viewModel.searchValue.observe(viewLifecycleOwner, Observer {
            allCustomersListAdapter.filter.filter(it)
        })
    }

    private fun showHideDataView() {
        if (allCustomers.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

    companion object {
        private var allCustomers: List<Customer> = ArrayList()
    }
}