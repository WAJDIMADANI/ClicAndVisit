package com.clickandvisit.ui.user.visits

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityVisitsBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.ui.ads.adsdetails.AdsDetailsActivity
import com.clickandvisit.ui.user.meet.MeetAdapter
import com.clickandvisit.ui.user.meet.MeetViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class VisitsActivity : BaseActivity() {

    private val viewModel: MeetViewModel by viewModels()

    private lateinit var binding: ActivityVisitsBinding

    @Inject
    lateinit var adapter: MeetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_visits)
        registerBindingAndBaseObservers(binding)
        registerRecycler(binding)
    }


    private fun registerRecycler(binding: ActivityVisitsBinding) {
        adapter.viewModel = viewModel
        adapter.isFromMeet = false
        binding.rvMeet.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvMeet.adapter = adapter
        viewModel.getReservations(true)
        viewModel.setVisits(false)
    }


    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

            is Navigation.Back -> finish()

            is Navigation.GoToMapsNavigation -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:${navigationTo.reservation.road} ${navigationTo.reservation.city}")
                )
                startActivity(intent)
            }

            is Navigation.Phone -> {
                val uri = "tel:" + navigationTo.phoneNumber.trim()
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(uri)
                startActivity(intent)
            }

            is Navigation.VisitsNavigation -> {
                viewModel.getPropertyDetails(navigationTo.propertyId.toInt())
            }

            is Navigation.AdsDetailsActivityNavigation -> {
                Intent(this, AdsDetailsActivity::class.java).let { intent ->
                    intent.putExtra(
                        ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_PROP,
                        navigationTo.value
                    )
                    startActivity(intent)
                }
            }

        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityVisitsBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}