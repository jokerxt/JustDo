package com.example.justdo.model.data.storage

import android.content.Context
import com.example.justdo.domain.entities.server.TokenInfo
import javax.inject.Inject

class GlobalPreference @Inject constructor(
    private val context: Context
) {

    private fun getSharedPreferences(prefsName: String) =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    private val TOKEN_DATA = "token_data"
    private val KEY_TOKEN = "token"
    private val KEY_REFRESH_TOKEN = "refresh_token"

    private val authPrefs by lazy { getSharedPreferences(TOKEN_DATA) }

    var tokenInfo: TokenInfo
        get() = TokenInfo(token, refreshToken)
        set(value) {
            token = value.accessToken
            refreshToken = value.refreshToken
        }

    var token: String?
        get() {
            val token = authPrefs.getString(KEY_TOKEN, null)
            return token?.let { "Bearer $it" }
        }
        set(value) {
            authPrefs.edit().putString(KEY_TOKEN, value).apply()
        }

    var refreshToken: String?
        get() = authPrefs.getString(KEY_REFRESH_TOKEN, null)
        set(value) {
            authPrefs.edit().putString(KEY_REFRESH_TOKEN, value).apply()
        }
}