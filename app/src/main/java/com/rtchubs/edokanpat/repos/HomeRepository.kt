package com.rtchubs.edokanpat.repos

import com.google.gson.JsonObject
import com.rtchubs.edokanpat.api.ApiService
import com.rtchubs.edokanpat.models.AllMerchantResponse
import com.rtchubs.edokanpat.models.AllProductResponse
import com.rtchubs.edokanpat.models.AllShoppingMallResponse
import com.rtchubs.edokanpat.models.customers.AddCustomerResponse
import com.rtchubs.edokanpat.models.customers.CustomerListResponse
import com.rtchubs.edokanpat.models.payment_account_models.AddCardOrBankResponse
import com.rtchubs.edokanpat.models.payment_account_models.BankOrCardListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun requestBankListRepo(type:String,token:String): Response<BankOrCardListResponse> {
        return withContext(Dispatchers.IO) {
            apiService.requestBankList(type,token)
        }
    }

    suspend fun addBankRepo(bankId: Int, accountNumber: String, token: String): Response<AddCardOrBankResponse> {
        val jsonObjectBody = JsonObject().apply {
            addProperty("bankId", bankId)
            addProperty("accountNumber", accountNumber)
        }

        return withContext(Dispatchers.IO) {
            apiService.addBankAccount(jsonObjectBody, token)
        }
    }

    suspend fun addCardRepo(bankId: Int, cardNumber: String, expireMonth: Int, expireYear: Int, token: String): Response<AddCardOrBankResponse> {
        val jsonObjectBody = JsonObject().apply {
            addProperty("bankId", bankId)
            addProperty("cardNumber", cardNumber)
            addProperty("expireMonth", expireMonth)
            addProperty("expireYear", expireYear)
        }

        return withContext(Dispatchers.IO) {
            apiService.addCardAccount(jsonObjectBody, token)
        }
    }

    // eDokanPat
    suspend fun getAllMallsRepo(): Response<AllShoppingMallResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getAllMalls()
        }
    }

    suspend fun getAllMerchantsRepo(): Response<AllMerchantResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getAllMerchants()
        }
    }

    suspend fun getAllProductsRepo(id: String): Response<AllProductResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getAllProducts(id)
        }
    }

    suspend fun allCustomers(token: String): Response<CustomerListResponse> {
        val jsonObjectBody = JsonObject().apply {
            addProperty("token", token)
        }

        return withContext(Dispatchers.IO) {
            apiService.allCustomers(jsonObjectBody, 1)
        }
    }

    suspend fun addCustomer(name: String, phone: String, email: String, contact_person: String,
                            discountAmount: String, city: String, state: String, zipcode: String,
                            address: String, merchant_id: Int): Response<AddCustomerResponse> {
        val jsonObjectBody = JsonObject().apply {
            addProperty("name", name)
            addProperty("phone", phone)
            addProperty("email", email)
            addProperty("contact_person", contact_person)
            addProperty("discountAmount", discountAmount)
            addProperty("city", city)
            addProperty("state", state)
            addProperty("zipcode", zipcode)
            addProperty("address", address)
            addProperty("merchant_id", merchant_id)
            addProperty("token", email)
        }

        return withContext(Dispatchers.IO) {
            apiService.addCustomer(jsonObjectBody)
        }
    }
}