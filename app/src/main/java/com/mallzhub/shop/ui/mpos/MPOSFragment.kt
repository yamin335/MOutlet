package com.mallzhub.shop.ui.mpos

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.AllProductsFragmentBinding
import com.mallzhub.shop.databinding.MPOSFragmentBinding
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.ui.NavDrawerHandlerCallback
import com.mallzhub.shop.ui.common.BaseFragment

class MPOSFragment : BaseFragment<MPOSFragmentBinding, MPOSViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_mpos
    override val viewModel: MPOSViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun showHideDataView() {
        if (allProductsList.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

    companion object {
        var allProductsList: List<Product> = ArrayList()
        var id = 0
    }
}