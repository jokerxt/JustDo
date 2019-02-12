package com.example.justdo.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.justdo.R
import com.example.justdo.extension.*
import com.example.justdo.ui.AppActivity
import kotlinx.android.synthetic.main.fragment_auth_signup.*
import kotlinx.android.synthetic.main.layout_auth_accessing_agree.*
import kotlinx.android.synthetic.main.layout_auth_header_choice_auth.*

class SignupFragment : AuthFragment(), View.OnFocusChangeListener {

    override val layoutRes = R.layout.fragment_auth_signup

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        choiceSignUpButton.isClickable = false
        choiceLogInButton.apply {
            setTextColor(ContextCompat.getColor(context, R.color.textInactiveButtonColor))
            setOnClickListener { viewModel?.onChoiceLogInClick() }
        }

        signupEmail.onFocusChangeListener = this
        signupPassword.onFocusChangeListener = this
        signupConfirmPassword.onFocusChangeListener = this

        signUpButton.setOnClickListener {
            signupRootLayout.requestFocus()
            it.hideKeyboard()

            val email = signupEmail.text.toString()
            val password = signupPassword.text.toString()

            val isValidEmail = email.isValidEmail()
            val isValidPassword = password.isValidPassword()
            val isConfirmedPassword = password.contentEquals(signupConfirmPassword.text)

            if (!isValidEmail || !isValidPassword || !isConfirmedPassword) {
                if (!isValidEmail)
                    signupEmailLayout.error = getString(R.string.email_does_not_exist)

                if (!isValidPassword)
                    signupPasswordLayout.error = getString(R.string.password_must_contain)

                if (!isConfirmedPassword)
                    signupConfirmPasswordLayout.error = getString(R.string.password_do_not_match)

                return@setOnClickListener
            }

            signupChangeStateViews(true)
            viewModel?.onSignupClick(email, password)
        }

        viewModel?.responseError?.observe(this, Observer {
            signupChangeStateViews(false)
            (activity as? AppActivity?)?.showErrorMessage(it)
        })
    }

    private fun signupChangeStateViews(isStartSignup: Boolean) {
        signupPassword.apply { if (isStartSignup) disable() else enable() }
        signupEmail.apply { if (isStartSignup) disable() else enable() }
        signupConfirmPassword.apply { if (isStartSignup) disable() else enable() }
        termsTextView.apply { if (isStartSignup) disable() else enable() }
        privacyTextView.apply { if (isStartSignup) disable() else enable() }
        choiceLogInButton.apply { if (isStartSignup) disable() else enable() }
        signUpButton.apply { if (isStartSignup) hide() else show() }
        signupProgress.apply { if (isStartSignup) show() else hide() }
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            when (v.id) {
                R.id.signupEmail -> signupEmailLayout.error = ""
                R.id.signupPassword -> signupPasswordLayout.error = ""
                R.id.signupConfirmPassword -> signupConfirmPasswordLayout.error = ""
            }
        }
    }

}