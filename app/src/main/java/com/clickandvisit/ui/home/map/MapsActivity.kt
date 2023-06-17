package com.clickandvisit.ui.home.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityMapsBinding
import com.clickandvisit.databinding.FragmentFilterBinding
import com.clickandvisit.global.helper.Navigation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    ///private val viewModel: MapsViewModel by viewModels()

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)

        binding = ActivityMapsBinding.inflate(layoutInflater)

        // registerBindingAndBaseObservers(binding)

        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val sydney = LatLng(48.85, 2.26)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14F))

        mMap.addMarker(
            MarkerOptions()
                .flat(true)
                .icon(
                    bitmapDescriptorFromVector(
                        this,
                        R.drawable.ic_marker
                    )
                )
                .position(sydney)
        )
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
/*
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
*/

}