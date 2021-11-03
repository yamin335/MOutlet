package com.mallzhub.shop.ui.offers

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.models.add_product.AddProductResponse
import com.mallzhub.shop.models.customers.Customer
import com.mallzhub.shop.models.order.OrderStoreBody
import com.mallzhub.shop.models.order.OrderStoreResponse
import com.mallzhub.shop.repos.HomeRepository
import com.mallzhub.shop.repos.OrderRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.inject.Inject

class CreateOfferViewModel @Inject constructor(
    private val application: Application,
    private val orderRepository: OrderRepository
) : BaseViewModel(application) {

    val offerNote: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val offerAmount: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val offerItems: MutableLiveData<MutableList<Product>> by lazy {
        MutableLiveData<MutableList<Product>>()
    }

    val orderPlaceResponse: MutableLiveData<OrderStoreResponse> by lazy {
        MutableLiveData<OrderStoreResponse>()
    }

    fun incrementOfferItemQuantity(id: Int) {
        val items = offerItems.value ?: mutableListOf()
        val tempItems = items.map { if (it.id == id && it.quantity != null) { it.quantity = it.quantity!! + 1 }
            it}.toMutableList()
        offerItems.postValue(tempItems)
    }

    fun decrementOfferItemQuantity(id: Int) {
        val items = offerItems.value ?: mutableListOf()
        val tempItems = items.map { if (it.id == id && it.quantity != null) { it.quantity = it.quantity!! - 1 }
            it}.toMutableList()
        offerItems.postValue(tempItems)
    }

    fun placeOrder(orderStoreBody: OrderStoreBody) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(orderRepository.placeOrder(orderStoreBody))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        orderPlaceResponse.postValue(apiResponse.body)
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