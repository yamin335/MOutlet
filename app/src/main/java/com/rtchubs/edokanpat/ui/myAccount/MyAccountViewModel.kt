package com.rtchubs.edokanpat.ui.myAccount

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.edokanpat.api.*
import com.rtchubs.edokanpat.models.AllProductResponse
import com.rtchubs.edokanpat.models.add_product.AddProductResponse
import com.rtchubs.edokanpat.repos.HomeRepository
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import com.rtchubs.edokanpat.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyAccountViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel(application) {

}