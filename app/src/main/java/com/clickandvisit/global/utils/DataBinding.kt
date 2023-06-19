package com.clickandvisit.global.utils

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

/**
 * Created by sazouzi on 24/06/2023
 */


@BindingAdapter("spinnerEntries")
fun Spinner.setEntries(entries: List<String>?) {
    setSpinnerEntries(entries)
}

@BindingAdapter("selectedValue")
fun Spinner.setSelectedValue(selectedValue: String?) {
    setSpinnerValue(selectedValue)
}

@BindingAdapter("selectedValueAttrChanged")
fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
    setSpinnerInverseBindingListener(inverseBindingListener)
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun Spinner.getSelectedValue(): Any? {
    return getSpinnerValue()
}