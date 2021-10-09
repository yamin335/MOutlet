package com.mallzhub.shop.repos

import com.mallzhub.shop.api.ApiService
import com.mallzhub.shop.models.order.OrderListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getOrderList(page: Int?, token: String?): Response<OrderListResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getOrderList(page, token)
        }
    }
}