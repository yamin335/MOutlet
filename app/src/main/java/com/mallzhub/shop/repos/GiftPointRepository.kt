package com.mallzhub.shop.repos

import com.google.gson.JsonObject
import com.mallzhub.shop.api.ApiService
import com.mallzhub.shop.models.GiftPointRequestListResponse
import com.mallzhub.shop.models.GiftPointsHistoryDetailsResponse
import com.mallzhub.shop.models.ShopWiseGiftPointResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiftPointRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getGiftPointRequestList(merchantId: Int): Response<GiftPointRequestListResponse> {
        val jsonObject = JsonObject().apply {
            addProperty("merchant_id", merchantId)
        }
        return withContext(Dispatchers.IO) {
            apiService.getGiftPointRequestList(1, jsonObject)
        }
    }

    suspend fun getGiftPointHistory(customerId: Int): Response<ShopWiseGiftPointResponse> {
        val jsonObject = JsonObject().apply {
            addProperty("customer_id", customerId)
        }
        return withContext(Dispatchers.IO) {
            apiService.getGiftPointHistory(1, jsonObject)
        }
    }

    suspend fun getGiftPointHistoryDetails(customerId: Int, merchantId: Int): Response<GiftPointsHistoryDetailsResponse> {
        val jsonObject = JsonObject().apply {
            addProperty("customer_id", customerId)
            addProperty("merchant_id", merchantId)
        }
        return withContext(Dispatchers.IO) {
            apiService.getGiftPointHistoryDetails(1, jsonObject)
        }
    }
}