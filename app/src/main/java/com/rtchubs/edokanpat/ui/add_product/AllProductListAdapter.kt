package com.rtchubs.edokanpat.ui.add_product

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
import com.rtchubs.edokanpat.AppExecutors
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.AddProductListItemBinding
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.models.add_product.ItemAddProduct
import com.rtchubs.edokanpat.util.DataBoundListAdapter

class AllProductListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((Product) -> Unit)? = null
) : DataBoundListAdapter<Product, AddProductListItemBinding>(
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

    override fun createBinding(parent: ViewGroup): AddProductListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_add_product, parent, false
        )
    }


    override fun bind(binding: AddProductListItemBinding, position: Int) {
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

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}