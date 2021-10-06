package com.mallzhub.shop.prefs

import com.mallzhub.shop.api.TokenInformation
import com.mallzhub.shop.models.login.Merchant


interface PreferencesHelper {

    var test: String?

    var isRegistered: Boolean

    var isTermsAccepted: Boolean

    var pinNumber: String?

    var mobileNo: String?

    var operator: String?

    var deviceId: String?

    var deviceName: String?

    var deviceModel: String?

    var isLoggedIn: Boolean

    var accessToken: String?

    var refreshToken: String?

    var phoneNumber: String?

    var merchantId: Int

    var userRole: String?

    var accessTokenExpiresIn: Long

    val isAccessTokenExpired: Boolean

    fun getAccessTokenHeader(): String

    fun getAuthHeader(token: String?): String

    fun logoutUser()

    fun saveToken(tokenInformation: TokenInformation)

    fun saveMerchant(merchant: Merchant)

    fun getMerchant(): Merchant

    fun getToken(): TokenInformation

    var validityLimiterMap: MutableMap<String, Long>?

    var language: String?
}
