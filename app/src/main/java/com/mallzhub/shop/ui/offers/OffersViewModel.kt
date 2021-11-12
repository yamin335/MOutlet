package com.mallzhub.shop.ui.offers

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mallzhub.shop.api.*
import com.mallzhub.shop.local_db.dao.CartDao
import com.mallzhub.shop.models.AllProductResponse
import com.mallzhub.shop.models.OfferProductListResponseData
import com.mallzhub.shop.models.Product
import com.mallzhub.shop.models.order.OrderStoreBody
import com.mallzhub.shop.models.order.OrderStoreResponse
import com.mallzhub.shop.repos.HomeRepository
import com.mallzhub.shop.repos.OfferRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class OffersViewModel @Inject constructor(
    private val application: Application,
    private val cartDao: CartDao,
    private val offerRepository: OfferRepository,
    private val homeRepository: HomeRepository
) : BaseViewModel(application) {
    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }

    val offerProductList: MutableLiveData<List<OfferProductListResponseData>> by lazy {
        MutableLiveData<List<OfferProductListResponseData>>()
    }

    fun getProductDetails(id: Int?): LiveData<Product> {
        val productDetails: MutableLiveData<Product> = MutableLiveData()
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(homeRepository.getProductDetailsRepo(id))) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        productDetails.postValue(apiResponse.body.data)
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
        return productDetails
    }

    fun getAllOfferList() {
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(AppConstants.serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {
                when (val apiResponse = ApiResponse.create(offerRepository.getOfferList())) {
                    is ApiSuccessResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                        offerProductList.postValue(apiResponse.body.data)
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