package com.example.justdo.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.justdo.R
import com.example.justdo.extension.hide
import com.example.justdo.extension.inflate
import kotlinx.android.synthetic.main.fragment_menu_dialog.*

class MenuDialogFragment : RootDialogFragment(), View.OnClickListener {
    override val layoutRes = R.layout.fragment_menu_dialog

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.apply {
            getString(ARG_CANCEL)?.let { menuCancel.text = it }

            dialogTag = getString(ARG_TAG, dialogTag)

            getIntArray(ARG_ITEMS)?.apply {
                val lastItem = size - 1
                forEachIndexed { index, resId ->
                    val itemView = menuItemsHolder.inflate(R.layout.item_menu)
                    itemView.findViewById<TextView>(R.id.menuItem).apply {
                        text = getString(resId)
                        setOnClickListener(this@MenuDialogFragment)
                    }

                    if (index == lastItem)
                        itemView.findViewById<View>(R.id.menuItemDivider).hide(true)

                    menuItemsHolder.addView(itemView)
                }
            }

            menuCancel.setOnClickListener(this@MenuDialogFragment)
            menuDialogRootLayout.setOnClickListener(this@MenuDialogFragment)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.menuDialogRootLayout,
            R.id.menuCancel -> {
                dismissAllowingStateLoss()
                clickListener.dialogCanceled(dialogTag)
            }
            else -> {
                dismissAllowingStateLoss()
                clickListener.dialogItemClicked(dialogTag, menuItemsHolder.indexOfChild(v.parent as View))
            }
        }
    }

    companion object {
        private const val ARG_ITEMS = "arg_items"
        private const val ARG_CANCEL = "arg_cancel"
        private const val ARG_TAG = "arg_tag"

        fun create(items: List<Int>, cancelText: String? = null, tag: String? = null) =
            MenuDialogFragment().apply {
                arguments = Bundle().apply {
                    putIntArray(ARG_ITEMS, items.toIntArray())
                    putString(ARG_CANCEL, cancelText)
                    putString(ARG_TAG, tag)
                }
            }
    }

}