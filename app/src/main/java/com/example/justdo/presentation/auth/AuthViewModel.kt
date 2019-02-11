package com.example.justdo.presentation.auth

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import com.example.justdo.App
import com.example.justdo.Screens
import com.example.justdo.domain.interactors.auth.AuthInteractor
import com.example.justdo.system.FlowRouter
import com.example.justdo.system.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var router: FlowRouter

    @Inject
    lateinit var authInteractor: AuthInteractor

    val isPasswordChanged = SingleLiveEvent<Boolean>()
    val isErrorRequest = SingleLiveEvent<String>()

    private val compositeDisposable = CompositeDisposable()

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

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    private fun Disposable.connect() {
        compositeDisposable.add(this)
    }

    fun onLoginClick(email: String, password: String) {

        val isDebug = ((getApplication<App>().applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0)

        if(isDebug) {
            authInteractor.login(email, password)
                .subscribe(
                    { router.newRootFlow(Screens.TasksFlow) },
                    {
                        Timber.e("login error: $it")
                        isErrorRequest.postValue(it.message)
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
                            isErrorRequest.postValue("Incorrect email or password")
                        } else {
                            isErrorRequest.postValue("Server error - try again later")
                        }
                    }
                }
            }).start()
        }
    }

    fun onSignupClick(email: String, password: String) {
        authInteractor.signup(email, password)
            .subscribe(
                { router.newRootFlow(Screens.TasksFlow) },
                {
                    Timber.e("signup error: $it")
                    isErrorRequest.postValue(it.message)
                }
            )
            .connect()
    }

    fun onChangePasswordClick() {
        Thread(Runnable {
            Thread.sleep(5000)

            isPasswordChanged.postValue(true)

        }).start()
    }

    fun onForgotPasswordClick() {
        router.navigateTo(Screens.ForgotPassword)
    }

    fun onForgotPasswordSendRequestClick() {
        router.navigateTo(Screens.ResetPassword)
    }

    fun backToLogin() {
        router.backTo(Screens.Login)
    }
}