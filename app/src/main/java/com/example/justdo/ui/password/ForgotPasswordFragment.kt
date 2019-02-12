package com.example.justdo.ui.password

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.R
import com.example.justdo.extension.*
import com.example.justdo.presentation.auth.AuthViewModel
import com.example.justdo.ui.AppActivity
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
            if (hasFocus)
                forgotPasswordEmailInputLayout.error = ""
        }

        sendRequestButton.setOnClickListener {
            forgotPasswordRootLayout.requestFocus()
            it.hideKeyboard()

            val email = forgotPasswordEmail.text.toString()
            val isValidEmail = email.isValidEmail()

            if (!isValidEmail) {
                forgotPasswordEmailInputLayout.error = getString(R.string.email_does_not_exist)
                return@setOnClickListener
            }

            resetChangeStateViews(true)
            viewModel?.onForgotPasswordSendRequestClick(email)
        }

        viewModel?.responseError?.observe(this, Observer {
            resetChangeStateViews(false)
            (activity as? AppActivity?)?.showErrorMessage(it)
        })

        forgotPasswordArrowBack.apply {
            expandTouchArea(15f)
            setOnClickListener { onBackPressed() }
        }
    }

    private fun resetChangeStateViews(isStartReset: Boolean) {
        forgotPasswordEmail.apply { if (isStartReset) disable() else enable() }
        forgotPasswordArrowBack.apply { if (isStartReset) disable() else enable() }
        sendRequestButton.apply { if (isStartReset) hide() else show() }
        forgotPasswordProgress.apply { if (isStartReset) show() else hide() }
    }

    override fun onBackPressed() {
        viewModel?.onBackPressed()
    }
}