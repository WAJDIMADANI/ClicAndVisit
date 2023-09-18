package com.clickandvisit.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.data.model.property.SearchRequest
import com.clickandvisit.databinding.ActivityHomeBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.ui.ads.addads.AddAdsActivity
import com.clickandvisit.ui.ads.adsdetails.AdsDetailsActivity
import com.clickandvisit.ui.ads.adslist.AdsListActivity
import com.clickandvisit.ui.ads.favourites.FavouritesActivity
import com.clickandvisit.ui.ads.filter.FilterActivity
import com.clickandvisit.ui.ads.search.SearchActivity
import com.clickandvisit.ui.home.map.MapsActivity
import com.clickandvisit.ui.user.chat.ChatActivity
import com.clickandvisit.ui.user.meet.MeetActivity
import com.clickandvisit.ui.user.profile.ProfileActivity
import com.clickandvisit.ui.user.signin.SignInActivity
import com.clickandvisit.ui.user.visits.VisitsActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity(), DrawerLayout.DrawerListener {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var adapter: SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)

        navController =
            androidx.navigation.Navigation.findNavController(this, R.id.fragmentHomeNavHost)
        registerBindingAndBaseObservers(binding)
        registerHomeObservers()
        bindListeners()
        registerRecycler(binding)

        if (viewModel.isConnected().not()) {
            binding.navigationViewHome.menu.findItem(R.id.nav_menu_9).isVisible = false
        }

    }

    private fun registerRecycler(binding: ActivityHomeBinding) {
        adapter.viewModel = viewModel
        adapter.userId = viewModel.getUserId()
        binding.rvSearch.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvSearch.adapter = adapter
    }

    private fun fetchNotificationsBull() {

        binding.toolbarHome.setNotification(viewModel.getChat())


        if (viewModel.getVisits()) {
            binding.navigationViewHome.menu.getItem(3).setTitle(R.string.home_menu_8_notification)
        } else {
            binding.navigationViewHome.menu.getItem(3).setTitle(R.string.home_menu_8)
        }

        if (viewModel.getMeet()) {
            binding.navigationViewHome.menu.getItem(2).setTitle(R.string.home_menu_5_notification)
        } else {
            binding.navigationViewHome.menu.getItem(2).setTitle(R.string.home_menu_5)
        }


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

    private fun bindListeners() {
        binding.navigationViewHome.setNavigationItemSelectedListener {
            binding.drawerLayoutHome.closeDrawers()
            if (viewModel.isConnected()) {
                when (it.itemId) {
                    R.id.nav_menu_1 -> {
                        startFilterForResult(viewModel.searchRequest)
                    }
                    R.id.nav_menu_2 -> {
                        Intent(this, AddAdsActivity::class.java).let { intent ->
                            intent.putExtra(
                                ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_EDIT,
                                false
                            )
                            intent.putExtra(
                                ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_MEET,
                                false
                            )
                            startActivity(intent)
                        }
                    }
                    R.id.nav_menu_3 -> {
                        navigateToActivity(AdsListActivity::class)
                    }
                    R.id.nav_menu_4 -> {
                        navigateToActivity(ProfileActivity::class)
                    }
                    R.id.nav_menu_5 -> {
                        navigateToActivity(MeetActivity::class)
                    }
                    R.id.nav_menu_6 -> {
                        navigateToActivity(FavouritesActivity::class)
                    }
                    R.id.nav_menu_7 -> {
                        Intent(this, SearchActivity::class.java).let { intent ->
                            intent.putExtra(
                                ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY,
                                viewModel.searchRequest
                            )
                            startActivityForResult(intent, ExtraKeys.FilterActivity.SEARCH_REQ_CODE)
                        }

                    }
                    R.id.nav_menu_8 -> {
                        navigateToActivity(VisitsActivity::class)
                    }
                    R.id.nav_menu_9 -> {
                        viewModel.disconnect()
                    }
                }
            } else {
                navigateToActivity(SignInActivity::class)
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun startFilterForResult(searchRequest: SearchRequest?) {
        Intent(this, FilterActivity::class.java).let { intent ->
            intent.putExtra(
                ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY,
                searchRequest
            )
            startActivityForResult(intent, ExtraKeys.FilterActivity.SEARCH_REQ_CODE)
        }
    }

    /**
     * register UI Home activity Observers
     */
    private fun registerHomeObservers() {
        binding.navigationViewHome.menu.getItem(0).isChecked = true
        binding.drawerLayoutHome.addDrawerListener(this)
    }


    override fun onDrawerStateChanged(newState: Int) {

    }

    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        val slideX: Float = drawerView.width * slideOffset
        binding.constraintLayoutHomeContent.translationX = slideX
    }

    override fun onDrawerClosed(drawerView: View) {

    }

    override fun onDrawerOpened(drawerView: View) {

    }


    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

            is Navigation.Back -> finish()

            is Navigation.VisitsActivityNav -> navigateToActivity(VisitsActivity::class)
            is Navigation.MeetActivityNav -> navigateToActivity(MeetActivity::class)

            is Navigation.OpenDrawerNavigation -> {
                binding.drawerLayoutHome.openDrawer(binding.navigationViewHome)
            }

            is Navigation.ShareNavigation -> {
                Intent().let {
                    it.action = Intent.ACTION_SEND
                    it.type = "text/plain"
                    it.putExtra(Intent.EXTRA_TEXT, navigationTo.property.toString())
                    startActivity(
                        Intent.createChooser(
                            it,
                            getString(R.string.global_recommend_app)
                        )
                    )
                }
            }

            is Navigation.SignInActivityNavigation -> navigateToActivity(
                SignInActivity::class,
                true
            )


            is Navigation.AdsDetailsActivityNavigation -> {
                Intent(this, AdsDetailsActivity::class.java).let { intent ->
                    intent.putExtra(
                        ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_PROP,
                        navigationTo.value
                    )
                    startActivity(intent)
                }
            }

            is Navigation.ChatActivityNavigation -> {
                navigateToActivity(ChatActivity::class)
            }

            is Navigation.ProfileActivityNavigation -> {
                navigateToActivity(ProfileActivity::class)
            }

            is Navigation.MapsActivityNavigation -> {
                Intent(this, MapsActivity::class.java).let {
                    it.putExtra(
                        ExtraKeys.MapsActivity.SEARCH_EXTRA_KEY,
                        navigationTo.searchResponse
                    )
                    startActivity(it)
                }
            }

            is Navigation.FilterActivityNavigation -> {
                startFilterForResult(navigationTo.searchRequest)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        fetchNotificationsBull()
        viewModel.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                viewModel.onActivityResult(requestCode, resultCode, data)
            },
            1100
        )
    }


    override fun onBackPressed() {
        if (binding.drawerLayoutHome.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayoutHome.closeDrawers()
        } else {
            finish()
        }
    }

}