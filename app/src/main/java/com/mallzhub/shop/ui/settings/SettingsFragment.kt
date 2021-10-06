package com.mallzhub.shop.ui.settings

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.mallzhub.shop.R
import com.mallzhub.shop.BR
import com.mallzhub.shop.databinding.SettingsFragmentBinding
import com.mallzhub.shop.ui.common.BaseFragment

class SettingsFragment : BaseFragment<SettingsFragmentBinding, SettingsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                navController.navigateUp()
            }
        }
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)
    }
}