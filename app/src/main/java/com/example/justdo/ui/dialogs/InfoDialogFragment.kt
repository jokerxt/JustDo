package com.example.justdo.ui.dialogs

import android.os.Bundle
import com.example.justdo.R
import kotlinx.android.synthetic.main.fragment_info_dialog.*


class InfoDialogFragment : RootDialogFragment() {

    override val layoutRes = R.layout.fragment_info_dialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.apply {
            getString(ARG_TITLE)?.let { dialogTitle.text = it }
            getString(ARG_MESSAGE)?.let { dialogMessage.text = it }
            dialogСonfirmButton.text = getString(ARG_CONFIRM_TEXT, getString(R.string.ok))

            dialogTag = getString(ARG_TAG, dialogTag)

            infoDialogRootLayout.setOnClickListener {
                dismissAllowingStateLoss()
                clickListener.dialogCanceled(dialogTag)
            }

            dialogСonfirmButton.setOnClickListener {
                dismissAllowingStateLoss()
                clickListener.dialogConfirmClicked(dialogTag)
            }
        }
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_MESSAGE = "arg_message"
        private const val ARG_TAG = "arg_tag"
        private const val ARG_CONFIRM_TEXT = "arg_confirm_text"

        fun create(title: String, message: String, confirmText: String? = null, tag: String? = null) =
            InfoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_MESSAGE, message)
                    putString(ARG_CONFIRM_TEXT, confirmText)
                    putString(ARG_TAG, tag)
                }
            }
    }

}