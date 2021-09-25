package com.rtchubs.edokanpat.ui.transactions

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.databinding.MyAccountsFragmentBinding
import com.rtchubs.edokanpat.databinding.MyDevicesFragmentBinding
import com.rtchubs.edokanpat.databinding.SettingsFragmentBinding
import com.rtchubs.edokanpat.databinding.TransactionsFragmentBinding
import com.rtchubs.edokanpat.ui.common.BaseFragment

class TransactionsFragment : BaseFragment<TransactionsFragmentBinding, TransactionsViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_transactions
    override val viewModel: TransactionsViewModel by viewModels {
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