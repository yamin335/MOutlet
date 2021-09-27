package com.rtchubs.edokanpat.ui.order

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.BuildConfig
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.api.ApiCallStatus
import com.rtchubs.edokanpat.databinding.AddProductFragmentBinding
import com.rtchubs.edokanpat.databinding.CreateOrderFragmentBinding
import com.rtchubs.edokanpat.models.add_product.AddProductResponse
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.ui.customers.SelectCustomerFragment
import com.rtchubs.edokanpat.util.BitmapUtilss
import com.rtchubs.edokanpat.util.PermissionUtils.isCameraAndGalleryPermissionGranted
import com.rtchubs.edokanpat.util.PermissionUtils.isCameraPermission
import com.rtchubs.edokanpat.util.PermissionUtils.isGalleryPermission
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CreateOrderFragment : BaseFragment<CreateOrderFragmentBinding, CreateOrderViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_create_order
    override val viewModel: CreateOrderViewModel by viewModels {
        viewModelFactory
    }

    var taxType = ""

    var taxTypes = arrayOf("VAT/TAX Type", "VAT/TAX Inclusive", "VAT/TAX Exclusive")

    override fun onResume() {
        super.onResume()
        if (SelectCustomerFragment.selectedCustomer != null) {
            viewModel.selectedCustomer.postValue(SelectCustomerFragment.selectedCustomer)
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

        viewDataBinding.btnAddProduct.setOnClickListener {

        }

        viewDataBinding.selectCustomer.setOnClickListener {
            SelectCustomerFragment.selectedCustomer = null
            navigateTo(CreateOrderFragmentDirections.actionCreateOrderFragmentToSelectCustomerNavGraph())
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