package com.mallzhub.shop.ui.home

import androidx.fragment.app.viewModels
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.SetAFragmentBinding
import com.mallzhub.shop.ui.common.BaseFragment

class SetAFragment : BaseFragment<SetAFragmentBinding, SetAViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_set_a

    override val viewModel: SetAViewModel by viewModels { viewModelFactory }
}