package com.clickandvisit.ui.shared.bottomsheet

import android.content.Context
import com.clickandvisit.data.model.Card

/**
 * Created by sazouzi on 08/07/2020
 */

class SignUpBottomSheet private constructor(
    val card: Card?,
    val okActionBlock: (() -> Unit)? = null,
    val dismissActionBlock: (() -> Unit)? = null
) {

    companion object {

        fun build(
            card: Card?,
            actionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): SignUpBottomSheet {
            return SignUpBottomSheet(
                card,
                actionBlock,
                dismissActionBlock
            )
        }

        fun build(
            context: Context,
            card: Card?,
            okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): SignUpBottomSheet {
            return SignUpBottomSheet(
                card,
                okActionBlock,
                dismissActionBlock
            )
        }

    }

}