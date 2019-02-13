package com.example.justdo.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.justdo.R
import com.example.justdo.extension.*
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

            changeStateViews(true)
            viewModel?.onSignupClick(email, password)
        }
    }

    override fun changeStateViews(state: Boolean) {
        signupPassword.apply { if (state) disable() else enable() }
        signupEmail.apply { if (state) disable() else enable() }
        signupConfirmPassword.apply { if (state) disable() else enable() }
        termsTextView.apply { if (state) disable() else enable() }
        privacyTextView.apply { if (state) disable() else enable() }
        choiceLogInButton.apply { if (state) disable() else enable() }
        signUpButton.apply { if (state) hide() else show() }
        signupProgress.apply { if (state) show() else hide() }
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