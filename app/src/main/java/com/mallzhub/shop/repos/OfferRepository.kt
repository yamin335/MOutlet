package com.mallzhub.shop.repos

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.mallzhub.shop.api.ApiService
import com.mallzhub.shop.models.OfferAddResponse
import com.mallzhub.shop.models.OfferProductListResponse
import com.mallzhub.shop.models.OfferStoreBody
import com.mallzhub.shop.models.order.OrderStoreBody
import com.mallzhub.shop.models.order.OrderStoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfferRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getOfferList(): Response<OfferProductListResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getOfferList()
        }
    }

    suspend fun addNewOffer(offerStoreBody: OfferStoreBody): Response<OfferAddResponse> {
        val jsonString = Gson().toJson(offerStoreBody)
        val jsonObject = JsonParser().parse(jsonString).asJsonObject
        return withContext(Dispatchers.IO) {
            apiService.addNewOffer(jsonObject)
        }
    }
}