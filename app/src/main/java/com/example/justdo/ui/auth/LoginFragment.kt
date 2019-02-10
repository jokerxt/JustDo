package com.example.justdo.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.justdo.R
import com.example.justdo.extension.*
import com.example.justdo.ui.AppActivity
import kotlinx.android.synthetic.main.fragment_auth_login.*
import kotlinx.android.synthetic.main.layout_auth_accessing_agree.*
import kotlinx.android.synthetic.main.layout_auth_header_choice_auth.*

class LoginFragment : AuthFragment(), View.OnFocusChangeListener {

    override val layoutRes = R.layout.fragment_auth_login

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        choiceLogInButton.isClickable = false
        choiceSignUpButton.apply {
            setTextColor(ContextCompat.getColor(context, R.color.textInactiveButtonColor))
            setOnClickListener { viewModel?.onChoiceSignUpClick() }
        }

        loginEmail.onFocusChangeListener = this
        loginPassword.onFocusChangeListener = this

        loginButton.setOnClickListener {
            loginRootLayout.requestFocus()
            it.hideKeyboard()

            val isValidEmail = loginEmail.text.toString().isValidEmail()
            val isValidPassword = loginPassword.text.toString().isValidPassword()

            if (!isValidEmail || !isValidPassword) {
                if (!isValidEmail)
                    loginEmailInputLayout.error = getString(R.string.email_does_not_exist)

                if (!isValidPassword)
                    loginPasswordInputLayout.error = getString(R.string.password_must_contain)

                return@setOnClickListener
            }

            loginChangeStateViews(true)
            viewModel?.onLoginClick()
        }

        viewModel?.isErrorRequest?.observe(this, Observer {
            loginChangeStateViews(false)
            (activity as? AppActivity?)?.showErrorMessage(it)
        })

        forgotLoginPassword.setOnClickListener { viewModel?.onForgotPasswordClick() }
    }

    private fun loginChangeStateViews(isStartLogin: Boolean) {
        loginEmail.apply { if(isStartLogin) disable() else enable() }
        loginPassword.apply { if(isStartLogin) disable() else enable() }
        forgotLoginPassword.apply { if(isStartLogin) disable() else enable() }
        termsTextView.apply { if(isStartLogin) disable() else enable() }
        privacyTextView.apply { if(isStartLogin) disable() else enable() }
        choiceSignUpButton.apply { if(isStartLogin) disable() else enable() }
        loginButton.apply { if(isStartLogin) hide() else show() }
        loginProgress.apply { if(isStartLogin) show() else hide() }
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            when (v.id) {
                R.id.loginEmail -> loginEmailInputLayout.error = ""
                R.id.loginPassword -> loginPasswordInputLayout.error = ""
            }
        }
    }

}