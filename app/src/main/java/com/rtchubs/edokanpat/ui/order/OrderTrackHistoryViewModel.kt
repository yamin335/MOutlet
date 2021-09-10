package com.rtchubs.edokanpat.ui.order

import android.app.Application
import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rtchubs.edokanpat.api.*
import com.rtchubs.edokanpat.local_db.dao.CartDao
import com.rtchubs.edokanpat.local_db.dao.FavoriteDao
import com.rtchubs.edokanpat.local_db.dbo.CartItem
import com.rtchubs.edokanpat.models.AllProductResponse
import com.rtchubs.edokanpat.models.Product
import com.rtchubs.edokanpat.repos.HomeRepository
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import com.rtchubs.edokanpat.util.AppConstants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderTrackHistoryViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel(application) {


}