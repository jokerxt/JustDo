package com.example.justdo.ui.common.dialogs

import com.example.justdo.R
import com.example.justdo.ui.common.BaseFragment

class ChoiceDialogFragment : BaseFragment() {

    companion object {
        const val ARG_ = "arg"

        fun create(id: Long) = ChoiceDialogFragment().apply {
            arguments?.putLong(ARG_, id)
        }
    }

    override val layoutRes = R.layout.fragment_choice_dialog

}