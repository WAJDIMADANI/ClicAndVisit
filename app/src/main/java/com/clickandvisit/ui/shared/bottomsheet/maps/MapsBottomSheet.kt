package com.clickandvisit.ui.shared.bottomsheet.maps

import android.content.Context
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.global.listener.OnMapsClickedListener


class MapsBottomSheet private constructor(
    val property: Property,
    val userId: Int,
    val onMapsClickedListener: OnMapsClickedListener,
    val okActionBlock: (() -> Unit)? = null,
    val dismissActionBlock: (() -> Unit)? = null
) {

    companion object {

        fun build(
            property: Property,
            userId: Int,
            onMapsClickedListener: OnMapsClickedListener,
            actionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): MapsBottomSheet {
            return MapsBottomSheet(
                property,
                userId,
                onMapsClickedListener,
                actionBlock,
                dismissActionBlock
            )
        }

        fun build(
            context: Context,
            property: Property,
            userId: Int,
            onMapsClickedListener: OnMapsClickedListener,
            okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): MapsBottomSheet {
            return MapsBottomSheet(
                property,
                userId,
                onMapsClickedListener,
                okActionBlock,
                dismissActionBlock
            )
        }

    }

}