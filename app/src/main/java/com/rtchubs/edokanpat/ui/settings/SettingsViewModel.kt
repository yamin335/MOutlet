package com.rtchubs.edokanpat.ui.settings

import android.app.Application
import androidx.lifecycle.ViewModel
import com.rtchubs.edokanpat.prefs.PreferencesHelper
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import javax.inject.Inject


class SettingsViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel(application) {

}