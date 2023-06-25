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
) : RecyclerView.ViewHolder(binding.root) {//TODO: query: mes rendez-vous vs mes visites


    fun bind(value: Reservation, position: Int) {

        binding.tvAdsName.text = "${value.category} - ${value.city}"
        binding.tvAdsAddress.text = "${value.road},\n ${value.postalCode} ${value.city}"
        binding.tvAdsPrice.text = "${value.price} €"
        binding.tvAdsDate.text = getWsDate(value.reservationDetails?.dateTime)
        binding.tvAdsHour.text = "à " + getWsTime(value.reservationDetails?.dateTime)

        if (value.owner.name.isNullOrEmpty().not()){
            binding.tvAdsMeetUserName.text = value.owner.name
        }else{
            binding.tvAdsMeetUserName.visibility = View.INVISIBLE
        }

        if (value.reservationUser?.profilePhoto.toMediaUrl() != "https://")
            picasso.load(value.reservationUser?.profilePhoto.toMediaUrl())
                .into(binding.customCircleUserPhoto)


        if (value.mainPhoto.toMediaUrl() != "https://")
            picasso.load(value.mainPhoto.toMediaUrl()).into(binding.appCompatImageView2)

        if (value.reservationDetails?.statusCode == 1) {
            binding.tvAdsMeetTitle.text = context.getString(R.string.confirmed_meet_with)
            if (value.reservationUser?.userPhone.isNullOrEmpty().not()){
                binding.tvAdsMeetUserPhone.text = "${value.reservationUser?.userPhone}"
            }else{
                binding.tvAdsMeetUserPhone.visibility = View.INVISIBLE
            }
            binding.tvAdsMeetUserPhone.visibility = View.VISIBLE
            binding.tvConfirmMeet.visibility = View.INVISIBLE
            binding.tvRejectMeet.visibility = View.INVISIBLE
            binding.tvCancelMeet.visibility = View.VISIBLE
            binding.customCircleUserPhoto.visibility = View.VISIBLE

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