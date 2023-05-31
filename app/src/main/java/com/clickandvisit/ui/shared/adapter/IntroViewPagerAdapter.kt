package com.clickandvisit.ui.shared.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.clickandvisit.databinding.ActivityIntroBinding
import com.clickandvisit.ui.intro.stepone.OneFragment
import com.clickandvisit.ui.intro.stepthree.ThreeFragment
import com.clickandvisit.ui.intro.steptwo.TwoFragment

class IntroViewPagerAdapter(
    fragment: FragmentActivity,
    val binding: ActivityIntroBinding,
    val context: Context
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

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
            else -> return OneFragment()
        }
    }


}