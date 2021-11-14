package com.mallzhub.shop.api

import com.google.gson.JsonObject
import com.mallzhub.shop.api.Api.ContentType
import com.mallzhub.shop.models.*
import com.mallzhub.shop.models.add_product.AddProductResponse
import com.mallzhub.shop.models.common.MyAccountListResponse
import com.mallzhub.shop.models.customers.AddCustomerResponse
import com.mallzhub.shop.models.customers.CustomerListResponse
import com.mallzhub.shop.models.login.LoginResponse
import com.mallzhub.shop.models.order.OrderListResponse
import com.mallzhub.shop.models.order.OrderStoreResponse
import com.mallzhub.shop.models.payment_account_models.AddCardOrBankResponse
import com.mallzhub.shop.models.payment_account_models.BankOrCardListResponse
import com.mallzhub.shop.models.registration.InquiryResponse
import com.mallzhub.shop.models.registration.DefaultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * REST API access points
 */
interface ApiService {

    @Multipart
    @POST(ApiEndPoint.INQUIRE)
    suspend fun inquire(@Part("PhoneNumber") mobileNumber: RequestBody, @Part("DeviceId") deviceId: RequestBody): Response<InquiryResponse>

    @Multipart
    @POST(ApiEndPoint.REQUESTOTP)
    suspend fun requestOTP(
        @Part("PhoneNumber") mobileNumber: RequestBody,
        @Part("HasGivenConsent") hasGivenConsent: RequestBody
    ): Response<DefaultResponse>

    @Multipart
    @POST(ApiEndPoint.REGISTRATION)
    suspend fun registration(
        @Part("MobileNumber") mobileNumber: RequestBody,
        @Part("Otp") otp: RequestBody,
        @Part("Pin") password: RequestBody,
        @Part("FullName") fullName: RequestBody,
        @Part("MobileOperator") mobileOperator: RequestBody,
        @Part("DeviceId") deviceId: RequestBody,
        @Part("DeviceName") deviceName: RequestBody,
        @Part("DeviceModel") deviceModel: RequestBody,
        @Part userImage: MultipartBody.Part?,
        @Part("NidNumber") nidNumber: RequestBody,
        @Part nidFrontImage: MultipartBody.Part?,
        @Part nidBackImage: MultipartBody.Part?
    ): Response<DefaultResponse>


    @FormUrlEncoded
    @POST(ApiEndPoint.CONNECT_TOKEN)
    suspend fun connectToken(
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String,
        @Field("scope") scope: String,
        @Field("device_id") deviceID: String,
        @Field("device_name") deviceName: String,
        @Field("device_model") deviceModel: String,
        @Field("client_id") clientID: String,
        @Field("client_secret") clientSecret: String,
        @Field("otp") otp: String
    ): Response<String>


    @GET(ApiEndPoint.GET_BANK_LIST)
    suspend fun requestBankList(
        @Query("type") type: String,
        @Header("Authorization") token: String
    ): Response<BankOrCardListResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.ADD_CARD)
    suspend fun addBankAccount(
        @Body jsonObject: JsonObject,
        @Header("Authorization") token: String
    ): Response<AddCardOrBankResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.ADD_BANK)
    suspend fun addCardAccount(
        @Body jsonObject: JsonObject,
        @Header("Authorization") token: String
    ): Response<AddCardOrBankResponse>

    @GET(ApiEndPoint.MY_ACCOUNT_LIST)
    suspend fun myAccountList(
        @Header("Authorization") token: String
    ): Response<MyAccountListResponse>


    // eDokanPat
    @Headers(ContentType)
    @POST(ApiEndPoint.LOGIN)
    suspend fun shopLogin(
        @Body jsonObject: JsonObject
    ): Response<LoginResponse>

    @GET(ApiEndPoint.ALL_MALL)
    suspend fun getAllMalls(): Response<AllShoppingMallResponse>

    @GET(ApiEndPoint.ALL_MERCHANTS)
    suspend fun getAllMerchants(): Response<AllMerchantResponse>

    @GET(ApiEndPoint.MERCHANT_PRODUCTS)
    suspend fun getAllProducts(
        @Path("id") type: String
    ): Response<AllProductResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.CUSTOMERS)
    suspend fun allCustomers(
        @Body jsonObject: JsonObject,
        @Query("page") page: Int
    ): Response<CustomerListResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.ADD_CUSTOMER)
    suspend fun addCustomer(
        @Body jsonObject: JsonObject
    ): Response<AddCustomerResponse>

    @POST(ApiEndPoint.ADD_PRODUCT)
    suspend fun addProduct(@Body partFormData: RequestBody): Response<AddProductResponse>

    @GET(ApiEndPoint.ORDER_LIST)
    suspend fun getOrderList(
        @Query("page") page: Int?,
        @Query("token") token: String?): Response<OrderListResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.SALE)
    suspend fun placeOrder(
        @Body jsonObject: JsonObject
    ): Response<OrderStoreResponse>

    @POST(ApiEndPoint.OFFER_LIST)
    suspend fun getOfferList(
        @Query("page") page: Int?,
        @Query("token") token: String?): Response<OfferProductListResponse>

    @GET(ApiEndPoint.PRODUCT_DETAILS)
    suspend fun getProductDetails(
        @Path("id") type: Int?
    ): Response<ProductDetailsResponse>

    @Headers(ContentType)
    @POST(ApiEndPoint.ADD_OFFER)
    suspend fun addNewOffer(
        @Body jsonObject: JsonObject
    ): Response<OfferAddResponse>

}
