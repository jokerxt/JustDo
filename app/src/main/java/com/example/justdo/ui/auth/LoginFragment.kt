package com.example.justdo.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.justdo.R
import com.example.justdo.extension.*
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

            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            val isValidEmail = email.isValidEmail()
            val isValidPassword = password.isValidPassword()

            if (!isValidEmail || !isValidPassword) {
                if (!isValidEmail)
                    loginEmailInputLayout.error = getString(R.string.email_does_not_exist)

                if (!isValidPassword)
                    loginPasswordInputLayout.error = getString(R.string.password_must_contain)

                return@setOnClickListener
            }

            changeStateViews(true)
            viewModel?.onLoginClick(email, password)
        }

        forgotLoginPassword.setOnClickListener { viewModel?.onForgotPasswordClick() }
    }

    override fun changeStateViews(state: Boolean) {
        loginEmail.apply { if (state) disable() else enable() }
        loginPassword.apply { if (state) disable() else enable() }
        forgotLoginPassword.apply { if (state) disable() else enable() }
        termsTextView.apply { if (state) disable() else enable() }
        privacyTextView.apply { if (state) disable() else enable() }
        choiceSignUpButton.apply { if (state) disable() else enable() }
        loginButton.apply { if (state) hide() else show() }
        loginProgress.apply { if (state) show() else hide() }
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