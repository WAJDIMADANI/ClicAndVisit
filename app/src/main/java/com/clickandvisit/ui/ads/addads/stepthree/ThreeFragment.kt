package com.clickandvisit.ui.ads.addads.stepthree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.viewModels
import com.clickandvisit.R
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.databinding.ThreeFragmentBinding
import com.clickandvisit.global.helper.Navigation
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.Places
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
        if (property != null){
            viewModel.onEditProperty(property)
        }
        binding.lifecycleOwner = viewLifecycleOwner
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerBaseObserver(viewModel)
        Places.initialize(requireContext(), getString(R.string.GOOGLE_MAPS_KEY))//TODO
        mPlacesClient = Places.createClient(requireContext())
        placeAdapter =
            PlaceArrayAdapter(requireContext(), R.layout.layout_item_places, mPlacesClient)
        binding.autoCompleteEditText.setAdapter(placeAdapter)

        binding.autoCompleteEditText.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val place = parent.getItemAtPosition(position) as PlaceDataModel
                binding.autoCompleteEditText.apply {
                    setText(place.fullText)
                    setSelection(binding.autoCompleteEditText.length())
                }
            }
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