package com.mallzhub.shop.ui.topup

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.mallzhub.shop.ui.common.BaseViewModel
import javax.inject.Inject

class TopUpPinViewModel @Inject constructor(private val application: Application) : BaseViewModel(application) {

    val pin: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}