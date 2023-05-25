package com.clickandvisit.ui.shared.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.databinding.ItemListFooterBinding
import com.clickandvisit.global.helper.PaginationState
import com.clickandvisit.global.listener.RetryListener


class ListFooterViewHolder(
    private val binding: ItemListFooterBinding,
    private val retryListener: RetryListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(status: PaginationState) {
        binding.retryListener = retryListener
        binding.isLoading = status== PaginationState.Loading
        binding.isError = status is PaginationState.LoadingError
        binding.executePendingBindings()
    }


    companion object {
        fun create(parent: ViewGroup, retryListener: RetryListener): ListFooterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemListFooterBinding.inflate(inflater, parent, false)
            return ListFooterViewHolder(binding, retryListener)
        }
    }
}
