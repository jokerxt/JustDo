package com.example.justdo.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.justdo.App
import com.example.justdo.R
import com.example.justdo.Screens
import com.example.justdo.extension.setLaunchScreen
import com.example.justdo.ui.common.FlowFragment

class TasksFlowFragment : FlowFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.componentsManager.getFlowComponent().inject(this)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Screens.TasksList)
        }
    }

    override fun animateTransaction(
        currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction
    ) {
        fragmentTransaction.setCustomAnimations(
            R.anim.fade_in, R.anim.fade_out,
            R.anim.fade_in, R.anim.fade_out
        )
    }

}