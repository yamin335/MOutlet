package com.mallzhub.shop.ui.add_product

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.AllProductResponse
import com.mallzhub.shop.repos.HomeRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllProductViewModel @Inject constructor(
    private val application: Application,
    private val repository: HomeRepository
) : BaseViewModel(application) {

    val productListResponse: MutableLiveData<AllProductResponse> by lazy {
        MutableLiveData<AllProductResponse>()
    }

    fun getProductList(merchantID: String) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(repository.getAllProductsRepo(id = merchantID))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        productListResponse.postValue(apiResponse.body)
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