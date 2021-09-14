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

class AddCustomerViewModel @Inject constructor(
    private val application: Application,
    private val repository: HomeRepository
) : BaseViewModel(application) {

    val customer: MutableLiveData<Customer> by lazy {
        MutableLiveData<Customer>()
    }

    val name: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val phone: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val contactPerson: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val discountAmount: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val city: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val state: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val address: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun addNewCustomer(zipcode: String, merchant_id: Int) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(repository.addCustomer(name.value ?: "", phone.value ?: "",
                    email.value ?: "", contactPerson.value ?: "",
                    discountAmount.value ?: "", city.value ?: "", state.value ?: "", zipcode,
                    address.value ?: "", merchant_id))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        customer.postValue(apiResponse.body.data?.customer)
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