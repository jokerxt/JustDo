package com.example.justdo.presentation.auth

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.Handler
import com.example.justdo.App
import com.example.justdo.Screens
import com.example.justdo.domain.interactors.auth.AuthInteractor
import com.example.justdo.model.data.server.error.ServerError
import com.example.justdo.model.data.storage.GlobalPreference
import com.example.justdo.presentation.BaseViewModel
import com.example.justdo.system.FlowRouter
import com.example.justdo.system.SingleLiveEvent
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

class AuthViewModel(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var router: FlowRouter

    @Inject
    lateinit var authInteractor: AuthInteractor

    val isPasswordChanged = SingleLiveEvent<Boolean>()

    init {
        App.componentsManager.getFlowComponent().inject(this)
    }

    fun onBackPressed() = router.exit()

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

    fun onLoginClick(email: String, password: String) {
        val isDebug = ((getApplication<App>().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0)

        if (!isDebug) {
            authInteractor.login(email, password)
                .subscribe(
                    { router.newRootFlow(Screens.TasksFlow) },
                    {
                        val code = if (it is ServerError) it.errorCode.toString() else ""
                        Timber.e("$code LOGIN error: $it")
                        responseError.postValue(it.message)
                    }
                )
                .connect()
        } else {
            val handler = Handler()
            Thread(Runnable {
                Thread.sleep(1000)
                handler.post {
                    if (Random.nextBoolean()) {
                        val prefs = GlobalPreference(getApplication())
                        prefs.token = "wrDFfwr2323*&^TS^&Vgdsadgragw"
                        prefs.refreshToken = "xfkswj485ytmrhg"

                        router.newRootFlow(Screens.TasksFlow)
                    } else {
                        if (Random.nextBoolean()) {
                            responseError.postValue("Incorrect email or password")
                        } else {
                            responseError.postValue("Server error - try again later")
                        }
                    }
                }
            }).start()
        }
    }

    fun onSignupClick(email: String, password: String) {
        val isDebug = ((getApplication<App>().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0)

        if (!isDebug) {
            authInteractor.signup(email, password)
                .subscribe(
                    {
                        val prefs = GlobalPreference(getApplication())
                        prefs.token = "wrDFfwr2323*&^TS^&Vgdsadgragw"
                        prefs.refreshToken = "xfkswj485ytmrhg"

                        router.newRootFlow(Screens.TasksFlow)
                    },
                    {
                        val code = if (it is ServerError) it.errorCode.toString() else ""
                        Timber.e("$code SIGNUP error: $it")
                        responseError.postValue(it.message)
                    }
                )
                .connect()
        } else {
            val handler = Handler()
            Thread(Runnable {
                Thread.sleep(1000)
                handler.post {
                    if (Random.nextBoolean()) {
                        router.newRootFlow(Screens.TasksFlow)
                    } else {
                        if (Random.nextBoolean()) {
                            responseError.postValue("User already exists")
                        } else {
                            responseError.postValue("Server error - try again later")
                        }
                    }
                }
            }).start()
        }
    }

    fun onChangePasswordClick(resetCode: String, password: String) {
        val isDebug = ((getApplication<App>().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0)

        if (!isDebug) {
            authInteractor.changePassword(resetCode, password)
                .subscribe(
                    {
                        isPasswordChanged.postValue(true)
                        router.backTo(Screens.Login)
                    },
                    {
                        val code = if (it is ServerError) it.errorCode.toString() else ""
                        Timber.e("$code CHANGE_PASSWORD error: $it")
                        responseError.postValue(it.message)
                    }
                )
                .connect()
        } else {
            val handler = Handler()
            Thread(Runnable {
                Thread.sleep(1000)
                handler.post {
                    if (Random.nextBoolean()) {
                        isPasswordChanged.postValue(true)
                        router.backTo(Screens.Login)
                    } else {
                        responseError.postValue("Server error - try again later")
                    }
                }
            }).start()
        }
    }

    fun onForgotPasswordClick() {
        router.navigateTo(Screens.ForgotPassword)
    }

    fun onForgotPasswordSendRequestClick(email: String) {
        val isDebug = ((getApplication<App>().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0)

        if (!isDebug) {
            authInteractor.forgotPassword(email)
                .subscribe(
                    { router.navigateTo(Screens.ResetPassword) },
                    {
                        val code = if (it is ServerError) it.errorCode.toString() else ""
                        Timber.e("$code FORGOT_PASSWORD error: $it")
                        responseError.postValue(it.message)
                    }
                )
                .connect()
        } else {
            val handler = Handler()
            Thread(Runnable {
                Thread.sleep(1000)
                handler.post {
                    if (Random.nextBoolean()) {
                        router.navigateTo(Screens.ResetPassword)
                    } else {
                        if (Random.nextBoolean()) {
                            responseError.postValue("Server error - try again later")
                        } else {
                            responseError.postValue("User with this email not registered")
                        }
                    }
                }
            }).start()
        }
    }
}