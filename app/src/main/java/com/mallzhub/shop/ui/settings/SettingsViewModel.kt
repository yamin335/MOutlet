package com.mallzhub.shop.ui.settings

import android.app.Application
import com.mallzhub.shop.ui.common.BaseViewModel
import javax.inject.Inject


class SettingsViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel(application) {

}