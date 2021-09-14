package com.rtchubs.edokanpat.ui.customers

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
import com.rtchubs.edokanpat.databinding.CustomerListItemBinding
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.models.add_product.ItemAddProduct
import com.rtchubs.edokanpat.models.customers.Customer
import com.rtchubs.edokanpat.util.DataBoundListAdapter

class AllCustomersListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((Customer) -> Unit)? = null
) : DataBoundListAdapter<Customer, CustomerListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Customer,
            newItem: Customer
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): CustomerListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_customer, parent, false
        )
    }


    override fun bind(binding: CustomerListItemBinding, position: Int) {
        val item = getItem(position)
        binding.item = item

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}