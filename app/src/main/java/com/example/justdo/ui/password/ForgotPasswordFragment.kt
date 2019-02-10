package com.example.justdo.ui.password

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.R
import com.example.justdo.extension.expandTouchArea
import com.example.justdo.extension.hideKeyboard
import com.example.justdo.extension.isValidEmail
import com.example.justdo.presentation.auth.AuthViewModel
import com.example.justdo.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_forgot_password

    private val viewModel: AuthViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it).get(AuthViewModel::class.java) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        forgotPasswordEmail.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus)
                forgotPasswordEmailInputLayout.error = ""
        }

        sendRequestButton.setOnClickListener {
            forgotPasswordRootLayout.requestFocus()
            it.hideKeyboard()

            val isValidEmail = forgotPasswordEmail.text.toString().isValidEmail()

            if (!isValidEmail) {
                forgotPasswordEmailInputLayout.error = getString(R.string.email_does_not_exist)
                return@setOnClickListener
            }

            viewModel?.onForgotPasswordSendRequestClick()
        }

        forgotPasswordArrowBack.apply {
            expandTouchArea(15f)
            setOnClickListener { onBackPressed() }
        }
    }

    override fun onBackPressed() {
        viewModel?.onBackPressed()
    }
}