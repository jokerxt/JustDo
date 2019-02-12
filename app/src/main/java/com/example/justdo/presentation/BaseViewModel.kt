package com.example.justdo.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.justdo.system.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val responseError = SingleLiveEvent<String>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun Disposable.connect() {
        compositeDisposable.add(this)
    }
}