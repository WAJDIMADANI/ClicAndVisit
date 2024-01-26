package com.clickandvisit.ui.ads.adslist


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityAdsListBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.ui.ads.addads.AddAdsActivity
import com.clickandvisit.ui.ads.adsdetails.AdsDetailsActivity
import com.clickandvisit.ui.shared.view.CustomButton
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AdsListActivity : BaseActivity() {

    private val viewModel: AdsListViewModel by viewModels()

    private lateinit var binding: ActivityAdsListBinding

    @Inject
    lateinit var adapter: PropertyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ads_list)
        registerBindingAndBaseObservers(binding)
        registerRecycler(binding)
    }

    private fun registerRecycler(binding: ActivityAdsListBinding) {
        adapter.viewModel = viewModel
        binding.rvAds.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvAds.adapter = adapter
        enableSwipeToDeleteAndUndo()
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, i: Int) {
                viewModel.deleteProp(adapter.list[viewHolder.adapterPosition].id,viewHolder.adapterPosition )
            }
        }
        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(binding.rvAds)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

            is Navigation.Back -> finish()

            is Navigation.AddAdsActivity -> {
                Intent(this, AddAdsActivity::class.java).let { intent ->
                    intent.putExtra(
                        ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_EDIT,
                        true
                    )
                    intent.putExtra(
                        ExtraKeys.AddAdsActivity.PROPERTY_EXTRA_KEY_MEET,
                        navigationTo.isMeet
                    )
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
                    it.putExtra(Intent.EXTRA_TEXT, getString(R.string.global_recommend_app) +  navigationTo.property.toShare())
                    startActivity(
                        Intent.createChooser(
                            it,
                            getString(R.string.global_recommend_app)
                        )
                    )
                }
            }

            is Navigation.RateNav -> {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_rate_layout)
                dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.setCancelable(false)
                dialog.findViewById<CustomButton>(R.id.customButton).setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
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
    private fun registerBindingAndBaseObservers(binding: ActivityAdsListBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}