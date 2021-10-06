package com.mallzhub.shop.ui.order

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mallzhub.shop.AppExecutors
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.OrderListItemBinding
import com.mallzhub.shop.models.order.Order
import com.mallzhub.shop.util.AppConstants.orderCancelled
import com.mallzhub.shop.util.AppConstants.orderDelivered
import com.mallzhub.shop.util.AppConstants.orderPicked
import com.mallzhub.shop.util.AppConstants.orderProcessing
import com.mallzhub.shop.util.AppConstants.orderShipped
import com.mallzhub.shop.util.DataBoundListAdapter

class OrderListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((Order) -> Unit)? = null
) : DataBoundListAdapter<Order, OrderListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Order,
            newItem: Order
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): OrderListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_order, parent, false
        )
    }


    override fun bind(binding: OrderListItemBinding, position: Int) {
        val item = getItem(position)
        binding.item = item
        val context = binding.root.context

        when(item.status) {
            orderProcessing -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_processing)
                binding.status.setTextColor(context.getColor(R.color.orderProcessing))
            }
            orderPicked -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_picked)
                binding.status.setTextColor(context.getColor(R.color.orderPicked))
            }
            orderShipped -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_shipped)
                binding.status.setTextColor(context.getColor(R.color.orderShipped))
            }
            orderDelivered -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_delivered)
                binding.status.setTextColor(context.getColor(R.color.orderDelivered))
            }
            orderCancelled -> {
                binding.status.background = ContextCompat.getDrawable(context, R.drawable.status_order_cancelled)
                binding.status.setTextColor(context.getColor(R.color.orderCancelled))
            }
        }

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}