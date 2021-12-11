package com.mallzhub.shop.ui.gift_point

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.GiftPointsHistoryDetailsResponseData
import com.mallzhub.shop.repos.GiftPointRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class GiftPointHistoryDetailsViewModel @Inject constructor(
    private val application: Application,
    private val giftPointRepository: GiftPointRepository
) : BaseViewModel(application) {

    val giftPointsHistoryDetailsResponse: MutableLiveData<GiftPointsHistoryDetailsResponseData> by lazy {
        MutableLiveData<GiftPointsHistoryDetailsResponseData>()
    }

    fun getGiftPointsHistoryDetails(customerId: Int, merchantId: Int) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(giftPointRepository.getGiftPointHistoryDetails(customerId, merchantId))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        giftPointsHistoryDetailsResponse.postValue(apiResponse.body.data)
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