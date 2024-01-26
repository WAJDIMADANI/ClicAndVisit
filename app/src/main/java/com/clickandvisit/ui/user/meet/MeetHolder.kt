package com.clickandvisit.ui.user.meet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.data.model.reservation.Reservation
import com.clickandvisit.databinding.ItemMeetBinding
import com.clickandvisit.global.listener.OnMeetClickedListener
import com.clickandvisit.global.utils.getWsDate
import com.clickandvisit.global.utils.getWsTime
import com.clickandvisit.global.utils.toMediaUrl
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso


class MeetHolder(
    private val binding: ItemMeetBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val onMeetClickedListener: OnMeetClickedListener,
    private val isFromMeet: Boolean
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(value: Reservation, position: Int) {

        binding.tvAdsName.text = "${value.category} - ${value.city}"
        if (isFromMeet){
            binding.tvAdsAddress.text = "${value.road},\n ${value.postalCode} ${value.city}"
        }else{
            binding.tvAdsAddress.visibility = View.GONE
        }

        binding.tvAdsPrice.text =  value.getPriceNBR()
        binding.tvAdsDate.text = getWsDate(value.reservationDetails?.dateTime)
        binding.tvAdsHour.text = "à " + getWsTime(value.reservationDetails?.dateTime)

        if (isFromMeet.not()) {
            if (value.owner.name.isEmpty().not()) {
                binding.tvAdsMeetUserName.text = value.owner.name
            } else {
                binding.tvAdsMeetUserName.visibility = View.INVISIBLE
            }

            if (value.owner.photo.toMediaUrl() != "https://")
                picasso.load(value.owner.photo.toMediaUrl()).fit().centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(binding.customCircleUserPhoto)
        } else {
            if (value.reservationUser?.userName.isNullOrEmpty().not()) {
                binding.tvAdsMeetUserName.text = value.reservationUser?.userName
            } else {
                binding.tvAdsMeetUserName.visibility = View.INVISIBLE
            }

            if (value.reservationUser?.profilePhoto.toMediaUrl() != "https://")
                picasso.load(value.reservationUser?.profilePhoto.toMediaUrl()).fit().centerCrop()
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(binding.customCircleUserPhoto)
        }



        if (value.mainPhoto.toMediaUrl() != "https://")
            picasso.load(value.mainPhoto.toMediaUrl()).fit().centerCrop()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(binding.appCompatImageView2)

        if (value.reservationDetails?.statusCode == 1) {
            //binding.tvAdsMeetTitle.text = context.getString(R.string.meet_with)

            binding.tvConfirmMeet.visibility = View.INVISIBLE
            binding.tvRejectMeet.visibility = View.INVISIBLE
            binding.tvCancelMeet.visibility = View.VISIBLE
            binding.customCircleUserPhoto.visibility = View.VISIBLE

            if (value.reservationUser?.userPhone.isNullOrEmpty().not()) {
                binding.tvAdsMeetUserPhone.text = "${value.reservationUser?.userPhone}"
            } else {
                binding.tvAdsMeetUserPhone.visibility = View.GONE
            }
            binding.tvAdsMeetUserPhone.visibility = View.VISIBLE

        } else {
            //binding.tvAdsMeetTitle.text = context.getString(R.string.meet_with)
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
            onMeetClickedListener: OnMeetClickedListener,
            isFromMeet: Boolean
        ): MeetHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemMeetBinding.inflate(inflater, parent, false)
            return MeetHolder(
                binding,
                context,
                picasso,
                onMeetClickedListener,
                isFromMeet
            )
        }
    }

}