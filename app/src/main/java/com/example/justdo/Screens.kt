package com.example.justdo

import com.example.justdo.ui.auth.AuthFlowFragment
import com.example.justdo.ui.auth.LoginFragment
import com.example.justdo.ui.auth.SignupFragment
import com.example.justdo.ui.password.ChangePasswordFragment
import com.example.justdo.ui.password.ForgotPasswordFragment
import com.example.justdo.ui.password.ResetPasswordFragment
import com.example.justdo.ui.privacypolicy.PrivacyPolicyFragment
import com.example.justdo.ui.tasks.add.AddTaskFragment
import com.example.justdo.ui.tasks.FilterTasksFragment
import com.example.justdo.ui.tasks.TasksFlowFragment
import com.example.justdo.ui.tasks.list.TasksListFragment
import com.example.justdo.ui.termsconditions.TermsConditionsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    // Terms
    object PrivacyPolicy : SupportAppScreen() {
        override fun getFragment() = PrivacyPolicyFragment()
    }

    object TermsConditions : SupportAppScreen() {
        override fun getFragment() = TermsConditionsFragment()
    }

    // Auth
    object AuthFlow : SupportAppScreen() {
        override fun getFragment() = AuthFlowFragment()
    }

    object Login : SupportAppScreen() {
        override fun getFragment() = LoginFragment()
    }

    object Signup : SupportAppScreen() {
        override fun getFragment() = SignupFragment()
    }

    object ForgotPassword : SupportAppScreen() {
        override fun getFragment() = ForgotPasswordFragment()
    }

    object ResetPassword : SupportAppScreen() {
        override fun getFragment() = ResetPasswordFragment()
    }

    object ChangePassword : SupportAppScreen() {
        override fun getFragment() = ChangePasswordFragment()
    }


    // Tasks
    object TasksFlow : SupportAppScreen() {
        override fun getFragment() = TasksFlowFragment()
    }

    object TasksList : SupportAppScreen() {
        override fun getFragment() = TasksListFragment()
    }

    object TasksFilter : SupportAppScreen() {
        override fun getFragment() = FilterTasksFragment()
    }

    object AddTask : SupportAppScreen() {
        override fun getFragment() = AddTaskFragment()
    }

}