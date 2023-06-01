package com.clickandvisit.ui.home.menu.myads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.clickandvisit.base.BaseFragment
import com.clickandvisit.databinding.FragmentMyadsBinding

class MyAdsFragment : BaseFragment() {

    private var _binding: FragmentMyadsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(MyAdsViewModel::class.java)

        _binding = FragmentMyadsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}