package com.mallzhub.shop.ui.mpos

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.mallzhub.shop.BR
import com.mallzhub.shop.BuildConfig
import com.mallzhub.shop.R
import com.mallzhub.shop.api.ApiCallStatus
import com.mallzhub.shop.databinding.CreateMPOSOrderFragmentBinding
import com.mallzhub.shop.databinding.CreateOrderFragmentBinding
import com.mallzhub.shop.models.MPOSOrderProductsRequestBody
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.models.order.OrderStoreBody
import com.mallzhub.shop.models.order.OrderStoreProduct
import com.mallzhub.shop.ui.barcode_reader.LiveBarcodeScanningActivity
import com.mallzhub.shop.ui.common.BaseFragment
import com.mallzhub.shop.ui.common.CommonAlertDialog
import com.mallzhub.shop.ui.customers.SelectCustomerFragment
import com.mallzhub.shop.ui.order.OrderProductListAdapter
import com.mallzhub.shop.ui.products.SelectProductFragment
import com.mallzhub.shop.util.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateMPOSOrderFragment : BaseFragment<CreateMPOSOrderFragmentBinding, CreateMPOSOrderViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_create_mpos_order
    override val viewModel: CreateMPOSOrderViewModel by viewModels {
        viewModelFactory
    }

    lateinit var orderProductListAdapter: MPOSOrderProductListAdapter


    lateinit var permissionRequestLauncher: ActivityResultLauncher<Array<String>>
    lateinit var qrCodeScannerLauncher: ActivityResultLauncher<Intent>

    val requiredPermissions = arrayOf(
        Manifest.permission.CAMERA
    )

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

//        SelectProductFragment.selectedProduct?.let {
//            val list = viewModel.orderItems.value ?: mutableListOf()
//            if (list.contains(it)) {
//                viewModel.incrementOrderItemQuantity(it.id)
//            } else {
//                it.available_qty = 1
//                viewModel.orderItems.addNewItem(it)
//            }
//            SelectProductFragment.selectedProduct = null
//        }

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
        //setHasOptionsMenu(true)
        viewModel.generateInvoiceID()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        orderProductListAdapter = MPOSOrderProductListAdapter (appExecutors) { item ->
            viewModel.orderItems.removeItem(item)
        }

        viewDataBinding.productRecycler.adapter = orderProductListAdapter

        viewModel.orderProducts.observe(viewLifecycleOwner, androidx.lifecycle.Observer { products ->
            products?.let { orderProducts ->
                orderProducts.forEach { item ->
                    viewModel.orderItems.addNewItem(item)
                }
                viewModel.orderProducts.postValue(null)
            }
        })

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
            navigateTo(CreateMPOSOrderFragmentDirections.actionCreateMPOSOrderFragmentToSelectCustomerNavGraph())
        }

        viewModel.orderItems.observe(viewLifecycleOwner, androidx.lifecycle.Observer { orderItems ->
            orderItems?.let {
                showHideDataView()
                orderProductListAdapter.submitList(it)
                orderProductListAdapter.notifyDataSetChanged()
                total = 0.0
                it.forEach { item ->
                    val price = item.product_detail?.selling_price ?: 0
                    total += price
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
                val mrp = item.product_detail?.selling_price ?: 0
                productList.add(OrderStoreProduct(item.id, item.product?.description, "qty",
                    1, mrp, 0, "0",
                    0, "0", mrp, ""))
            }

            val today = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(
                Date()
            )

            viewModel.placeOrder(OrderStoreBody(viewModel.selectedCustomer.value?.id, preferencesHelper.merchantId,
                "", viewModel.invoiceNumber.value, today, taxType, "", total.toInt(),
                0, 0, total.toInt(), 0, total.toInt(), productList))
        }

        permissionRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value == true
            }

            if (granted) {
                scanQRCode()
            } else {
                val shouldShowPermissionRationaleDialog = PermissionUtils.checkPermissionRationale(
                    requireActivity() as AppCompatActivity,
                    requiredPermissions)

                if (shouldShowPermissionRationaleDialog) {
                    val explanationDialog = CommonAlertDialog(object :  CommonAlertDialog.YesCallback{
                        override fun onYes() {
                            permissionRequestLauncher.launch(requiredPermissions)
                        }
                    }, "Allow Permissions!", "Allow location and camera permissions to " +
                            "use this feature.\n\nDo you want to allow permission?")
                    explanationDialog.show(childFragmentManager, "#call_permission_dialog")
                } else {
                    val explanationDialog = CommonAlertDialog(object :  CommonAlertDialog.YesCallback{
                        override fun onYes() {
                            PermissionUtils.goToSettings(requireContext(), BuildConfig.APPLICATION_ID)
                        }
                    }, "Allow Permissions!", "Allow location and camera permissions to " +
                            "use this feature.\n\nDo you want to allow permission?")
                    explanationDialog.show(childFragmentManager, "#call_permission_dialog")
                }
            }
        }

        qrCodeScannerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            val intent = result.data ?: return@registerForActivityResult
            if (intent.hasExtra("barcode_result")) {
                val qrCodeData = intent.getStringExtra("barcode_result") ?: ""
                if (qrCodeData.isNotBlank()) {
                    val dataArray = qrCodeData.split(",")
                    if (dataArray.isNotEmpty()) {
                        val codes = ArrayList<Long>()
                        for(data in dataArray) {
                            try {
                                val code = data.toLong()
                                codes.add(code)
                            } catch (e: Exception) {
                                e.printStackTrace()
                                showErrorToast(requireContext(), "Invalid Barcode Found!")
                            }
                        }
                        viewModel.getProductsByBarcodes(MPOSOrderProductsRequestBody(codes))
                    } else {
                        showErrorToast(requireContext(), "No product added!")
                    }
                } else {
                    showErrorToast(requireContext(), "Invalid QR Code!")
                }
            }
        }

        viewDataBinding.btnAddProduct.setOnClickListener {
            scanQRCode()
        }

        if (viewModel.orderItems.value.isNullOrEmpty()) {
            scanQRCode()
        }
    }


    private fun scanQRCode() {
        if (PermissionUtils.checkPermission(
                requireActivity() as AppCompatActivity,
                requiredPermissions
            )) {
            qrCodeScannerLauncher.launch(Intent(requireActivity(), LiveBarcodeScanningActivity::class.java))
        } else {
            permissionRequestLauncher.launch(requiredPermissions)
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