package com.example.justdo.extension

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager


fun View.expandTouchArea(extraPaddingDp: Float) = (parent as View).also {
    it.post {
        val extraPadding =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, extraPaddingDp, resources.displayMetrics).toInt()
        val rect = Rect()
        getHitRect(rect)
        rect.top -= extraPadding
        rect.left -= extraPadding
        rect.right += extraPadding
        rect.bottom += extraPadding
        it.touchDelegate = TouchDelegate(rect, this)
    }
}

fun View.hideKeyboard() {
    context?.let {
        (it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(this.windowToken, 0)
    }
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(isGone: Boolean = false) {
    visibility = if(isGone) View.GONE else View.INVISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
