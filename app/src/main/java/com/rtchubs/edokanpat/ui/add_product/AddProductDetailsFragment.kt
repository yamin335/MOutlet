package com.rtchubs.edokanpat.ui.add_product

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
import com.rtchubs.edokanpat.databinding.AddProductDetailsFragmentBinding
import com.rtchubs.edokanpat.databinding.AddProductFragmentBinding
import com.rtchubs.edokanpat.models.add_product.ItemAddProduct
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.util.BitmapUtilss
import com.rtchubs.edokanpat.util.PermissionUtils.isCameraAndGalleryPermissionGranted
import com.rtchubs.edokanpat.util.PermissionUtils.isCameraPermission
import com.rtchubs.edokanpat.util.PermissionUtils.isGalleryPermission
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val FEATURE_IMAGE = 1
private const val SAMPLE_IMAGE = 2
class AddProductDetailsFragment : BaseFragment<AddProductDetailsFragmentBinding, AddProductDetailsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_add_product_details
    override val viewModel: AddProductDetailsViewModel by viewModels {
        viewModelFactory
    }

    var placeholder: Drawable? = null

    lateinit var sampleImageAdapter: ProductDetailsSampleImageListAdapter
    lateinit var product: ItemAddProduct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        placeholder = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.image_placeholder
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        product = AllProductsFragment.allProductsList[productId]

        sampleImageAdapter = ProductDetailsSampleImageListAdapter()
        viewDataBinding.sampleImageRecycler.adapter = sampleImageAdapter

        viewDataBinding.featureImage.setImageBitmap(product.featureImage)
        if (!product.sampleImages.isNullOrEmpty()) {
            sampleImageAdapter.submitImageList(product.sampleImages)
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

    companion object {
        var productId: Int = 0
    }
}