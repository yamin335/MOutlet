package com.mallzhub.shop.ui.home

import androidx.fragment.app.viewModels
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.SetBFragmentBinding
import com.mallzhub.shop.ui.common.BaseFragment

class SetBFragment : BaseFragment<SetBFragmentBinding, SetBViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_b

    override val viewModel: SetBViewModel by viewModels { viewModelFactory }
}