package com.mallzhub.shop.ui.order

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.api.ApiCallStatus
import com.mallzhub.shop.databinding.CreateOrderFragmentBinding
import com.mallzhub.shop.models.order.OrderStoreBody
import com.mallzhub.shop.models.order.OrderStoreProduct
import com.mallzhub.shop.ui.common.BaseFragment
import com.mallzhub.shop.ui.customers.SelectCustomerFragment
import com.mallzhub.shop.ui.products.SelectProductFragment
import com.mallzhub.shop.util.addNewItem
import com.mallzhub.shop.util.removeItem
import com.mallzhub.shop.util.showWarningToast
import com.mallzhub.shop.util.toRounded
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

    var taxTypeValues = arrayOf("", "inclusive", "exclusive")

    var taxTypes = arrayOf("VAT/TAX Type", "VAT/TAX Inclusive", "VAT/TAX Exclusive")

    var total = 0.0

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
        viewModel.generateInvoiceID()
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
                taxType = taxTypeValues[position]
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

        viewModel.orderPlaceResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer { response ->
            response?.let {
                if (it.data?.sale != null) navController.popBackStack()
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
                total = 0.0
                it.forEach { item ->
                    val price = item.mrp ?: 0.0
                    val quantity = item.quantity ?: 0
                    total += price * quantity
                }
                total = total.toRounded(2)
                viewDataBinding.totalPrice = total.toString()
                viewDataBinding.linearTotal.visibility = View.VISIBLE
            }
        })

        viewDataBinding.btnSubmitOrder.setOnClickListener {
            if (viewModel.selectedCustomer.value == null) {
                showWarningToast(requireContext(), "Please select customer")
                return@setOnClickListener
            }

            if (viewModel.orderItems.value.isNullOrEmpty()) {
                showWarningToast(requireContext(), "Please select product")
                return@setOnClickListener
            }

            val productList = ArrayList<OrderStoreProduct>()
            viewModel.orderItems.value?.forEach { item ->
                val quantity = item.quantity ?: 1
                val mrp = item.mrp?.toInt() ?: 0
                productList.add(OrderStoreProduct(item.id, item.description, "qty",
                    quantity, item.mrp?.toInt(), 0, "0",
                    0, "0", mrp * quantity, ""))
            }

            val today = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(
                Date()
            )

            viewModel.placeOrder(OrderStoreBody(viewModel.selectedCustomer.value?.id, preferencesHelper.merchantId,
                "", viewModel.invoiceNumber.value, today, taxType, "", total.toInt(),
                0, 0, total.toInt(), 0, total.toInt(), productList))

        }

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