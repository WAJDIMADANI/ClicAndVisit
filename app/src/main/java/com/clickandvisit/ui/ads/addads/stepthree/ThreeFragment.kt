package com.clickandvisit.ui.ads.addads.stepthree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ThreeFragmentBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.DebugLog
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThreeFragment(val property: Property?) : BaseFragment() {

    val viewModel: ThreeViewModel by viewModels()

    private lateinit var binding: ThreeFragmentBinding

    private var placeAdapter: PlaceArrayAdapter? = null
    private lateinit var mPlacesClient: PlacesClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.three_fragment, container, false)
        binding = ThreeFragmentBinding.bind(view)
        binding.viewModel = viewModel
        if (property != null) {
            viewModel.onEditProperty(property)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBaseObserver(viewModel)
        Places.initialize(requireContext(), getString(R.string.GOOGLE_MAPS_KEY))
        mPlacesClient = Places.createClient(requireContext())
        placeAdapter =
            PlaceArrayAdapter(requireContext(), R.layout.layout_item_places, mPlacesClient)
        binding.autoCompleteEditText.setAdapter(placeAdapter)

        binding.autoCompleteEditText.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val place = parent.getItemAtPosition(position) as PlaceDataModel

                binding.autoCompleteEditText.apply {
                    setText(getStringBeforeFirstComma(place.fullText))
                    setSelection(binding.autoCompleteEditText.length())
                }

           // Specify the fields you want to retrieve
                val placeFields = listOf(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.ADDRESS,
                    Place.Field.LAT_LNG
                )

           // Construct a request
                val request = FetchPlaceRequest.newInstance(place.placeId!!, placeFields)

          // Fetch the details asynchronously
                val placesClient = Places.createClient(requireContext())
                placesClient.fetchPlace(request)
                    .addOnSuccessListener { response ->

                        binding.etPostalCode.apply {
                            setText(getDigitsFromString(getStringBetweenCommas(response.place.address)))
                        }

                        binding.etCity.apply {
                            setText(removeDigits(getStringBetweenCommas(response.place.address)))
                        }

                    }
                    .addOnFailureListener {
                        DebugLog.e("PlaceDetails", "Error getting place details: ${it.message}")
                    }
            }
    }

    private fun getStringBetweenCommas(inputString: String): String {
        // Find the first and last occurrences of commas
        val firstCommaIndex = inputString.indexOf(',')
        val lastCommaIndex = inputString.lastIndexOf(',')

        // Check if there are at least two commas in the string
        if (firstCommaIndex != -1 && lastCommaIndex > firstCommaIndex) {
            // Extract the substring between the two commas
            return inputString.substring(firstCommaIndex + 1, lastCommaIndex).trim()
        }
        // Return null if there are not enough commas
        return ""
    }

    private fun removeDigits(inputString: String): String {
        return inputString.replace(Regex("\\d"), "")
    }

    private fun getDigitsFromString(inputString: String): String {
        return inputString.filter { it.isDigit() }
    }

    fun getStringBeforeFirstComma(inputString: String): String {
        return inputString.substringBefore(',')
    }

    /**
     * register UI One Observers
     */
    private fun registerOneObservers() {

    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {

    }

}