package com.rtchubs.edokanpat.ui.customers

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
import com.rtchubs.edokanpat.databinding.AddCustomerFragmentBinding
import com.rtchubs.edokanpat.databinding.AddProductFragmentBinding
import com.rtchubs.edokanpat.models.add_product.ItemAddProduct
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.util.BitmapUtilss
import com.rtchubs.edokanpat.util.PermissionUtils.isCameraAndGalleryPermissionGranted
import com.rtchubs.edokanpat.util.PermissionUtils.isCameraPermission
import com.rtchubs.edokanpat.util.PermissionUtils.isGalleryPermission
import com.rtchubs.edokanpat.util.showWarningToast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddCustomerFragment : BaseFragment<AddCustomerFragmentBinding, AddCustomerViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_add_customer
    override val viewModel: AddCustomerViewModel by viewModels {
        viewModelFactory
    }

    var cityList = arrayOf("Select City", "Dhaka", "Khulna", "Chattogram", "Cumilla", "Rajshahi", "Barishal", "Rangpur")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        val cityAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item, cityList)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewDataBinding.spinnerCity.adapter = cityAdapter

        viewDataBinding.spinnerCity.onItemSelectedListener =
            object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position != 0) {
                    viewModel.city.postValue(cityList[position])
                } else {
                    viewModel.city.postValue(null)
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

        viewDataBinding.btnAdd.setOnClickListener {
            when {
                viewModel.name.value.isNullOrBlank() -> {
                    showWarningToast(requireContext(), "Please give customer name!")
                    return@setOnClickListener
                }
                viewModel.phone.value.isNullOrBlank() -> {
                    showWarningToast(requireContext(), "Please give mobile number!")
                    return@setOnClickListener
                }
                viewModel.phone.value?.length ?: 0 < 11 -> {
                    showWarningToast(requireContext(), "Please give a valid mobile number!")
                    return@setOnClickListener
                }
                viewModel.email.value.isNullOrBlank() -> {
                    showWarningToast(requireContext(), "Please give email!")
                    return@setOnClickListener
                }
                viewModel.contactPerson.value.isNullOrBlank() -> {
                    showWarningToast(requireContext(), "Please give contact name!")
                    return@setOnClickListener
                }
                viewModel.discountAmount.value.isNullOrBlank() -> {
                    showWarningToast(requireContext(), "Please give discount amount!")
                    return@setOnClickListener
                }
                viewModel.city.value.isNullOrBlank() -> {
                    showWarningToast(requireContext(), "Please give city name!")
                    return@setOnClickListener
                }
                viewModel.address.value.isNullOrBlank() -> {
                    showWarningToast(requireContext(), "Please give address!")
                    return@setOnClickListener
                }
                else ->{
                    viewModel.addNewCustomer("1234", preferencesHelper.merchantId)
                }
            }
        }

        viewModel.customer.observe(viewLifecycleOwner, androidx.lifecycle.Observer { customer ->
            customer?.let {
                navController.popBackStack()
            }
        })
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