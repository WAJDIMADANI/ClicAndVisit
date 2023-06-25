package com.clickandvisit.ui.user.meet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.R
import com.clickandvisit.data.model.reservation.Reservation
import com.clickandvisit.databinding.ItemMeetBinding
import com.clickandvisit.global.listener.OnMeetClickedListener
import com.clickandvisit.global.utils.getWsDate
import com.clickandvisit.global.utils.getWsTime
import com.clickandvisit.global.utils.toMediaUrl
import com.squareup.picasso.Picasso


class MeetHolder(
    private val binding: ItemMeetBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val onMeetClickedListener: OnMeetClickedListener
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(value: Reservation, position: Int) {

        binding.tvAdsName.text = "${value.category} - ${value.city}"
        binding.tvAdsAddress.text = "${value.road},\n ${value.postalCode} ${value.city}"
        binding.tvAdsPrice.text = "${value.price} €"
        binding.tvAdsDate.text = getWsDate(value.reservationDetails?.dateTime)
        binding.tvAdsHour.text = "à " + getWsTime(value.reservationDetails?.dateTime)
        binding.tvAdsMeetUserName.text = "${value.reservationUser?.userName}"

        if (value.mainPhoto.toMediaUrl() != "https://")
            picasso.load(value.mainPhoto.toMediaUrl()).into(binding.appCompatImageView2)

        if (value.reservationDetails?.statusCode == 1) {
            binding.tvAdsMeetTitle.text = context.getString(R.string.confirmed_meet_with)
            binding.tvAdsMeetUserPhone.text = "${value.reservationUser?.userPhone}"
            binding.tvAdsMeetUserPhone.visibility = View.VISIBLE
            binding.tvConfirmMeet.visibility = View.INVISIBLE
            binding.tvRejectMeet.visibility = View.INVISIBLE
            binding.tvCancelMeet.visibility = View.VISIBLE
            binding.customCircleUserPhoto.visibility = View.VISIBLE

            if (value.reservationUser?.profilePhoto.toMediaUrl() != "https://")
                picasso.load(value.reservationUser?.profilePhoto.toMediaUrl())
                    .into(binding.customCircleUserPhoto)
        } else {
            binding.tvAdsMeetTitle.text = context.getString(R.string.meet_with)
            binding.tvAdsMeetUserPhone.visibility = View.INVISIBLE
            binding.tvConfirmMeet.visibility = View.VISIBLE
            binding.tvRejectMeet.visibility = View.VISIBLE
            binding.tvCancelMeet.visibility = View.INVISIBLE
            binding.customCircleUserPhoto.visibility = View.INVISIBLE
        }

        binding.reservation = value
        binding.listener = onMeetClickedListener

        binding.executePendingBindings()
    }


    companion object {
        fun create(
            parent: ViewGroup,
            context: Context,
            picasso: Picasso,
            onMeetClickedListener: OnMeetClickedListener
        ): MeetHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMeetBinding.inflate(inflater, parent, false)
            return MeetHolder(
                binding,
                context,
                picasso,
                onMeetClickedListener
            )
        }
    }

}