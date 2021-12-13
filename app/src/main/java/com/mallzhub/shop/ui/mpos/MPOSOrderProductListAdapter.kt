package com.mallzhub.shop.ui.mpos

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.mallzhub.shop.AppExecutors
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.MPOSOrderProductListItemBinding
import com.mallzhub.shop.models.MPOSOrderProduct
import com.mallzhub.shop.util.DataBoundListAdapter

class MPOSOrderProductListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((MPOSOrderProduct) -> Unit)? = null
) : DataBoundListAdapter<MPOSOrderProduct, MPOSOrderProductListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<MPOSOrderProduct>() {
        override fun areItemsTheSame(oldItem: MPOSOrderProduct, newItem: MPOSOrderProduct): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MPOSOrderProduct,
            newItem: MPOSOrderProduct
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): MPOSOrderProductListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_mpos_order_product, parent, false
        )
    }


    override fun bind(binding: MPOSOrderProductListItemBinding, position: Int) {
        val item = getItem(position)
        binding.productName = item.product?.name
        binding.productDescription = item.product?.description
        binding.productPrice = item.product_detail?.selling_price?.toString() ?: ""
        binding.imageUrl = item.product?.thumbnail
        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                binding.thumbnail.setImageResource(R.drawable.image_placeholder)
                return true
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                return false
            }
        }

        binding.remove.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}