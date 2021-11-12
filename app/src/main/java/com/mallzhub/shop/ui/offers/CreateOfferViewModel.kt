package com.mallzhub.shop.ui.offers

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.OfferAddResponse
import com.mallzhub.shop.models.OfferStoreBody
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.models.add_product.AddProductResponse
import com.mallzhub.shop.models.customers.Customer
import com.mallzhub.shop.models.order.OrderStoreBody
import com.mallzhub.shop.models.order.OrderStoreResponse
import com.mallzhub.shop.repos.HomeRepository
import com.mallzhub.shop.repos.OfferRepository
import com.mallzhub.shop.repos.OrderRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.security.SecureRandom
import javax.inject.Inject

class CreateOfferViewModel @Inject constructor(
    private val application: Application,
    private val offerRepository: OfferRepository
) : BaseViewModel(application) {

    val offerNote: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val offerPercent: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val offerItems: MutableLiveData<MutableList<Product>> by lazy {
        MutableLiveData<MutableList<Product>>()
    }

    val newOfferResponse: MutableLiveData<OfferAddResponse> by lazy {
        MutableLiveData<OfferAddResponse>()
    }

    fun addNewOffer(offerStoreBody: OfferStoreBody) {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(offerRepository.addNewOffer(offerStoreBody))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        newOfferResponse.postValue(apiResponse.body)
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