package com.rtchubs.edokanpat.ui.customers

import android.app.Application
import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.edokanpat.api.*
import com.rtchubs.edokanpat.local_db.dao.CartDao
import com.rtchubs.edokanpat.local_db.dao.FavoriteDao
import com.rtchubs.edokanpat.local_db.dbo.CartItem
import com.rtchubs.edokanpat.models.AllProductResponse
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.models.customers.Customer
import com.rtchubs.edokanpat.repos.HomeRepository
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import com.rtchubs.edokanpat.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectCustomerViewModel @Inject constructor(
    private val application: Application,
    private val repository: HomeRepository
) : BaseViewModel(application) {

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