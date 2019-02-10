package com.example.justdo.extension

import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.Patterns


@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned = run {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun String.isValidEmail(): Boolean {
    return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z].*[A-Z])(?=.*[~!@#\$%^&*()_+`\\-={}\\[\\]:;<>./\\\\])(?=\\S+\$).{8,15}\$"
    return !isNullOrEmpty() && pattern.toRegex().matches(this)
}