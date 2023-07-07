package com.clickandvisit.ui.ads.addads.stepfour

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.clickandvisit.databinding.ItemGalleryImagesBinding
import com.clickandvisit.global.listener.CancelClickedListener
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso


class ImageHolder(
    private val binding: ItemGalleryImagesBinding,
    private val context: Context,
    private val picasso: Picasso,
    private val cancelClickedListener: CancelClickedListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(value: Uri, position: Int) {

        Picasso.get().load(value)
            .memoryPolicy(
                MemoryPolicy.NO_CACHE,
                MemoryPolicy.NO_STORE
            )
            .fit()
            .centerInside()
            .into(binding.iv1)

        binding.ivCancel.setOnClickListener {
            cancelClickedListener.onCancelImageClick(position)
        }

        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup,
            context: Context,
            picasso: Picasso,
            cancelClickedListener: CancelClickedListener
        ): ImageHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGalleryImagesBinding.inflate(inflater, parent, false)
            return ImageHolder(
                binding,
                context,
                picasso,
                cancelClickedListener
            )
        }
    }
}