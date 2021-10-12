package com.mallzhub.shop.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.ProductDetailsFragmentBinding
import com.mallzhub.shop.local_db.dao.CartDao
import com.mallzhub.shop.models.OrderProduct
import com.mallzhub.shop.ui.common.BaseFragment
import com.mallzhub.shop.ui.shops.ShopDetailsProductListFragment
import com.mallzhub.shop.util.showSuccessToast
import com.mallzhub.shop.util.showWarningToast
import javax.inject.Inject

class ProductDetailsFragment :
    BaseFragment<ProductDetailsFragmentBinding, ProductDetailsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_product_details
    override val viewModel: ProductDetailsViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var cart: CartDao

    val args: ProductDetailsFragmentArgs by navArgs()

    lateinit var pdImageSampleAdapter: PDImageSampleAdapter
    lateinit var pdColorChooserAdapter: PDColorChooserAdapter
    lateinit var pdSizeChooserAdapter: PDSizeChooserAdapter
    var quantity = 1
    var alreadyAddedToCart = false
    var alreadyAddedToFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        val product = args.product

        viewDataBinding.toolbar.title = product.name
        viewDataBinding.name = product.name
        viewDataBinding.price = "$${product.mrp}"

        viewModel.toastWarning.observe(viewLifecycleOwner, Observer {
            it?.let { message ->
                showWarningToast(requireContext(), message)
                viewModel.toastWarning.postValue(null)
            }
        })

        viewModel.toastSuccess.observe(viewLifecycleOwner, Observer {
            it?.let { message ->
                showSuccessToast(requireContext(), message)
                viewModel.toastSuccess.postValue(null)
            }
        })

        pdImageSampleAdapter = PDImageSampleAdapter(
            appExecutors
        ) { item ->
            viewDataBinding.imageUrl = item
        }

        viewDataBinding.rvSampleImage.adapter = pdImageSampleAdapter

        pdImageSampleAdapter.submitList(listOf(product.thumbnail, product.thumbnail, product.thumbnail, product.thumbnail, product.thumbnail))

        viewDataBinding.imageUrl = product.thumbnail
        viewDataBinding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                viewDataBinding.imageView.setImageResource(R.drawable.image_placeholder)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }

        pdColorChooserAdapter = PDColorChooserAdapter(
            appExecutors
        ) { item ->
            //viewDataBinding.imageUrl = item
        }

        viewDataBinding.rvColorChooser.adapter = pdColorChooserAdapter

        pdColorChooserAdapter.submitList(listOf("#d32f2f", "#0AB939", "#2A5D79", "#C7A90D", "#FD87A9", "#E91E63", "#D500F9"))

        //viewDataBinding.rvSizeChooser.addItemDecoration(GridRecyclerItemDecorator(4, 0, true))
        //viewDataBinding.rvSizeChooser.layoutManager = GridLayoutManager(requireContext(), 4)
        pdSizeChooserAdapter = PDSizeChooserAdapter(
            appExecutors
        ) { item ->
            //viewDataBinding.imageUrl = item
        }

        viewDataBinding.rvSizeChooser.adapter = pdSizeChooserAdapter

        pdSizeChooserAdapter.submitList(listOf("XS", "S", "M", "L", "XL", "XXL", "3XL"))

        viewDataBinding.quantity.text = quantity.toString()

        viewDataBinding.decrementQuantity.setOnClickListener {
            if (quantity > 1) --quantity
            viewDataBinding.quantity.text = quantity.toString()
        }

        viewDataBinding.incrementQuantity.setOnClickListener {
            ++quantity
            viewDataBinding.quantity.text = quantity.toString()
        }

        viewDataBinding.addToCart.setOnClickListener {
            if (alreadyAddedToCart) {
                showWarningToast(requireContext(), "Already added to cart!")
            } else {
                viewModel.addToCart(
                    OrderProduct(product.id, product.name, product.barcode,
                        product.description, product.buying_price?.toInt(), product.selling_price?.toInt(),
                        product.mrp?.toInt(), product.expired_date, product.thumbnail,
                        product.product_image1, product.product_image2,
                        product.product_image3, product.product_image4,
                        product.product_image5, product.category_id, product.merchant_id,
                        product.created_at, product.updated_at,
                        ShopDetailsProductListFragment.orderMerchant, product.category), quantity)
            }
        }

        viewDataBinding.addToFavorite.setOnClickListener {
            if (alreadyAddedToFavorite) {
                showWarningToast(requireContext(), "Already added to favorite!")
            } else {
                viewModel.addToFavorite(product)
            }
        }

//        viewModel.doesItemExistsInCart(product).observe(viewLifecycleOwner, Observer {
//            it?.let { value ->
//                alreadyAddedToCart = value
//            }
//        })
//
//        viewModel.doesItemExistsInFavorite(product).observe(viewLifecycleOwner, Observer {
//            it?.let { value ->
//                alreadyAddedToFavorite = value
//            }
//        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_details, menu)

        val menuItem = menu.findItem(R.id.menu_cart)
        val actionView = menuItem.actionView
        val badge = actionView.findViewById<TextView>(R.id.badge)
        badge.text = viewModel.cartItemCount.value?.toString()
        actionView.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }

        viewModel.cartItemCount.observe(viewLifecycleOwner, Observer {
            it?.let { value ->
                if (value < 1) {
                    badge.visibility = View.INVISIBLE
                    return@Observer
                } else {
                    badge.visibility = View.VISIBLE
                    badge.text = value.toString()
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }

            R.id.menu_cart -> {
                navController.navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentToCartFragment())
            }
        }

        return true
    }
}