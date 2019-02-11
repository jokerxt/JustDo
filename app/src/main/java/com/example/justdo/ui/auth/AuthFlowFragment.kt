package com.example.justdo.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.justdo.App
import com.example.justdo.R
import com.example.justdo.Screens
import com.example.justdo.extension.setLaunchScreen
import com.example.justdo.ui.common.FlowFragment
import com.example.justdo.ui.privacypolicy.PrivacyPolicyFragment
import com.example.justdo.ui.termsconditions.TermsConditionsFragment

class AuthFlowFragment : FlowFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.componentsManager.getFlowComponent().inject(this)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.Login)
        }
    }

    override fun animateTransaction(
        currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction
    ) {
        when (nextFragment) {
            is TermsConditionsFragment,
            is PrivacyPolicyFragment -> {
                fragmentTransaction.setCustomAnimations(
                    R.anim.slide_in_up, R.anim.slide_out_up,
                    R.anim.slide_in_down, R.anim.slide_out_down
                )
            }
            else -> {
                fragmentTransaction.setCustomAnimations(
                    R.anim.fade_in, R.anim.fade_out,
                    R.anim.fade_in, R.anim.fade_out
                )
            }
        }
    }

}