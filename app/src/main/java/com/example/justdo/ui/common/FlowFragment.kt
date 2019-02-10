package com.example.justdo.ui.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.justdo.R
import com.example.justdo.system.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

abstract class FlowFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.layout_container

    private val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    @Inject
    lateinit var cicerone: Cicerone<FlowRouter>

    private val navigatorHolder: NavigatorHolder by lazy {
        cicerone.navigatorHolder
    }

    @Inject
    lateinit var appRouter: Router

    protected val navigator: Navigator by lazy {
        object : SupportAppNavigator(this.activity, childFragmentManager, R.id.container) {
            override fun activityBack() {
                onExit()
            }

            override fun setupFragmentTransaction(
                command: Command?,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                //fix incorrect order lifecycle callback of MainFlowFragment
                fragmentTransaction.setReorderingAllowed(true)
                animateTransaction(currentFragment, nextFragment, fragmentTransaction)
            }
        }
    }

    open fun animateTransaction(
        currentFragment: Fragment?, nextFragment: Fragment?, fragmentTransaction: FragmentTransaction) { }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    open fun onExit() {
        appRouter.exit()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}