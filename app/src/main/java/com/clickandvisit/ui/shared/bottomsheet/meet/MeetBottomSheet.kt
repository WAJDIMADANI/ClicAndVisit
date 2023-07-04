package com.clickandvisit.ui.shared.bottomsheet.meet

import android.content.Context
import com.clickandvisit.global.listener.OnSendClickedListener


class MeetBottomSheet private constructor(
    val title: String,
    val hint: String,
    val onSendClickedListener: OnSendClickedListener,
    val okActionBlock: (() -> Unit)? = null,
    val dismissActionBlock: (() -> Unit)? = null
) {

    companion object {

        fun build(
            title: String,
            hint: String,
            onSendClickedListener: OnSendClickedListener,
            actionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): MeetBottomSheet {
            return MeetBottomSheet(
                title,
                hint,
                onSendClickedListener,
                actionBlock,
                dismissActionBlock
            )
        }

        fun build(
            context: Context,
            title: String,
            hint: String,
            onSendClickedListener: OnSendClickedListener,
            okActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): MeetBottomSheet {
            return MeetBottomSheet(
                title,
                hint,
                onSendClickedListener,
                okActionBlock,
                dismissActionBlock
            )
        }

    }

}