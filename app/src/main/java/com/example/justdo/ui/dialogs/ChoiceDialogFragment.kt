package com.example.justdo.ui.dialogs

import android.os.Bundle
import com.example.justdo.R
import kotlinx.android.synthetic.main.fragment_choice_dialog.*

class ChoiceDialogFragment : RootDialogFragment() {

    override val layoutRes = R.layout.fragment_choice_dialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.apply {
            getString(ARG_TITLE)?.let { dialogTitle.text = it }
            dialogPositiveButton.text = getString(ARG_POSITIVE_TEXT, getString(R.string.yes))
            dialogNegativeButton.text = getString(ARG_NEGATIVE_TEXT, getString(R.string.no))

            dialogTag = getString(ARG_TAG, dialogTag)

            choiceDialogRootLayout.setOnClickListener {
                dismissAllowingStateLoss()
                clickListener.dialogCanceled(dialogTag)
            }

            dialogPositiveButton.setOnClickListener {
                dismissAllowingStateLoss()
                clickListener.dialogPositiveClicked(dialogTag)
            }

            dialogNegativeButton.setOnClickListener {
                dismissAllowingStateLoss()
                clickListener.dialogNegativeClicked(dialogTag)
            }

        }
    }

    companion object {
        private const val ARG_TITLE = "arg_title"
        private const val ARG_TAG = "arg_tag"
        private const val ARG_POSITIVE_TEXT = "arg_positive_text"
        private const val ARG_NEGATIVE_TEXT = "arg_negative_text"

        fun create(
            title: String, positive: String? = null, negative: String? = null, tag: String? = null
        ) =
            ChoiceDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putString(ARG_POSITIVE_TEXT, positive)
                    putString(ARG_NEGATIVE_TEXT, negative)
                    putString(ARG_TAG, tag)
                }
            }
    }
}