package com.example.justdo.ui.password

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.R
import com.example.justdo.extension.*
import com.example.justdo.presentation.auth.AuthViewModel
import com.example.justdo.ui.AppActivity
import com.example.justdo.ui.common.BaseFragment
import com.example.justdo.ui.common.dialogs.OnDialogClickListener
import kotlinx.android.synthetic.main.fragment_reset_password.*

class ResetPasswordFragment : BaseFragment(), View.OnFocusChangeListener, OnDialogClickListener {

    override val layoutRes = R.layout.fragment_reset_password

    private val viewModel: AuthViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it).get(AuthViewModel::class.java) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        newPassword.onFocusChangeListener = this
        confirmNewPassword.onFocusChangeListener = this
        resetPasswordCode.onFocusChangeListener = this

        changePasswordButton.setOnClickListener {
            resetPasswordRootLayout.requestFocus()
            it.hideKeyboard()

            val resetCode = resetPasswordCode.text.toString()
            val password = newPassword.text.toString()

            val isCodeEntered = resetCode.isNotEmpty()
            val isValidPassword = password.isValidPassword()
            val isConfirmedPassword = password.contentEquals(confirmNewPassword.text)

            if (!isCodeEntered || !isValidPassword || !isConfirmedPassword) {
                if (!isCodeEntered)
                    resetPasswordCodeLayout.error = getString(R.string.enter_code)

                if (!isValidPassword)
                    newPasswordLayout.error = getString(R.string.password_must_contain)

                if (!isConfirmedPassword)
                    confirmNewPasswordLayout.error = getString(R.string.password_do_not_match)

                return@setOnClickListener
            }

            resetChangeStateViews(true)
            viewModel?.onChangePasswordClick(resetCode, password)
        }

        resetPasswordArrowBack.apply {
            expandTouchArea(15f)
            setOnClickListener { onBackPressed() }
        }

        viewModel?.responseError?.observe(this, Observer {
            resetChangeStateViews(false)
            (activity as? AppActivity?)?.showErrorMessage(it)
        })

        viewModel?.isPasswordChanged?.observe(this, Observer {
            (activity as? AppActivity?)?.showMessage(
                getString(R.string.success),
                getString(R.string.password_success_changed)
            )
        })
    }

    private fun resetChangeStateViews(isStartReset: Boolean) {
        resetPasswordArrowBack.apply { if (isStartReset) disable() else enable() }
        resetPasswordCode.apply { if (isStartReset) disable() else enable() }
        newPassword.apply { if (isStartReset) disable() else enable() }
        confirmNewPassword.apply { if (isStartReset) disable() else enable() }
        changePasswordButton.apply { if (isStartReset) hide() else show() }
        changePasswordProgress.apply { if (isStartReset) show() else hide() }
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (hasFocus) {
            when (v.id) {
                R.id.newPassword -> newPasswordLayout.error = ""
                R.id.confirmNewPassword -> confirmNewPasswordLayout.error = ""
                R.id.resetPasswordCode -> resetPasswordCodeLayout.error = ""
            }
        }
    }

    override fun onBackPressed() {
        viewModel?.onBackPressed()
    }
}