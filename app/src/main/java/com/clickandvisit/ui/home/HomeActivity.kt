package com.clickandvisit.ui.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityHomeBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity(), DrawerLayout.DrawerListener{

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)

        navController = androidx.navigation.Navigation.findNavController(this, R.id.fragmentHomeNavHost)
        navController.setGraph(R.navigation.mobile_navigation, intent.extras)
        registerBindingAndBaseObservers(binding)
        registerHomeObservers()
        bindListeners()
    }


    private fun registerAutoNavigation() {

    }

    override fun onStart() {
        super.onStart()
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityHomeBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentHomeNavHost)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun isItemChecked(index: Int) = binding.navigationViewHome.menu.getItem(index).isChecked

    private fun bindListeners() {
        binding.navigationViewHome.setNavigationItemSelectedListener {
            binding.drawerLayoutHome.closeDrawers()
            when (it.itemId) {
                R.id.nav_menu_1 -> {
                    if (!isItemChecked(0)) {

                    }
                }

                R.id.nav_menu_2 -> {
                    if (!isItemChecked(1)) {

                    }
                }

                R.id.nav_menu_3 -> {
                    if (!isItemChecked(2)) {

                    }
                }

                R.id.nav_menu_4 -> {
                    if (!isItemChecked(3)) {

                    }
                }

                R.id.nav_menu_5 -> {
                    if (!isItemChecked(4)) {
                        //navController.navigate(R.id.messageFragment)

                    }
                }

                R.id.nav_menu_6 -> {
                    if (!isItemChecked(5)) {

                    }
                }

                R.id.nav_menu_7 -> {
                    if (!isItemChecked(6)) {

                    }
                }

                R.id.nav_menu_8 -> {
                    if (!isItemChecked(7)) {

                    }
                }

                R.id.nav_menu_9 -> {
                    viewModel.disconnect()
                    binding.navigationViewHome.menu.getItem(8).isChecked = false
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    /**
     * register UI Home activity Observers
     */
    private fun registerHomeObservers() {
        binding.navigationViewHome.menu.getItem(0).isChecked = true
        binding.drawerLayoutHome.addDrawerListener(this)
        registerAutoNavigation()

    }


    private fun onMenuClicked() {
        if (binding.drawerLayoutHome.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayoutHome.closeDrawers()
        } else {
            binding.drawerLayoutHome.openDrawer(GravityCompat.START)
        }
    }


    override fun onDrawerStateChanged(newState: Int) {
        DebugLog.i(TAG, "onDrawerStateChanged")
    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        val slideX: Float = drawerView.width * slideOffset
        binding.constraintLayoutHomeContent.translationX = slideX
    }

    override fun onDrawerClosed(drawerView: View) {
        // toolbarHome.startActionBtn.visibility = View.VISIBLE
    }

    override fun onDrawerOpened(drawerView: View) {
        // toolbarHome.startActionBtn.visibility = View.GONE
        //viewModel.getProfile()
    }


    fun onStarted() {
        DebugLog.d(TAG, "onStarted")
    }

    override fun navigate(navigationTo: Navigation) {
        super.navigate(navigationTo)
        when (navigationTo) {
            is Navigation.Back -> finish()
            is Navigation.OpenDrawerNavigation -> {
                binding.drawerLayoutHome.openDrawer(binding.navigationViewHome)
            }
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayoutHome.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayoutHome.closeDrawers()
        } else {
            //viewModel.onBackPressed()
            finish()
        }
    }

}