package com.clickandvisit.ui.shared.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.gridlayout.widget.GridLayout


class RemoveItemByVisibilityGridLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : GridLayout(context, attrs, defStyle) {

    var mChild: Array<View?>? = null

    private fun arrangeElements() {
        mChild = arrayOfNulls(childCount)
        for (i in 0 until childCount) {
            mChild!![i] = getChildAt(i)
        }
        removeAllViews()
        for (i in mChild!!.indices) {
            if (mChild!![i]!!.visibility != GONE) addView(mChild!![i])
        }
        for (i in mChild!!.indices) {
            if (mChild!![i]!!.visibility == GONE) addView(mChild!![i])
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        arrangeElements()
        super.onLayout(changed, left, top, right, bottom)
    }

}