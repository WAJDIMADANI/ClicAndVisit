package com.clickandvisit.ui.shared.bottomsheet.filter

import android.content.Context
import com.clickandvisit.global.listener.OnFilterClickedListener


class FilterBottomSheet private constructor(
    val onFilterClickedListener: OnFilterClickedListener,
    val okActionBlock: (() -> Unit)? = null,
    val dismissActionBlock: (() -> Unit)? = null
) {

    companion object {

        fun build(
            onFilterClickedListener: OnFilterClickedListener,
            actionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): FilterBottomSheet {
            return FilterBottomSheet(
                onFilterClickedListener,
                actionBlock,
                dismissActionBlock
            )
        }

        fun build(
            context: Context,
            onFilterClickedListener: OnFilterClickedListener,
            okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): FilterBottomSheet {
            return FilterBottomSheet(
                onFilterClickedListener,
                okActionBlock,
                dismissActionBlock
            )
        }

    }

}