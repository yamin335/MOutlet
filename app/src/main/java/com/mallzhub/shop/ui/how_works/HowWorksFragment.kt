package com.mallzhub.shop.ui.how_works

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.shop.R
import com.mallzhub.shop.BR
import com.mallzhub.shop.databinding.HowWorksBinding
import com.mallzhub.shop.ui.common.BaseFragment


class HowWorksFragment : BaseFragment<HowWorksBinding, HowWorksViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_how_works
    override val viewModel: HowWorksViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.btnOnboardingStart.setOnClickListener {
        }
    }
}