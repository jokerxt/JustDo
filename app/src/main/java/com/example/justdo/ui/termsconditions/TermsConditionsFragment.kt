package com.example.justdo.ui.termsconditions

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.justdo.extension.expandTouchArea
import com.example.justdo.extension.fromHtml
import com.example.justdo.presentation.auth.AuthViewModel
import com.example.justdo.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_terms_conditions.*


class TermsConditionsFragment : BaseFragment() {

    override val layoutRes = com.example.justdo.R.layout.fragment_terms_conditions

    private val viewModel: AuthViewModel? by lazy {
        parentFragment?.let { ViewModelProviders.of(it).get(AuthViewModel::class.java) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        termsConditionsArrowBack.apply {
            expandTouchArea(15f)
            setOnClickListener { onBackPressed() }
        }

        termsConditionsContent.text =
            "These terms and conditions («Terms», «Agreement») are an agreement between Mobile Application Developer («Mobile Application Developer», «us», «we» or «our») and you («User», «you» or «your»). This Agreement sets forth the general terms and conditions of your use of the Mentalstack mobile application and any of its products or services (collectively, «Mobile Application» or «Services»).<br><h2><font color=\"#5B5993\">Accounts and membership</font></h2>You must be at least 13 years of age to use this Mobile Application. By using this Mobile Application and by agreeing to this Agreement you warrant and represent that you are at least 13 years of age. If you create an account in the Mobile Application, you are responsible for maintaining the security of your account and you are fully responsible for all activities that occur under the account and any other actions taken in connection with it. Providing false contact information of any kind may result in the termination of your account. You must immediately notify us of any unauthorized uses of your account or any other breaches of security. We will not be liable for any acts or omissions by you, including any damages of any kind incurred as a result of such acts or omissions. We may suspend, disable, or delete your account (or any part thereof) if we determine that you have violated any provision of this Agreement or that your conduct or content would tend to damage our reputation and goodwill. If we delete your account for the foregoing reasons, you may not re-register for our Services. We may block your email address and Internet protocol address to prevent further registration.<br><h2><font color=\"#5B5993\">User content</font></h2>We do not own any data, information or material («Content») that you submit in the Mobile Application in the course of using the Service. You shall have sole responsibility for the accuracy, quality, integrity, legality, reliability, appropriateness, and intellectual property ownership or right to use of all submitted Content. We may, but have no obligation to, monitor Content in the Mobile Application submitted or created using our Services by you. Unless specifically permitted by you, your use of the Mobile Application does not grant us the license to use, reproduce, adapt, modify, publish or distribute the Content created by you or stored in your user account for commercial, marketing or any similar purpose. But you grant us permission to access, copy, distribute, store, transmit, reformat, display and perform the Content of your user account solely as required for the purpose of providing the Services to you. Backups We perform regular backups of the Content and will do our best to ensure completeness and accuracy of these backups. In the event of the hardware failure or data loss we will restore backups automatically to minimize the impact and downtime. Links to other mobile applications Although this Mobile Application may be linked to other mobile applications, we are not, directly or indirectly, implying any approval, association, sponsorship, endorsement, or affiliation with any linked mobile application, unless specifically stated herein. We are not responsible for examining or evaluating, and we do not warrant the offerings of, any businesses or individuals or the content of their mobile applications. We do not assume any responsibility or liability for the actions, products, services, and content of any other third-parties. You should carefully review the legal statements and other conditions of use of any mobile application which you access through a link from this Mobile Application. Your linking to any other off-site mobile applications is at your own risk.<br><h2><font color=\"#5B5993\">Indemnification</font></h2>You agree to indemnify and hold Mobile Application Developer and its affiliates, directors, officers, employees, and agents harmless from and against any liabilities, losses, damages or costs, including reasonable attorneys' fees, incurred in connection with or arising from any third-party allegations, claims, actions, disputes, or demands asserted against any of them as a result of or relating to your Content, your use of the Mobile Application or Services or any willful misconduct on your part. Changes and amendments We reserve the right to modify this Agreement or its policies relating to the Mobile Application or Services at any time, effective upon posting of an updated version of this Agreement in the Mobile Application. When we do, we will post a notification in our Mobile Application. Continued use of the Mobile Application after any such changes shall constitute your consent to such changes. Policy was created with WebsitePolicies.com".fromHtml()
    }

    override fun onBackPressed() {
        viewModel?.onBackPressed()
    }

}