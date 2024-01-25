package com.clickandvisit.ui.ads.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.EditText
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.clickandvisit.R
import com.clickandvisit.base.BaseActivity
import com.clickandvisit.databinding.ActivityFilterBinding
import com.clickandvisit.global.helper.Navigation
import com.clickandvisit.global.utils.ExtraKeys
import com.clickandvisit.ui.ads.addads.stepone.SpaceTextWatcher
import com.clickandvisit.ui.ads.addads.stepthree.PlaceArrayAdapter
import com.clickandvisit.ui.ads.addads.stepthree.PlaceDataModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FilterActivity : BaseActivity() {

    private val viewModel: FilterViewModel by viewModels()

    private lateinit var binding: ActivityFilterBinding

    private var placeAdapter: PlaceArrayAdapter? = null
    private lateinit var mPlacesClient: PlacesClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter)
        registerBindingAndBaseObservers(binding)

        Places.initialize(applicationContext, getString(R.string.GOOGLE_MAPS_KEY))//TODO
        mPlacesClient = Places.createClient(applicationContext)
        placeAdapter =
            PlaceArrayAdapter(applicationContext, R.layout.layout_item_places, mPlacesClient)
        binding.autoCompleteEditText.setAdapter(placeAdapter)

        binding.autoCompleteEditText.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val place = parent.getItemAtPosition(position) as PlaceDataModel
                binding.autoCompleteEditText.apply {
                    setText(place.fullText.subSequence(0,place.fullText.indexOf(',')).toString().toUpperCase())
                    setSelection(binding.autoCompleteEditText.length())
                }
            }

        binding.etPriceMin.addTextChangedListener(SpaceTextWatcher(binding.etPriceMin))
        binding.etPriceMax.addTextChangedListener(SpaceTextWatcher(binding.etPriceMax))
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> {
                val intent = Intent()
                setResult(Activity.RESULT_CANCELED, intent)
                finish()
            }
            is Navigation.HomeActivityNavigationData -> {
                val intent = Intent()
                 intent.putExtra(
                     ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY,
                     navigationTo.searchRequest
                 )
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        }
    }

    private fun onBackNavigation() {
        val intent = Intent()
        intent.putExtra(ExtraKeys.FilterActivity.SEARCH_REQ_EXTRA_KEY, viewModel.searchRequest.value)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityFilterBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}
class SpaceTextWatcher(private val editText: EditText) : TextWatcher {
    private var previousText = ""

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Not needed for this implementation
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Not needed for this implementation
    }

    override fun afterTextChanged(s: Editable?) {
        val newText = s.toString().replace("\\s".toRegex(), "")

        if (newText != previousText) {
            editText.removeTextChangedListener(this)

            val sb = StringBuilder()
            newText.forEachIndexed { index, char ->
                if (index > 0 && index % 3 == 0) {
                    sb.append(' ')
                }
                sb.append(char)
            }

            editText.setText(sb.toString())
            editText.setSelection(sb.length)

            editText.addTextChangedListener(this)

            previousText = sb.toString()
        }
    }
}