package com.rtchubs.edokanpat.ui.add_product

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.edokanpat.api.*
import com.rtchubs.edokanpat.models.AllProductResponse
import com.rtchubs.edokanpat.models.add_product.AddProductResponse
import com.rtchubs.edokanpat.repos.HomeRepository
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import com.rtchubs.edokanpat.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddProductViewModel @Inject constructor(
    private val application: Application,
    private val homeRepository: HomeRepository
) : BaseViewModel(application) {
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

    fun addProduct(categoryId: Int, merchantId: Int, token: String?) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(homeRepository.addProduct(null, null,
                    null, null, null, null, name.value, "",
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