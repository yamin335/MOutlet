package com.mallzhub.shop.ui.common

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mallzhub.shop.R
import com.mallzhub.shop.util.NetworkUtils
import com.mallzhub.shop.util.showErrorToast

abstract class BaseViewModel constructor(val context: Application) : ViewModel() {

    val apiCallStatus: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val toastError = MutableLiveData<String>()
    val toastWarning = MutableLiveData<String>()
    val toastSuccess = MutableLiveData<String>()
    val popBackStack = MutableLiveData<Boolean>()

    fun checkNetworkStatus() = if (NetworkUtils.isNetworkConnected(context)) {
        true
    } else {
        showErrorToast(context, context.getString(R.string.internet_error_msg))
        false
    }

    fun onAppExit(preferences: SharedPreferences) {
        preferences.edit().apply {
            putString("LoggedUserPassword",null)
            putString("LoggedUserID", null)
            putBoolean("goToLogin", false)
            apply()
        }
    }

    fun onLogOut(preferences: SharedPreferences) {
        preferences.edit().apply {
            putString("LoggedUserPassword",null)
            putString("LoggedUserID", null)
            putBoolean("goToLogin", true)
            apply()
        }
    }
}