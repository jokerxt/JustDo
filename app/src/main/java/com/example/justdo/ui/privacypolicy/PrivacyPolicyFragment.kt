package com.example.justdo.ui.privacypolicy

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.R
import com.example.justdo.extension.expandTouchArea
import com.example.justdo.extension.fromHtml
import com.example.justdo.presentation.auth.AuthViewModel
import com.example.justdo.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_privacy_policy.*

class PrivacyPolicyFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_privacy_policy

    private val viewModel: AuthViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it).get(AuthViewModel::class.java) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        privacyPolicyArrowBack.apply {
            expandTouchArea(15f)
            setOnClickListener { onBackPressed() }
        }

        privacyPolicyContent.text =
            "My Company (change this) («us», «we», or «our») operates http://www.mysite.com (change this) (the «Site»). This page informs you of our policies regarding the collection, use and disclosure of Personal Information we receive from users of the Site. We use your Personal Information only for providing and improving the Site. By using the Site, you agree to the collection and use of information in accordance with this policy.<br><h2><font color=\"#5B5993\">Information Collection And Use</font></h2>While using our Site, we may ask you to provide us with certain personally identifiable information that can be used to contact or identify you. Personally identifiable information may include, but is not limited to your name («Personal Information»).<br><h2><font color=\"#5B5993\">Log Data</font></h2>Like many site operators, we collect information that your browser sends whenever you visit our Site («Log Data»). This Log Data may include information such as your computer's Internet Protocol (\"IP\") address, browser type, browser version, the pages of our Site that you visit, the time and date of your visit, the time spent on those pages and other statistics. In addition, we may use third party services such as Google Analytics that collect, monitor and analyze this …<br><h2><font color=\"#5B5993\">Communications</font></h2>We may use your Personal Information to contact you with newsletters, marketing or promotional materials and other information that …".fromHtml()
    }

    override fun onBackPressed() {
        viewModel?.onBackPressed()
    }
}