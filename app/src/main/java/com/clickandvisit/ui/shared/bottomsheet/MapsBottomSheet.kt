package com.clickandvisit.ui.shared.bottomsheet

import android.content.Context
import com.clickandvisit.data.model.property.Property
import com.clickandvisit.global.listener.OnMapsClickedListener



class MapsBottomSheet private constructor(
    val property: Property,
    val onMapsClickedListener: OnMapsClickedListener,
    val okActionBlock: (() -> Unit)? = null,
    val dismissActionBlock: (() -> Unit)? = null
) {

    companion object {

        fun build(
            property: Property,
            onMapsClickedListener: OnMapsClickedListener,
            actionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): MapsBottomSheet {
            return MapsBottomSheet(
                property,
                onMapsClickedListener,
                actionBlock,
                dismissActionBlock
            )
        }

        fun build(
            context: Context,
            property: Property,
            onMapsClickedListener: OnMapsClickedListener,
            okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): MapsBottomSheet {
            return MapsBottomSheet(
                property,
                onMapsClickedListener,
                okActionBlock,
                dismissActionBlock
            )
        }

    }

}