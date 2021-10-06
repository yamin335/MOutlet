package com.mallzhub.shop.ui.home

import androidx.fragment.app.viewModels
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.SetCFragmentBinding
import com.mallzhub.shop.ui.common.BaseFragment

class SetCFragment : BaseFragment<SetCFragmentBinding, SetCViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_c

    override val viewModel: SetCViewModel by viewModels { viewModelFactory }
}