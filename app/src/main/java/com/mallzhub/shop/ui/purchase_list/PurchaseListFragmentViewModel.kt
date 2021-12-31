package com.mallzhub.shop.ui.purchase_list

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.product_stock.StockProductWithDetails
import com.mallzhub.shop.models.purchase_list.PurchaseListResponseData
import com.mallzhub.shop.repos.StockProductRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class PurchaseListFragmentViewModel @Inject constructor(
    private val application: Application,
    private val stockProductRepository: StockProductRepository
) : BaseViewModel(application) {

    val purchaseList: MutableLiveData<List<PurchaseListResponseData>> by lazy {
        MutableLiveData<List<PurchaseListResponseData>>()
    }

    fun getPurchaseList(page: Int, token: String) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(stockProductRepository.getPurchaseList(page, token))) {
                    is ApiSuccessResponse -> {
                        purchaseList.postValue(apiResponse.body.data)
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