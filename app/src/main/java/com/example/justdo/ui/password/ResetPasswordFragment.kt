package com.example.justdo.ui.password

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.R
import com.example.justdo.extension.*
import com.example.justdo.presentation.auth.AuthViewModel
import com.example.justdo.ui.common.BaseFragment
import com.example.justdo.ui.common.dialogs.InfoDialogFragment
import kotlinx.android.synthetic.main.fragment_reset_password.*

class ResetPasswordFragment : BaseFragment(), View.OnFocusChangeListener, InfoDialogFragment.OnClickListener {

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

            val password = newPassword.text.toString()
            val isCodeEntered = resetPasswordCode.text.isNotEmpty()
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

            resetPasswordArrowBack.disable()
            resetPasswordCode.disable()
            newPassword.disable()
            confirmNewPassword.disable()
            changePasswordButton.hide()
            changePasswordProgress.show()

            viewModel?.onChangePasswordClick()
        }

        resetPasswordArrowBack.apply {
            expandTouchArea(15f)
            setOnClickListener { onBackPressed() }
        }

        viewModel?.isPasswordChanged?.observe(this, Observer { isPassChanged ->
            val tag = if(isPassChanged) SUCCESS_CHANGED_PASSWORD_TAG else ERROR_CHANGED_PASSWORD_TAG
            val title = getString(if(isPassChanged) R.string.success else R.string.error_title_dialog)
            val messageResId = if(isPassChanged) R.string.password_success_changed else R.string.error_message_dialog
            val message = getString(messageResId)

            InfoDialogFragment.create(title, message, getString(R.string.ok), tag).show(childFragmentManager, tag)
        })
    }

    override fun dialogConfirm(tag: String) {
        handleDialogActions(tag)
    }

    override fun dialogCanceled(tag: String) {
        handleDialogActions(tag)
    }

    private fun handleDialogActions(tag: String) {
        when (tag) {
            SUCCESS_CHANGED_PASSWORD_TAG -> viewModel?.backToLogin()
            ERROR_CHANGED_PASSWORD_TAG -> {
                resetPasswordArrowBack.enable()
                resetPasswordCode.enable()
                newPassword.enable()
                confirmNewPassword.enable()
                changePasswordButton.show()
                changePasswordProgress.hide()
            }
        }
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

    private companion object {
        private const val SUCCESS_CHANGED_PASSWORD_TAG = "success_changed_password_tag"
        private const val ERROR_CHANGED_PASSWORD_TAG = "error_changed_password_tag"
    }

}