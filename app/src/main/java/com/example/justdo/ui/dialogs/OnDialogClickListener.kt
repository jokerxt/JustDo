package com.example.justdo.ui.dialogs

interface OnDialogClickListener {
    fun dialogConfirmClicked(tag: String?) {}
    fun dialogPositiveClicked(tag: String?) {}
    fun dialogNegativeClicked(tag: String?) {}
    fun dialogItemClicked(tag: String?, position: Int) {}
    fun dialogCanceled(tag: String?) {}
}