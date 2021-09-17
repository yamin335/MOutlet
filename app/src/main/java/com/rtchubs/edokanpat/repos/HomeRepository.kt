package com.rtchubs.edokanpat.repos

import android.graphics.Bitmap
import com.google.gson.JsonObject
import com.rtchubs.edokanpat.api.ApiService
import com.rtchubs.edokanpat.models.AllMerchantResponse
import com.rtchubs.edokanpat.models.AllProductResponse
import com.rtchubs.edokanpat.models.AllShoppingMallResponse
import com.rtchubs.edokanpat.models.add_product.AddProductResponse
import com.rtchubs.edokanpat.models.customers.AddCustomerResponse
import com.rtchubs.edokanpat.models.customers.CustomerListResponse
import com.rtchubs.edokanpat.models.payment_account_models.AddCardOrBankResponse
import com.rtchubs.edokanpat.models.payment_account_models.BankOrCardListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
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

    suspend fun addProduct(thumbnail: Bitmap?, product_image1: Bitmap?, product_image2: Bitmap?,
                           product_image3: Bitmap?, product_image4: Bitmap?, product_image5: Bitmap?, name: String?, barcode: String?,
                           description: String?, buying_price: String?, selling_price: String?, mrp: String?,
                           expired_date: String?, category_id: Int?, merchant_id: Int?, token: String?): Response<AddProductResponse> {

        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM).apply {
            addFormDataPart("name", name ?: "")
            addFormDataPart("barcode", barcode ?: "")
            addFormDataPart("description", description ?: "")
            addFormDataPart("buying_price", buying_price ?: "")
            addFormDataPart("selling_price", selling_price ?: "")
            addFormDataPart("mrp", mrp ?: "")
            addFormDataPart("expired_date", expired_date ?: "")
            addFormDataPart("category_id", category_id?.toString() ?: "")
            addFormDataPart("merchant_id", merchant_id?.toString() ?: "")
            addFormDataPart("token", token ?: "")
//            tileList.forEachIndexed { index, tile ->
//                val posterior = if (tile.frame == R.drawable.bold_frame) "bold" else "edge"
//                val imageFile = File(tile.resizedImagePath ?: "")
//                val fileRequestBody = imageFile.asRequestBody("multipart/form-data".toMediaTypeOrNull()) ?: return@forEachIndexed
//                addFormDataPart("files[]", "${index}_$posterior.jpg", fileRequestBody)
////                val fileRequestBody = tile.bitmap?.toFile(context, posterior)?.asRequestBody("multipart/form-data".toMediaTypeOrNull()) ?: return@forEachIndexed
////                addFormDataPart("files[]", "$index.jpg", fileRequestBody)
//            }
        }.build()

        return withContext(Dispatchers.IO) {
            apiService.addProduct(requestBody)
        }
    }
}