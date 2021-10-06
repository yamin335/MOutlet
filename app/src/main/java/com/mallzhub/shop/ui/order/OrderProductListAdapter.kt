package com.mallzhub.shop.ui.order

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
import com.mallzhub.shop.databinding.OrderProductListItemBinding
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.util.DataBoundListAdapter

class OrderProductListAdapter(
    private val appExecutors: AppExecutors,
    private val cartItemActionCallback: CartItemActionCallback,
    private val itemCallback: ((Product) -> Unit)? = null
) : DataBoundListAdapter<Product, OrderProductListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    interface CartItemActionCallback {
        fun incrementCartItemQuantity(id: Int)
        fun decrementCartItemQuantity(id: Int)
    }

    override fun createBinding(parent: ViewGroup): OrderProductListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_order_product, parent, false
        )
    }


    override fun bind(binding: OrderProductListItemBinding, position: Int) {
        val item = getItem(position)
        binding.item = item
        binding.imageUrl = item.thumbnail
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

        binding.incrementQuantity.setOnClickListener {
            cartItemActionCallback.incrementCartItemQuantity(item.id)
        }
        binding.decrementQuantity.setOnClickListener {
            if (item.quantity ?: 0 > 1) {
                cartItemActionCallback.decrementCartItemQuantity(item.id)
            }
        }
    }
}