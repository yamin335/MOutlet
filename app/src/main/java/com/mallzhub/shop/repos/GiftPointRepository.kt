package com.mallzhub.shop.repos

import com.google.gson.JsonObject
import com.mallzhub.shop.api.ApiService
import com.mallzhub.shop.models.GiftPointHistoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GiftPointRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getGiftPointsHistory(merchantId: Int): Response<GiftPointHistoryResponse> {
        val jsonObject = JsonObject().apply {
            addProperty("merchant_id", merchantId)
        }
        return withContext(Dispatchers.IO) {
            apiService.getGiftPointsHistory(1, jsonObject)
        }
    }
}