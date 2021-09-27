package com.rtchubs.edokanpat.ui.customers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.rtchubs.edokanpat.AppExecutors
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.databinding.CustomerListItemBinding
import com.rtchubs.edokanpat.models.customers.Customer
import com.rtchubs.edokanpat.util.DataBoundListAdapter
import com.rtchubs.edokanpat.util.DataBoundViewHolder
import java.util.*
import kotlin.collections.ArrayList

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

    }), Filterable {

    private var dataList: ArrayList<Customer> = ArrayList()
    private var filteredDataList: ArrayList<Customer> = ArrayList()

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

    fun submitDataList(dataList: List<Customer>) {
        this.dataList = dataList as ArrayList<Customer>
        this.filteredDataList = dataList
        submitList(dataList)
    }

    override fun getItemCount(): Int {
        return filteredDataList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                var filteredList: MutableList<Customer> = ArrayList()
                if (charString.isEmpty()) {
                    filteredList = dataList
                } else {
                    for (row in dataList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name?.toLowerCase(Locale.ROOT)?.contains(charSequence) == true) {
                            filteredList.add(row)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filterResults.values?.let {
                    filteredDataList = (it as? ArrayList<*>)?.filterIsInstance<Customer>() as ArrayList<Customer>? ?: ArrayList()
                    submitList(filteredDataList)
                }
            }
        }
    }
}