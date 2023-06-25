package com.clickandvisit.ui.ads.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.data.model.property.SearchData
import com.clickandvisit.databinding.ItemSearchBinding
import com.clickandvisit.global.listener.OnSearchClickedListener
import com.squareup.picasso.Picasso


class MySearchHolder(
    private val binding: ItemSearchBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val onSearchClickedListener: OnSearchClickedListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(value: SearchData, position: Int) {
        binding.tvSearchCity.text = value.data.getAddress()
        binding.tvSearchDate.text = value.getDateWithFormat()
        binding.tvAdsPrice.text = value.data.getPrice()
        binding.tvAdsRoomsNbr.text = value.data.getRoomsNbr()
        binding.tvAdsSpace.text = value.data.getArea()
        binding.tvSearchTypes.text = value.data.getStringTypesById()

        var isChecked = false
        binding.ivNotify.setOnClickListener {
            if (isChecked.not()) {
                binding.ivNotify.setBackgroundColor(context.getColor(R.color.gray))
                binding.ivNotify.setImageResource(R.drawable.ic_baseline_notifications)
            } else {
                binding.ivNotify.setBackgroundColor(context.getColor(R.color.light_grey))
                binding.ivNotify.setImageResource(R.drawable.ic_baseline_notifications_off)
            }
            isChecked = isChecked.not()
        }

        binding.data = value

        binding.listener = onSearchClickedListener
        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            context: Context,
            picasso: Picasso,
            onSearchClickedListener: OnSearchClickedListener
        ): MySearchHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemSearchBinding.inflate(inflater, parent, false)
            return MySearchHolder(
                binding,
                context,
                picasso,
                onSearchClickedListener
            )
        }
    }

}