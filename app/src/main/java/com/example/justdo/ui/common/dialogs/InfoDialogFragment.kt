package com.example.justdo.ui.common.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.justdo.R
import kotlinx.android.synthetic.main.fragment_info_dialog.*


class InfoDialogFragment : DialogFragment() {

    private val layoutRes = R.layout.fragment_info_dialog

    private var dialogTag = ""

    private val clickListener
        get() = when {
            parentFragment is OnClickListener -> parentFragment as OnClickListener
            activity is OnClickListener -> activity as OnClickListener
            else -> object : OnClickListener {}
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.InfoDialog)
    }

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
                clickListener.dialogConfirm(dialogTag)
            }

            return
        }

        dismissAllowingStateLoss()
        clickListener.dialogCanceled(dialogTag)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        clickListener.dialogCanceled(dialogTag)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
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

    interface OnClickListener {
        fun dialogConfirm(tag: String) {}
        fun dialogCanceled(tag: String) {}
    }

}