package com.clickandvisit.ui.shared.dialog


class ImgPickerDialog private constructor(
    val takePicActionBlock: (() -> Unit)? = null,
    val pickPicActionBlock: (() -> Unit)? = null,
    val dismissActionBlock: (() -> Unit)? = null
) {
    companion object {
        fun build(
            takePicActionBlock: (() -> Unit)? = null,
            pickPicActionBlock: (() -> Unit)? = null,
            dismissActionBlock: (() -> Unit)? = null
        ): ImgPickerDialog {
            return ImgPickerDialog (
                takePicActionBlock,
                pickPicActionBlock,
                dismissActionBlock
            )
        }
    }
}