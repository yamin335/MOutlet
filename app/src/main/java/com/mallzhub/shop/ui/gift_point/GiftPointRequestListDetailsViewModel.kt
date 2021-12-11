package com.mallzhub.shop.ui.gift_point

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.GiftPointRewards
import com.mallzhub.shop.repos.GiftPointRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class GiftPointRequestListDetailsViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel(application) {

}