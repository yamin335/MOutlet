package com.mallzhub.shop.ui.otp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.shop.R
import com.mallzhub.shop.BR
import com.mallzhub.shop.databinding.OtpBinding
import com.mallzhub.shop.ui.common.BaseFragment

class OtpFragment  : BaseFragment<OtpBinding, OtpViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_otp
    override val viewModel: OtpViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.btnVerify.setOnClickListener {
        }
    }
}