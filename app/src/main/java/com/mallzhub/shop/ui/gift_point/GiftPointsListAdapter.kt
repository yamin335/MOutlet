package com.mallzhub.shop.ui.gift_point

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mallzhub.shop.AppExecutors
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.GiftPointHistoryListItemBinding
import com.mallzhub.shop.models.GiftPointRewards
import com.mallzhub.shop.util.DataBoundListAdapter

class GiftPointsListAdapter(
    private val appExecutors: AppExecutors,
    private val itemCallback: ((GiftPointRewards) -> Unit)? = null
) : DataBoundListAdapter<GiftPointRewards, GiftPointHistoryListItemBinding>(
    appExecutors = appExecutors, diffCallback = object : DiffUtil.ItemCallback<GiftPointRewards>() {
        override fun areItemsTheSame(oldItem: GiftPointRewards, newItem: GiftPointRewards): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: GiftPointRewards,
            newItem: GiftPointRewards
        ): Boolean {
            return oldItem == newItem
        }

    }) {

    override fun createBinding(parent: ViewGroup): GiftPointHistoryListItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_gift_point_history, parent, false
        )
    }


    override fun bind(binding: GiftPointHistoryListItemBinding, position: Int) {
        val item = getItem(position)
        binding.customerName = item.name ?: "Unknown Customer"
        binding.giftPoint = item.reward?.toString() ?: "0"

        binding.root.setOnClickListener {
            itemCallback?.invoke(item)
        }
    }
}