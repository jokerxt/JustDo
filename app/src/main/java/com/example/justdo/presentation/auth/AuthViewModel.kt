package com.example.justdo.presentation.auth

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.example.justdo.App
import com.example.justdo.Screens
import com.example.justdo.system.FlowRouter
import com.example.justdo.system.SingleLiveEvent
import javax.inject.Inject

class AuthViewModel : ViewModel() {

    @Inject
    lateinit var router: FlowRouter

    val isPasswordChanged = SingleLiveEvent<Boolean>()
    val isErrorRequest = SingleLiveEvent<String>()

    init {
        App.componentsManager.getFlowComponent().inject(this)
    }

    fun onBackPressed() = router.exit()

    override fun onCleared() {
        super.onCleared()
    }

    fun onChoiceSignUpClick() {
        router.replaceScreen(Screens.Signup)
    }

    fun onChoiceLogInClick() {
        router.replaceScreen(Screens.Login)
    }

    fun onTermsConditionsClick() {
        router.navigateTo(Screens.TermsConditions)
    }

    fun onPrivacyPolicyClick() {
        router.navigateTo(Screens.PrivacyPolicy)
    }

    fun onLoginClick() {

        val handler1 = Handler(Looper.getMainLooper())

        Thread(Runnable {
            Thread.sleep(2000)

            //isErrorRequest.postValue("Incorrect email or password")

            handler1.post {
                router.newRootFlow(Screens.TasksFlow)
            }

        }).start()
    }

    fun onSignupClick() {
        Thread(Runnable {
            Thread.sleep(2000)

            isErrorRequest.postValue("User with this email already exists")

        }).start()
    }

    fun onForgotPasswordClick() {
        router.navigateTo(Screens.ForgotPassword)
    }

    fun onForgotPasswordSendRequestClick() {
        router.navigateTo(Screens.ResetPassword)
    }

    fun onChangePasswordClick() {
        Thread(Runnable {
            Thread.sleep(5000)

            isPasswordChanged.postValue(true)

        }).start()
    }

    fun backToLogin() {
        router.backTo(Screens.Login)
    }
}