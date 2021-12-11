package com.mallzhub.shop.ui.mpos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mallzhub.shop.AppExecutors
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.MPOSOrderListItemBinding
import com.mallzhub.shop.models.MPOSOrder
import com.mallzhub.shop.util.DataBoundListAdapter

class MPOSOrderListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((MPOSOrder) -> Unit)? = null
) : DataBoundListAdapter<MPOSOrder, MPOSOrderListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<MPOSOrder>() {
        override fun areItemsTheSame(oldItem: MPOSOrder, newItem: MPOSOrder): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: MPOSOrder,
            newItem: MPOSOrder
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): MPOSOrderListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_mpos_orders, parent, false
        )
    }


    override fun bind(binding: MPOSOrderListItemBinding, position: Int) {
        val item = getItem(position)
        binding.invoice = item.invoice ?: "Undefined Invoice"
        binding.date = item.date ?: "No date found"
        binding.subTotal = "${binding.root.context.getString(R.string.sign_taka)} ${item.total}"

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}