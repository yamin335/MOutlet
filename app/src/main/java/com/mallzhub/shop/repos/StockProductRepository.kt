package com.mallzhub.shop.repos

import com.google.gson.JsonObject
import com.mallzhub.shop.api.ApiService
import com.mallzhub.shop.models.GiftPointRequestListResponse
import com.mallzhub.shop.models.GiftPointsHistoryDetailsResponse
import com.mallzhub.shop.models.ShopWiseGiftPointResponse
import com.mallzhub.shop.models.product_stock.StockProductsDetails
import com.mallzhub.shop.models.product_stock.StockProductsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockProductRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getStockProduct(token: String): Response<StockProductsResponse> {
        val jsonObject = JsonObject().apply {
            addProperty("token", token)
        }
        return withContext(Dispatchers.IO) {
            apiService.getStockProduct("1", jsonObject)
        }
    }

    suspend fun getStockProductDetails(product_detail_id: Int): Response<List<StockProductsDetails>> {
        val jsonObject = JsonObject().apply {
            addProperty("product_detail_id", product_detail_id)
        }
        return withContext(Dispatchers.IO) {
            apiService.getStockProductDetails("all", jsonObject)
        }
    }
}