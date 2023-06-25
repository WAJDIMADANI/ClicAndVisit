package com.clickandvisit.ui.home.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityMapsBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.DebugLog
import com.clickandvisit.global.utils.TAG
import com.clickandvisit.global.utils.observeOnlyNotNull
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapsActivity : BaseActivity(), OnMapReadyCallback {

    private val viewModel: MapsViewModel by viewModels()

    private lateinit var map: GoogleMap

    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        registerBindingAndBaseObservers(binding)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(47.370208, 3.419315), 7F))

        viewModel.searchResponse.observeOnlyNotNull(this) { its ->
            its.properties.forEach {prop ->

                val vectorResId = if (prop.visitNow) {
                    R.drawable.ic_baseline_location_green
                } else {
                    R.drawable.ic_baseline_location_on
                }
                map.addMarker(
                    MarkerOptions()
                        .flat(true)
                        .icon(
                            bitmapDescriptorFromVector(
                                this,
                                vectorResId
                            )
                        )
                        .position(LatLng(prop.lat.toDouble(), prop.long.toDouble()))
                )

                map.setOnMarkerClickListener { _ ->
                    DebugLog.i(TAG, prop.id.toString())
                    true
                }

            }
        }
    }

    /** get BitmapDescriptor marker icon from vector res id **/
    private fun bitmapDescriptorFromVector(
        context: Context,
        vectorResId: Int
    ): BitmapDescriptor? {
        val vectorDrawable =
            ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityMapsBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {

            is Navigation.Back -> finish()

        }
    }

}