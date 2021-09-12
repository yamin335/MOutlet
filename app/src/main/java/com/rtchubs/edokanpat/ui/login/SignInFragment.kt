package com.rtchubs.edokanpat.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rtchubs.edokanpat.R
import com.rtchubs.edokanpat.BR
import com.rtchubs.edokanpat.databinding.LayoutOperatorSelectionBinding
import com.rtchubs.edokanpat.databinding.SignInBinding
import com.rtchubs.edokanpat.models.registration.RegistrationHelperModel
import com.rtchubs.edokanpat.ui.LoginHandlerCallback
import com.rtchubs.edokanpat.ui.common.BaseFragment
import com.rtchubs.edokanpat.util.AppConstants.commonErrorMessage
import com.rtchubs.edokanpat.util.hideKeyboard
import com.rtchubs.edokanpat.util.showErrorToast

class SignInFragment : BaseFragment<SignInBinding, SignInViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_sign_in
    override val viewModel: SignInViewModel by viewModels {
        viewModelFactory
    }

    private var loginListener: LoginHandlerCallback? = null

    val registrationHelper = RegistrationHelperModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LoginHandlerCallback) {
            loginListener = context
        } else {
            throw RuntimeException("$context must implement LoginHandlerCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        loginListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStatusBarBackgroundColor("#1E4356")
        registerToolbar(viewDataBinding.toolbar)

        viewModel.email.observe(viewLifecycleOwner, Observer {  mobileNo ->
            mobileNo?.let {
                viewDataBinding.btnProceed.isEnabled = it.isNotBlank() && !viewModel.password.value.isNullOrBlank()
            }
        })

        viewModel.password.observe(viewLifecycleOwner, Observer {  mobileNo ->
            mobileNo?.let {
                viewDataBinding.btnProceed.isEnabled = it.isNotBlank() && !viewModel.email.value.isNullOrBlank()
            }
        })

        viewDataBinding.btnProceed.setOnClickListener {
            hideKeyboard()
            viewModel.shopLogin().observe(viewLifecycleOwner, Observer { data ->
                data?.response?.let {
                    if (data.success == true) {
                        preferencesHelper.saveMerchant(it)
                        preferencesHelper.isLoggedIn = true
                        loginListener?.onLoggedIn()
                    }
                }
            })

            //tempopenOperatorSelectionDialog()
//            viewModel.mobileNo.value?.let { mobileNo ->
//                inquireAccount(mobileNo, Build.ID)
//            }
        }
    }

//    private fun tempopenOperatorSelectionDialog() {
//        val bottomSheetDialog = BottomSheetDialog(mActivity)
//        val binding = DataBindingUtil.inflate<LayoutOperatorSelectionBinding>(
//            layoutInflater,
//            R.layout.layout_operator_selection,
//            null,
//            false
//        )
//        bottomSheetDialog.setContentView(binding.root)
//
//
//        binding.btnBanglalink.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            val action = SignInFragmentDirections.actionSignInFragmentToTermsFragment(registrationHelper)
//            navController.navigate(action)
//        }
//
//        binding.btnGrameenphone.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            val action = SignInFragmentDirections.actionSignInFragmentToTermsFragment(registrationHelper)
//            navController.navigate(action)
//        }
//
//        binding.btnRobi.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            val action = SignInFragmentDirections.actionSignInFragmentToTermsFragment(registrationHelper)
//            navController.navigate(action)
//        }
//
//        binding.btnTeletalk.setOnClickListener {
//            bottomSheetDialog.dismiss()
//            val action = SignInFragmentDirections.actionSignInFragmentToTermsFragment(registrationHelper)
//            navController.navigate(action)
//        }
//        bottomSheetDialog.show()
//    }
//
//    private fun openOperatorSelectionDialog() {
//        val bottomSheetDialog = BottomSheetDialog(mActivity)
//        val binding = DataBindingUtil.inflate<LayoutOperatorSelectionBinding>(
//            layoutInflater,
//            R.layout.layout_operator_selection,
//            null,
//            false
//        )
//        bottomSheetDialog.setContentView(binding.root)
//
//
//        binding.btnBanglalink.setOnClickListener {
//            goForRegistration(bottomSheetDialog, "Banglalink")
//        }
//
//        binding.btnGrameenphone.setOnClickListener {
//            goForRegistration(bottomSheetDialog, "Grameenphone")
//        }
//
//        binding.btnRobi.setOnClickListener {
//            goForRegistration(bottomSheetDialog, "Robi")
//        }
//
//        binding.btnTeletalk.setOnClickListener {
//            goForRegistration(bottomSheetDialog, "Teletalk")
//        }
//        bottomSheetDialog.show()
//    }

//    private fun goForRegistration(dialog: BottomSheetDialog, operator: String) {
//        dialog.dismiss()
//        registrationHelper.isRegistered = false
//        registrationHelper.operator = operator
//        val action = SignInFragmentDirections.actionSignInFragmentToTermsFragment(registrationHelper)
//        navController.navigate(action)
//    }

//    private fun inquireAccount(mobile: String, deviceId: String) {
//        viewModel.inquireAccount(mobile, deviceId).observe(viewLifecycleOwner, Observer {response ->
//            response?.body?.let {
//                if (it.isRegistered == false) {
//                    registrationHelper.mobile = mobile
//                    openOperatorSelectionDialog()
//                } else if (it.isRegistered == true && it.isAllowed == true) {
//                    registrationHelper.isRegistered = true
//                    registrationHelper.isTermsAccepted = true
//                    registrationHelper.mobile = mobile
//                    val action = SignInFragmentDirections.actionSignInFragmentToOtpSignInFragment(registrationHelper)
//                    navController.navigate(action)
//                } else {
//                    showErrorToast(mContext, response.body.message ?: commonErrorMessage)
//                }
//            }
//        })
//    }
}