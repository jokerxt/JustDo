package com.example.justdo.domain.interactors

import com.example.justdo.model.data.storage.GlobalPreference
import javax.inject.Inject

class AppStartInteractor @Inject constructor(
    private val globalPreference: GlobalPreference
) {

    fun signUpToAccount() = globalPreference.token != null

}