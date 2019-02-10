package com.example.justdo.ui.auth

import android.os.Bundle
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.presentation.auth.AuthViewModel
import com.example.justdo.ui.common.BaseFragment
import kotlinx.android.synthetic.main.layout_auth_accessing_agree.*

abstract class AuthFragment : BaseFragment() {

    protected val viewModel: AuthViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it).get(AuthViewModel::class.java) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        termsTextView.setOnClickListener { viewModel?.onTermsConditionsClick() }
        privacyTextView.setOnClickListener { viewModel?.onPrivacyPolicyClick() }

        TextViewCompat.setAutoSizeTextTypeWithDefaults(
            accessingAgreeTextView,
            TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM
        )
    }

    override fun onBackPressed() {
        viewModel?.onBackPressed()
    }

}