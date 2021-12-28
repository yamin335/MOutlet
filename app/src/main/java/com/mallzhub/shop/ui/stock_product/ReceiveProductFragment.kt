package com.mallzhub.shop.ui.stock_product

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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mallzhub.shop.BR
import com.mallzhub.shop.BuildConfig
import com.mallzhub.shop.R
import com.mallzhub.shop.api.ApiCallStatus
import com.mallzhub.shop.databinding.ReceiveProductFragmentBinding
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.models.product_stock.ReceiveProductStoreBody
import com.mallzhub.shop.ui.add_product.AllProductListAdapter
import com.mallzhub.shop.ui.add_product.SampleImageListAdapter
import com.mallzhub.shop.ui.common.BaseFragment
import com.mallzhub.shop.ui.products.SelectProductFragment
import com.mallzhub.shop.util.BitmapUtilss
import com.mallzhub.shop.util.PermissionUtils.isCameraAndGalleryPermissionGranted
import com.mallzhub.shop.util.PermissionUtils.isCameraPermission
import com.mallzhub.shop.util.PermissionUtils.isGalleryPermission
import com.mallzhub.shop.util.addNewItem
import com.mallzhub.shop.util.showSuccessToast
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import kotlin.collections.ArrayList

const val PERMISSION_REQUEST_CODE = 111
private const val FEATURE_IMAGE = 1
private const val SAMPLE_IMAGE = 2
private const val IMAGE_FOLDER_NAME = "product_images"
class ReceiveProductFragment : BaseFragment<ReceiveProductFragmentBinding, ReceiveProductViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_receive_product
    override val viewModel: ReceiveProductViewModel by viewModels {
        viewModelFactory
    }

    var placeholder: Drawable? = null
    var featureImage: Bitmap? = null
    var imageType = 0
    var imageForPosition = 0

    lateinit var picFromCameraLauncher: ActivityResultLauncher<Intent>
    lateinit var picFromGalleryLauncher: ActivityResultLauncher<Intent>

    lateinit var currentPhotoPath: String

    lateinit var sampleImageAdapter: SampleImageListAdapter

    lateinit var selectedProductListAdapter: AllProductListAdapter

    var product: Product? = null

    val args: ReceiveProductFragmentArgs by navArgs()
    var isEditMode = false
    lateinit var receiveProductStoreBody: ReceiveProductStoreBody

    override fun onResume() {
        super.onResume()
        SelectProductFragment.selectedProduct?.let { product ->
            val list = viewModel.selectedProduct.value ?: mutableListOf()
            if (!list.contains(product)) {
                product.available_qty = 1
                viewModel.selectedProduct.addNewItem(product)
                receiveProductStoreBody.product = product
                viewModel.getProductDetails(product.id).observe(viewLifecycleOwner, Observer { response ->
                    response?.let {
                        receiveProductStoreBody.product_id = product.id
                        receiveProductStoreBody.unit_price = product.selling_price?.toInt()
                        receiveProductStoreBody.qty = 1
                        receiveProductStoreBody.sub_total = 1 * (receiveProductStoreBody.unit_price ?: 0)
                        receiveProductStoreBody.attributes = response.data?.attributes
                    }
                })
            }
            SelectProductFragment.selectedProduct = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        placeholder = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.image_placeholder
        )

        receiveProductStoreBody = ReceiveProductStoreBody(3, 3, 0,
            "", "", "", 0, 0, 0, 0,
            0, "", 0, "", 0, "",
            "", "", 0, null, "", "",
            "", 1, null, "")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        isEditMode = args.isEdit

        selectedProductListAdapter = AllProductListAdapter(
            appExecutors
        ) { item ->

        }

        viewDataBinding.productRecycler.adapter = selectedProductListAdapter

        sampleImageAdapter = SampleImageListAdapter {
            imageForPosition = it
            imageType = SAMPLE_IMAGE
            captureImage()
        }

        viewDataBinding.sampleImageRecycler.adapter = sampleImageAdapter

        var i = 0
        while (i < 5) {
            sampleImageAdapter.setImage(null, i)
            i++
        }

        checkImages()

        viewDataBinding.llAddFeatureImage.setOnClickListener {
            imageType = FEATURE_IMAGE
            captureImage()
        }

        viewDataBinding.btnAddFeatureImage.setOnClickListener {
            imageType = FEATURE_IMAGE
            captureImage()
        }

        picFromCameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            val file = File(currentPhotoPath)
            val imageBitmap = BitmapUtilss.getBitmapFromContentUri(
                requireContext().contentResolver, Uri.fromFile(
                    file
                )
            )
            val bitmap = imageBitmap ?: return@registerForActivityResult

            when(imageType) {
                FEATURE_IMAGE -> {
                    featureImage = BitmapUtilss.getResizedBitmap(bitmap, 700)
                    Glide.with(requireContext())
                        .load(featureImage)
                        .centerCrop()
                        .placeholder(placeholder)
                        .into(viewDataBinding.featureImage)
                }

                SAMPLE_IMAGE -> {
                    sampleImageAdapter.setImage(bitmap, imageForPosition)
                }
            }
            checkImages()
        }

        picFromGalleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            val photoUri = result?.data?.data
            photoUri?.let {
                val imageBitmap = BitmapUtilss.getBitmapFromContentUri(
                    requireContext().contentResolver, photoUri
                )
                val bitmap = imageBitmap ?: return@registerForActivityResult

                when(imageType) {
                    FEATURE_IMAGE -> {
                        featureImage = BitmapUtilss.getResizedBitmap(bitmap, 700)
                        Glide.with(requireContext())
                            .load(featureImage)
                            .centerCrop()
                            .placeholder(placeholder)
                            .into(viewDataBinding.featureImage)
                    }

                    SAMPLE_IMAGE -> {
                        sampleImageAdapter.setImage(bitmap, imageForPosition)
                    }

                    else -> {}
                }

                checkImages()
            }
        }

        viewModel.apiCallStatus.observe(viewLifecycleOwner, Observer {
            viewDataBinding.btnReceiveProduct.isEnabled = it != ApiCallStatus.LOADING
        })

        if (isEditMode) {
            product = args.product
            val item = product ?: return

            Glide.with(this)
                .asBitmap()
                .load(item.thumbnail)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        featureImage = resource
                        viewDataBinding.featureImage.setImageBitmap(featureImage)
                        checkImages()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            Glide.with(this)
                .asBitmap()
                .load(item.product_image1)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        sampleImageAdapter.setImage(resource, 0)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            Glide.with(this)
                .asBitmap()
                .load(item.product_image2)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        sampleImageAdapter.setImage(resource, 1)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            Glide.with(this)
                .asBitmap()
                .load(item.product_image3)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        sampleImageAdapter.setImage(resource, 2)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            Glide.with(this)
                .asBitmap()
                .load(item.product_image4)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        sampleImageAdapter.setImage(resource, 3)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            Glide.with(this)
                .asBitmap()
                .load(item.product_image5)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        sampleImageAdapter.setImage(resource, 4)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })

            viewModel.buyingPrice.postValue(item.buying_price?.toString())
            viewModel.sellingPrice.postValue(item.selling_price?.toString())
            viewModel.expiredDate.postValue(item.expired_date)
            viewModel.description.postValue(item.description)
        }

        viewDataBinding.btnSelectProduct.setOnClickListener {
            SelectProductFragment.selectedProduct = null
            navigateTo(ReceiveProductFragmentDirections.actionReceiveProductFragmentToSelectProductNavGraph())
        }

        viewModel.selectedProduct.observe(viewLifecycleOwner, androidx.lifecycle.Observer { orderItems ->
            orderItems?.let {
                val size = viewModel.selectedProduct.value?.size ?: 0
                viewDataBinding.btnSelectProduct.visibility = if (size > 0) View.INVISIBLE else View.VISIBLE
                showHideDataView()
                selectedProductListAdapter.submitList(it)
                selectedProductListAdapter.notifyDataSetChanged()
            }
        })

        viewModel.imageUploadResponse.observe(viewLifecycleOwner, Observer { response ->
            response?.let { imageNames ->
                var images = ""
                imageNames.forEach { imageName ->
                    images = if (images.isBlank()) imageName else "$images,$imageName"
                }
                receiveProductStoreBody.images = images
                receiveProductStoreBody.description = viewModel.description.value
                receiveProductStoreBody.buying_price = viewModel.buyingPrice.value
                receiveProductStoreBody.selling_price = viewModel.sellingPrice.value
                receiveProductStoreBody.unitType = "1"
                receiveProductStoreBody.total_received = 1
                receiveProductStoreBody.merchant_id = preferencesHelper.merchantId

                viewModel.storeReceivedProduct(
                    receiveProductStoreBody
                ).observe(viewLifecycleOwner, Observer { storeResponse ->
                    if (storeResponse.data != null) {
                        showSuccessToast(requireContext(), "Product stored successfully!")
                        navController.popBackStack()
                    }
                })
            }
        })

        viewDataBinding.btnReceiveProduct.setOnClickListener {
            val imagePaths = ArrayList<String>()
            val sampleImages = sampleImageAdapter.getImageList()
            viewModel.apiCallStatus.postValue(ApiCallStatus.LOADING)
            if (BitmapUtilss.makeEmptyFolderIntoExternalStorageWithTitle(requireContext(), IMAGE_FOLDER_NAME)) {
                featureImage?.let {
                    val file = BitmapUtilss.makeEmptyFileIntoExternalStorageWithTitle(requireContext(), IMAGE_FOLDER_NAME, "thumbnail.jpg")
                    if (file.exists()) file.delete()

                    try {
                        BitmapUtilss.saveBitmapFileIntoExternalStorageWithTitle(it, file)
                    } catch (e: IOException) {
                        viewModel.apiCallStatus.postValue(ApiCallStatus.ERROR)
                        return@let
                    }
                    imagePaths.add(file.path)
                }

                sampleImages.forEachIndexed { index, bitmap ->
                    bitmap?.let {
                        val file = BitmapUtilss.makeEmptyFileIntoExternalStorageWithTitle(requireContext(), IMAGE_FOLDER_NAME, "product_image$index.jpg")
                        if (file.exists()) file.delete()

                        try {
                            BitmapUtilss.saveBitmapFileIntoExternalStorageWithTitle(it, file)
                        } catch (e: IOException) {
                            viewModel.apiCallStatus.postValue(ApiCallStatus.ERROR)
                            return@let
                        }
                        imagePaths.add(file.path)
                    }
                }

                viewModel.uploadReceiveProductImages(
                    preferencesHelper.getMerchant().token,
                    "stock_barcode", "filelists",
                    "multi", imagePaths
                )
            }
        }

    }

    private fun showHideDataView() {
        if (viewModel.selectedProduct.value.isNullOrEmpty()) {
            viewDataBinding.productRecycler.visibility = View.GONE
            viewDataBinding.textNoProductsFound.visibility = View.VISIBLE
        } else {
            viewDataBinding.productRecycler.visibility = View.VISIBLE
            viewDataBinding.textNoProductsFound.visibility = View.GONE
        }
    }

    private fun checkImages() {
        if (featureImage == null) {
            viewDataBinding.btnAddFeatureImage.visibility = View.GONE
            viewDataBinding.llAddFeatureImage.visibility = View.VISIBLE
        } else {
            viewDataBinding.btnAddFeatureImage.visibility = View.VISIBLE
            viewDataBinding.llAddFeatureImage.visibility = View.GONE
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

    private fun captureImage() {
        if (isCameraAndGalleryPermissionGranted(requireActivity())) {
            selectImage()
        }
    }

    private fun selectImage() {
        val items = arrayOf<CharSequence>(
            getString(R.string.take_picture), getString(R.string.choose_from_gallery),
            getString(R.string.cancel)
        )
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle(getString(R.string.choose_an_option))
        builder.setItems(items) { dialog: DialogInterface, item: Int ->
            if (items[item] == getString(R.string.take_picture)) {
                if (isCameraPermission(requireActivity())) {
                    captureImageFromCamera()
                }
            } else if (items[item] == getString(R.string.choose_from_gallery)) {
                if (isGalleryPermission(requireActivity())) {
                    chooseImageFromGallery()
                }
            } else if (items[item] == getString(R.string.cancel)) {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun captureImageFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: Exception) {
                    // Error occurred while creating the File
                    ex.printStackTrace()
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${BuildConfig.APPLICATION_ID}.provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    val flags: Int = takePictureIntent.flags
                    // check that the nested intent does not grant URI permissions
                    if (flags and Intent.FLAG_GRANT_READ_URI_PERMISSION == 0 &&
                        flags and Intent.FLAG_GRANT_WRITE_URI_PERMISSION == 0
                    ) {
                        // redirect the nested Intent
                        picFromCameraLauncher.launch(takePictureIntent)
                    }
                }
            }
        }
    }

    private fun chooseImageFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK
        )
        galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        galleryIntent.resolveActivity(requireActivity().packageManager)?.let {
            val flags: Int = galleryIntent.flags
            // check that the nested intent does not grant URI permissions
            if (flags and Intent.FLAG_GRANT_READ_URI_PERMISSION == 0 &&
                flags and Intent.FLAG_GRANT_WRITE_URI_PERMISSION == 0
            ) {
                // redirect the nested Intent
                picFromGalleryLauncher.launch(galleryIntent)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isCameraAndGalleryPermissionGranted(requireActivity())) {
                    selectImage()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
}