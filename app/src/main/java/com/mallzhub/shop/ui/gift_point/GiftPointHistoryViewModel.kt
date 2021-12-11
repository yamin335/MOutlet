package com.mallzhub.shop.ui.gift_point

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.ShopWiseGiftPointRewards
import com.mallzhub.shop.repos.GiftPointRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class GiftPointHistoryViewModel @Inject constructor(
    private val application: Application,
    private val giftPointRepository: GiftPointRepository
) : BaseViewModel(application) {

    val giftPointsHistoryList: MutableLiveData<List<ShopWiseGiftPointRewards>> by lazy {
        MutableLiveData<List<ShopWiseGiftPointRewards>>()
    }

    fun getShopWiseGiftPoints(customerId: Int) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(giftPointRepository.getGiftPointHistory(customerId))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        giftPointsHistoryList.postValue(apiResponse.body.data?.rewards)
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.EMPTY)
                    }
                    is ApiErrorResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.ERROR)
                    }
                }
            }
        }
    }
}