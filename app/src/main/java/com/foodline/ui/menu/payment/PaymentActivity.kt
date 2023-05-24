package com.foodline.ui.menu.payment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodline.R
import com.foodline.base.BaseActivity
import com.foodline.databinding.ActivityPaymentBinding
import com.foodline.global.helper.Navigation
import com.foodline.ui.shared.adapter.CardAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PaymentActivity : BaseActivity() {

    private val viewModel: PaymentViewModel by viewModels()

    private lateinit var binding: ActivityPaymentBinding

    @Inject
    lateinit var cardAdapter: CardAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        registerRecycler()
        registerBindingAndBaseObservers(binding)
    }

    private fun registerRecycler() {
        cardAdapter.onEditClickListener = viewModel
        cardAdapter.onAddCardClickListener = viewModel
        cardAdapter.viewModel = viewModel
        binding.recyclerCard.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerCard.adapter = cardAdapter
    }

    /**
     * handling navigation event
     */
    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {
            is Navigation.Back -> finish()

        }
    }

    /**
     * Register the UI for XMLBinding
     * Register the activity for base observers
     */
    private fun registerBindingAndBaseObservers(binding: ActivityPaymentBinding) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        registerBaseObservers(viewModel)
    }

}