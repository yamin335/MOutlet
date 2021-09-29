package com.rtchubs.edokanpat.ui.order

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.api.ApiCallStatus
import com.rtchubs.edokanpat.databinding.CreateOrderFragmentBinding
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.ui.customers.SelectCustomerFragment
import com.rtchubs.edokanpat.ui.products.SelectProductFragment
import com.rtchubs.edokanpat.util.addNewItem
import com.rtchubs.edokanpat.util.removeItem

class CreateOrderFragment : BaseFragment<CreateOrderFragmentBinding, CreateOrderViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_create_order
    override val viewModel: CreateOrderViewModel by viewModels {
        viewModelFactory
    }

    lateinit var orderProductListAdapter: OrderProductListAdapter

    var taxType = ""

    var taxTypes = arrayOf("VAT/TAX Type", "VAT/TAX Inclusive", "VAT/TAX Exclusive")

    override fun onResume() {
        super.onResume()

        SelectCustomerFragment.selectedCustomer?.let {
            viewModel.selectedCustomer.postValue(it)
            SelectCustomerFragment.selectedCustomer = null
        }

        SelectProductFragment.selectedProduct?.let {
            val list = viewModel.orderItems.value ?: mutableListOf()
            if (list.contains(it)) {
                viewModel.incrementOrderItemQuantity(it.id)
            } else {
                it.quantity = 1
                viewModel.orderItems.addNewItem(it)
            }
            SelectProductFragment.selectedProduct = null
        }

        if (viewModel.selectedCustomer.value == null) {
            viewDataBinding.customerName.text = "Select Customer"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SelectCustomerFragment.selectedCustomer = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        orderProductListAdapter = OrderProductListAdapter (
            appExecutors,
            object : OrderProductListAdapter.CartItemActionCallback {
                override fun incrementCartItemQuantity(id: Int) {
                    viewModel.incrementOrderItemQuantity(id)
                }

                override fun decrementCartItemQuantity(id: Int) {
                    viewModel.decrementOrderItemQuantity(id)
                }

            }
        ) { item ->
            viewModel.orderItems.removeItem(item)
        }

        viewDataBinding.productRecycler.adapter = orderProductListAdapter

        viewModel.selectedCustomer.observe(viewLifecycleOwner, androidx.lifecycle.Observer { customer ->
            customer?.let {
                viewDataBinding.customerDetails.visibility = View.VISIBLE
            }
        })

        val categoryAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, taxTypes)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewDataBinding.spinnerVatTax.adapter = categoryAdapter

        viewDataBinding.spinnerVatTax.onItemSelectedListener =
            object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                taxType = if (position != 0) {
                    taxTypes[position]
                } else {
                    ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }
        }

        viewModel.addProductResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            response?.let {
                if (it.data != null) navController.popBackStack()
            }
        })

        viewModel.apiCallStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            viewDataBinding.btnSubmitOrder.isEnabled = it != ApiCallStatus.LOADING
        })

        viewDataBinding.selectCustomer.setOnClickListener {
            SelectCustomerFragment.selectedCustomer = null
            navigateTo(CreateOrderFragmentDirections.actionCreateOrderFragmentToSelectCustomerNavGraph())
        }

        viewDataBinding.btnAddProduct.setOnClickListener {
            SelectProductFragment.selectedProduct = null
            navigateTo(CreateOrderFragmentDirections.actionCreateOrderFragmentToSelectProductNavGraph())
        }

        viewModel.orderItems.observe(viewLifecycleOwner, androidx.lifecycle.Observer { orderItems ->
            orderItems?.let {
                showHideDataView()
                orderProductListAdapter.submitList(it)
                orderProductListAdapter.notifyDataSetChanged()
            }
        })

    }

    private fun showHideDataView() {
        if (viewModel.orderItems.value?.isEmpty() == true) {
            viewDataBinding.productRecycler.visibility = View.GONE
            viewDataBinding.textNoProductsFound.visibility = View.VISIBLE
        } else {
            viewDataBinding.productRecycler.visibility = View.VISIBLE
            viewDataBinding.textNoProductsFound.visibility = View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }
        }
        return true
    }
}