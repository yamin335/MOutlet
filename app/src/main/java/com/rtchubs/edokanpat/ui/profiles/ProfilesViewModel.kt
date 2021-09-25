package com.rtchubs.edokanpat.ui.profiles

import android.app.Application
import androidx.lifecycle.ViewModel
import com.rtchubs.edokanpat.prefs.PreferencesHelper
import com.rtchubs.edokanpat.ui.common.BaseViewModel
import javax.inject.Inject

class ProfilesViewModel @Inject constructor(
    private val application: Application
) : BaseViewModel(application) {

}