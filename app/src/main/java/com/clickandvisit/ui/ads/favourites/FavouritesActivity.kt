package com.clickandvisit.ui.ads.favourites

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityFavouritesBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.ui.ads.adsdetails.AdsDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesActivity : BaseActivity() {

    private val viewModel: FavouritesViewModel by viewModels()

    private lateinit var binding: ActivityFavouritesBinding

    @Inject
    lateinit var adapter: FavouritesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourites)
        registerBindingAndBaseObservers(binding)
        registerRecycler(binding)
    }

    private fun registerRecycler(binding: ActivityFavouritesBinding) {
        adapter.viewModel = viewModel
        adapter.userId = viewModel.getUserId()
        binding.rvSearch.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvSearch.adapter = adapter
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()

            is Navigation.AdsDetailsActivityNavigation -> {
                Intent(this, AdsDetailsActivity::class.java).let { intent ->
                    intent.putExtra(
                        ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_PROP,
                        navigationTo.value
                    )
                    startActivity(intent)
                }
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


        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityFavouritesBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}