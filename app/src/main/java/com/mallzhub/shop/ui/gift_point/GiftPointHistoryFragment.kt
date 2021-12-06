package com.mallzhub.shop.ui.gift_point

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.GiftPointHistoryFragmentBinding
import com.mallzhub.shop.models.GiftPointRewards
import com.mallzhub.shop.ui.common.BaseFragment

class GiftPointHistoryFragment : BaseFragment<GiftPointHistoryFragmentBinding, GiftPointHistoryViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_gift_point_history
    override val viewModel: GiftPointHistoryViewModel by viewModels {
        viewModelFactory
    }

    lateinit var pointHistoryListAdapter: GiftPointsListAdapter

    override fun onResume() {
        super.onResume()
        viewModel.getGiftPointsHistory(preferencesHelper.merchantId)
        visibleGoneEmptyView()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setHasOptionsMenu(true)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            android.R.id.home -> {
//                navController.navigateUp()
//            }
//        }
//        return true
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        pointHistoryListAdapter = GiftPointsListAdapter(appExecutors) {
            navigateTo(GiftPointHistoryFragmentDirections.actionGiftPointHistoryFragmentToGiftPointHistoryDetailsFragment(it))
        }

        viewDataBinding.historyRecycler.adapter = pointHistoryListAdapter

        viewModel.giftPointsHistoryList.observe(viewLifecycleOwner, Observer {
            giftPointHistoryList = it as ArrayList<GiftPointRewards>
            pointHistoryListAdapter.submitList(giftPointHistoryList)
            visibleGoneEmptyView()
        })
    }

    private fun visibleGoneEmptyView() {
        if (giftPointHistoryList.isEmpty()) {
            viewDataBinding.container.visibility = View.GONE
            viewDataBinding.emptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.container.visibility = View.VISIBLE
            viewDataBinding.emptyView.visibility = View.GONE
        }
    }

    companion object {
        var giftPointHistoryList = ArrayList<GiftPointRewards>()
    }
}