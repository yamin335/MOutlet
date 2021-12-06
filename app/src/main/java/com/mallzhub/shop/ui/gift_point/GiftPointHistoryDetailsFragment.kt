package com.mallzhub.shop.ui.gift_point

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.GiftPointHistoryDetailsFragmentBinding
import com.mallzhub.shop.databinding.GiftPointHistoryFragmentBinding
import com.mallzhub.shop.models.GiftPointRewards
import com.mallzhub.shop.ui.common.BaseFragment

class GiftPointHistoryDetailsFragment : BaseFragment<GiftPointHistoryDetailsFragmentBinding, GiftPointHistoryDetailsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_gift_point_history_details
    override val viewModel: GiftPointHistoryDetailsViewModel by viewModels {
        viewModelFactory
    }

    val args: GiftPointHistoryDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        viewDataBinding.name = args.reward.name
        viewDataBinding.mobile = args.reward.phone
        viewDataBinding.giftPoint = args.reward.reward?.toString() ?: "0"
        viewDataBinding.remarks = args.reward.remarks
    }
}