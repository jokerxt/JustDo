package com.example.justdo.ui

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.justdo.App
import com.example.justdo.R
import com.example.justdo.presentation.AppStarter
import com.example.justdo.ui.common.BaseFragment
import com.example.justdo.ui.common.dialogs.InfoDialogFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject
import javax.inject.Named

class AppActivity : AppCompatActivity() {

    @Inject
    lateinit var appStarter: AppStarter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment?

    private val navigator: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, R.id.container) {
            override fun setupFragmentTransaction(
                command: Command?, currentFragment: Fragment?,
                nextFragment: Fragment?, fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.componentsManager.getAppComponent().inject(this)

        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
        }
        else {
            window.statusBarColor = Color.BLACK
        }

        setContentView(R.layout.layout_container)

        if (savedInstanceState == null) {
            appStarter.start()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    private fun showDialogMessage(title: String, message: String) {
        InfoDialogFragment.create(title, message).show(supportFragmentManager, null)
    }

    fun showErrorMessage(message: String? = null) {
        val title = getString(R.string.error_title_dialog)
        showDialogMessage(title, message ?: getString(R.string.error_message_dialog))
    }

}
