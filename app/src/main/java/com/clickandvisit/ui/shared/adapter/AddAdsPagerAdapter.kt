package com.clickandvisit.ui.shared.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.clickandvisit.databinding.ActivityAddAdsBinding
import com.clickandvisit.ui.ads.addads.five.CalendarFragment
import com.clickandvisit.ui.ads.addads.stepone.OneFragment
import com.clickandvisit.ui.ads.addads.stepthree.ThreeFragment
import com.clickandvisit.ui.ads.addads.stepfour.FourFragment
import com.clickandvisit.ui.ads.addads.steptwo.TwoFragment

class AddAdsPagerAdapter(
    fragment: FragmentActivity,
    val binding: ActivityAddAdsBinding,
    val context: Context
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                return OneFragment()
            }
            1 -> {
                return TwoFragment()
            }
            2 -> {
                return ThreeFragment()
            }
            3 -> {
                return FourFragment()
            }
            4 -> {
                return CalendarFragment()
            }
            else -> return OneFragment()
        }
    }


}