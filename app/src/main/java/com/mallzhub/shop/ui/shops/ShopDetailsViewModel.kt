package com.mallzhub.shop.ui.shops

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.mallzhub.shop.local_db.dao.CartDao
import com.mallzhub.shop.repos.HomeRepository
import com.mallzhub.shop.ui.common.BaseViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ShopDetailsViewModel @Inject constructor(
    private val application: Application,
    private val repository: HomeRepository,
    private val cartDao: CartDao
) : BaseViewModel(application) {
    val cartItemCount: LiveData<Int> = liveData {
        cartDao.getCartItemsCount().collect { count ->
            emit(count)
        }
    }
}