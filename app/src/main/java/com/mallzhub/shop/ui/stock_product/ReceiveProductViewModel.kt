package com.mallzhub.shop.ui.stock_product

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.models.add_product.AddProductResponse
import com.mallzhub.shop.repos.HomeRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReceiveProductViewModel @Inject constructor(
    private val application: Application,
    private val homeRepository: HomeRepository
) : BaseViewModel(application) {

    val selectedProduct: MutableLiveData<MutableList<Product>> by lazy {
        MutableLiveData<MutableList<Product>>()
    }

    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val buyingPrice: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val sellingPrice: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val mrp: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val expiredDate: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val description: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val addProductResponse: MutableLiveData<AddProductResponse> by lazy {
        MutableLiveData<AddProductResponse>()
    }

    fun addProduct(thumbnail: String?, sampleImage1: String?, sampleImage2: String?,
                   sampleImage3: String?, sampleImage4: String?, sampleImage5: String?,
                   categoryId: Int, merchantId: Int, token: String?) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(homeRepository.addProduct(thumbnail, sampleImage1,
                    sampleImage2, sampleImage3, sampleImage4, sampleImage5, name.value, "",
                    description.value, buyingPrice.value, sellingPrice.value, mrp.value, expiredDate.value,
                    categoryId, merchantId, token))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        addProductResponse.postValue(apiResponse.body)
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