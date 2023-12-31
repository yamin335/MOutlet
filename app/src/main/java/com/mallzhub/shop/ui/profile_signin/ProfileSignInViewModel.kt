package com.mallzhub.shop.ui.profile_signin

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mallzhub.shop.api.*
import com.mallzhub.shop.models.registration.DefaultResponse
import com.mallzhub.shop.repos.RegistrationRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import com.mallzhub.shop.util.AppConstants.serverConnectionErrorMessage
import com.mallzhub.shop.util.asFile
import com.mallzhub.shop.util.asFilePart
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileSignInViewModel @Inject constructor(private val application: Application, private val repository: RegistrationRepository) : BaseViewModel(application) {
    fun registerNewUser(mobileNumber: String, otp: String, password: String, fullName: String,
                        mobileOperator: String, deviceId: String,
                        deviceName: String, deviceModel: String, nidNumber: String, nidFrontImage: Uri?,
                        nidBackImage: Uri?, userImage: MultipartBody.Part?): LiveData<DefaultResponse> {
        val response: MutableLiveData<DefaultResponse> = MutableLiveData()
        if (checkNetworkStatus()) {
            val handler = CoroutineExceptionHandler { _, exception ->
                exception.printStackTrace()
                apiCallStatus.postValue(ApiCallStatus.ERROR)
                toastError.postValue(serverConnectionErrorMessage)
            }

            apiCallStatus.postValue(ApiCallStatus.LOADING)
            viewModelScope.launch(handler) {

                val frontImagePart = nidFrontImage?.asFile(application)?.asFilePart("NidImageFront")
                val backImagePart = nidBackImage?.asFile(application)?.asFilePart("NidImageBack")
                //val userImagePart = userImage?.asFile(application)?.asFilePart("UserImage")

                when (val apiResponse = ApiResponse.create(repository.registrationRepo(mobileNumber, otp, password, fullName,
                    mobileOperator, deviceId, deviceName, deviceModel, nidNumber, frontImagePart, backImagePart, userImage))) {
                    is ApiSuccessResponse -> {
                        response.postValue(apiResponse.body)
                        apiCallStatus.postValue(ApiCallStatus.SUCCESS)
                    }
                    is ApiEmptyResponse -> {
                        apiCallStatus.postValue(ApiCallStatus.EMPTY)
                    }
                    is ApiErrorResponse -> {
                        response.postValue(Gson().fromJson(apiResponse.errorMessage, DefaultResponse::class.java))
                        apiCallStatus.postValue(ApiCallStatus.ERROR)
                    }
                }
            }
        }

        return response
    }
}