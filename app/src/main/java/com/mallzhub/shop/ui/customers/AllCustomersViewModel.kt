package com.mallzhub.shop.ui.customers

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.customers.Customer
import com.mallzhub.shop.repos.HomeRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllCustomersViewModel @Inject constructor(
    private val application: Application,
    private val repository: HomeRepository
) : BaseViewModel(application) {

    val searchValue: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val customerList: MutableLiveData<List<Customer>> by lazy {
        MutableLiveData<List<Customer>>()
    }

    fun getCustomers(token: String) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(repository.allCustomers(token))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        customerList.postValue(apiResponse.body.data?.customer?.data)
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