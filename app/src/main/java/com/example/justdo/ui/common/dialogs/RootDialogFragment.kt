package com.example.justdo.ui.common.dialogs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.justdo.R

abstract class RootDialogFragment : DialogFragment() {

    abstract val layoutRes: Int

    protected var dialogTag = ""

    protected val clickListener
        get() = when {
            parentFragment is OnDialogClickListener -> parentFragment as OnDialogClickListener
            activity is OnDialogClickListener -> activity as OnDialogClickListener
            else -> object : OnDialogClickListener {}
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(arguments == null) {
            dismissAllowingStateLoss()
            clickListener.dialogCanceled(dialogTag)
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        clickListener.dialogCanceled(dialogTag)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

}