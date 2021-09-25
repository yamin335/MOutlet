package com.rtchubs.edokanpat.ui.myAccount

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.databinding.MyAccountsFragmentBinding
import com.rtchubs.edokanpat.databinding.SettingsFragmentBinding
import com.rtchubs.edokanpat.ui.common.BaseFragment

class MyAccountsFragment : BaseFragment<MyAccountsFragmentBinding, MyAccountViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_my_accounts
    override val viewModel: MyAccountViewModel by viewModels {
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