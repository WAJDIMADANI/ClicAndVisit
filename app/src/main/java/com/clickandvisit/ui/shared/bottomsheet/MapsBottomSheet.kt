package com.clickandvisit.ui.shared.bottomsheet

import android.content.Context
import com.clickandvisit.data.model.property.Property

/**
 * Created by sazouzi on 08/07/2020
 */

class MapsBottomSheet private constructor(
    val property: Property,
    val okActionBlock: (() -> Unit)? = null,
    val dismissActionBlock: (() -> Unit)? = null
) {

    companion object {

        fun build(
            property: Property,
            actionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): MapsBottomSheet {
            return MapsBottomSheet(
                property,
                actionBlock,
                dismissActionBlock
            )
        }

        fun build(
            context: Context,
            property: Property,
            okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): MapsBottomSheet {
            return MapsBottomSheet(
                property,
                okActionBlock,
                dismissActionBlock
            )
        }

    }

}