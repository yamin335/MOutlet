package com.rtchubs.edokanpat.ui.add_product

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.rtchubs.edokanpat.AppExecutors
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.AddProductListItemBinding
import com.rtchubs.edokanpat.models.add_product.ItemAddProduct
import com.rtchubs.edokanpat.util.DataBoundListAdapter

class AllProductListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((ItemAddProduct) -> Unit)? = null
) : DataBoundListAdapter<ItemAddProduct, AddProductListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<ItemAddProduct>() {
        override fun areItemsTheSame(oldItem: ItemAddProduct, newItem: ItemAddProduct): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ItemAddProduct,
            newItem: ItemAddProduct
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
        binding.thumbnail.setImageBitmap(item.featureImage)

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}