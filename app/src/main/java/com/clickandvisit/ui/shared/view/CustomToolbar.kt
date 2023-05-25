package com.clickandvisit.ui.shared.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import com.clickandvisit.R
import com.clickandvisit.databinding.CustomToolbarBinding
import com.clickandvisit.global.listener.ToolBarListener


@BindingMethods(
    BindingMethod(
        type = CustomToolbar::class,
        attribute = "app:onActionClicked",
        method = "setToolBarListener"
    )
)
class CustomToolbar : FrameLayout, View.OnClickListener {

    private var toolBarListener: ToolBarListener? = null
    private lateinit var binding: CustomToolbarBinding

    val endActionBtn: AppCompatTextView
        get() {
            return binding.textToolBarAction
        }

    val startActionBtn: AppCompatTextView
        get() {
            return binding.textToolBarStartAction
        }

    constructor(context: Context) : super(context) {
        initialize(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initialize(context, attrs)
    }


    private fun initialize(context: Context, attrs: AttributeSet?) {
        LayoutInflater.from(getContext()).inflate(R.layout.custom_toolbar, this, true)
        binding = CustomToolbarBinding.inflate(LayoutInflater.from(getContext()), this, true)
        binding.layoutToolBarStart.setOnClickListener(this)
        binding.layoutToolBarEnd.setOnClickListener(this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.toolbar_style)

        if (typedArray != null) {

            initBackground(typedArray)

            val hasTitle = typedArray.getBoolean(R.styleable.toolbar_style_ct_has_title, false)
            if (hasTitle) {
                binding.layoutToolBarCenter.visibility = View.VISIBLE
                initTitle(typedArray)
            } else {
                binding.layoutToolBarCenter.visibility = View.GONE
            }

            initStartAction(typedArray)
            initEndAction(typedArray)

            typedArray.recycle()
        }
    }

    private fun initStartAction(typedArray: TypedArray) {

        val actionString = typedArray.getString(R.styleable.toolbar_style_ct_start_action_text)
        val actionTextColor =
            typedArray.getColor(R.styleable.toolbar_style_ct_start_action_text_color, Color.WHITE)
        val actionTextFont =
            typedArray.getString(R.styleable.toolbar_style_ct_start_action_text_font)

        val actionDrawable =
            typedArray.getDrawable(R.styleable.toolbar_style_ct_start_action_drawable)


        if (TextUtils.isEmpty(actionString) && actionDrawable == null) {
            binding.layoutToolBarStart.visibility = View.GONE
        } else {
            binding.layoutToolBarStart.visibility = View.VISIBLE

            if (TextUtils.isEmpty(actionString)) {
                binding.imageToolBarStartAction.visibility = View.VISIBLE
                binding.textToolBarStartAction.visibility = View.GONE
                binding.imageToolBarStartAction.setImageDrawable(actionDrawable)
            } else {
                binding.imageToolBarStartAction.visibility = View.GONE
                binding.textToolBarStartAction.visibility = View.VISIBLE

                binding.textToolBarStartAction.text = actionString
                binding.textToolBarStartAction.setTextColor(actionTextColor)
                binding.textToolBarStartAction.typeface = Typeface.create(actionTextFont, Typeface.BOLD)
            }
        }
    }

    fun showEndActionBtn() {
        binding.textToolBarAction.visibility = View.VISIBLE
        binding.imageToolBarAction.visibility = View.VISIBLE
    }

    fun showStartActionBtn() {
        binding.textToolBarStartAction.visibility = View.VISIBLE
        binding.imageToolBarStartAction.visibility = View.VISIBLE
    }

    fun hideStartActionBtn() {
        binding.textToolBarStartAction.visibility = View.GONE
        binding.imageToolBarStartAction.visibility = View.GONE
    }

    fun hideEndActionBtn() {
        binding.textToolBarAction.visibility = View.GONE
        binding.imageToolBarAction.visibility = View.GONE
    }


    private fun initEndAction(typedArray: TypedArray) {

        val actionString = typedArray.getString(R.styleable.toolbar_style_ct_end_action_text)
        val actionTextColor =
            typedArray.getColor(R.styleable.toolbar_style_ct_end_action_text_color, Color.WHITE)
        val actionTextFont = typedArray.getString(R.styleable.toolbar_style_ct_end_action_text_font)
        val actionDrawable =
            typedArray.getDrawable(R.styleable.toolbar_style_ct_end_action_drawable)


        if (TextUtils.isEmpty(actionString) && actionDrawable == null) {
            binding.layoutToolBarEnd.visibility = View.GONE
        } else {
            binding.layoutToolBarEnd.visibility = View.VISIBLE

            if (TextUtils.isEmpty(actionString)) {
                binding.imageToolBarAction.visibility = View.VISIBLE
                binding.textToolBarAction.visibility = View.GONE
                binding.imageToolBarAction.setImageDrawable(actionDrawable)
            } else {
                binding.imageToolBarAction.visibility = View.GONE
                binding.textToolBarAction.visibility = View.VISIBLE
                binding.textToolBarAction.text = actionString
                binding.textToolBarAction.setTextColor(actionTextColor)
                binding.textToolBarAction.typeface = Typeface.create(actionTextFont, Typeface.BOLD)
            }
        }
    }

    private fun initTitle(typedArray: TypedArray) {

        val title = typedArray.getString(R.styleable.toolbar_style_ct_title_text)
        val titleColor = typedArray.getColor(R.styleable.toolbar_style_ct_title_color, Color.BLACK)

        val icon = typedArray.getDrawable(R.styleable.toolbar_style_ct_title_drawable)

        if (!TextUtils.isEmpty(title)) {
            binding.imageToolBarTitle.visibility = View.VISIBLE
            binding.imageToolBarTitle.visibility = View.GONE
            binding.textToolBarTitle.text = title
            binding.textToolBarTitle.setTextColor(titleColor)

        } else {
            binding.imageToolBarTitle.visibility = View.GONE
            binding.imageToolBarTitle.visibility = View.VISIBLE
            binding.imageToolBarTitle.setImageDrawable(icon)
        }
    }

    private fun initBackground(typedArray: TypedArray) {
        val backgroundColor =
            typedArray.getColor(R.styleable.toolbar_style_ct_background, Color.RED)
        setBackgroundColor(backgroundColor)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.layoutToolBarStart -> toolBarListener!!.onStartClicked()
            R.id.layoutToolBarEnd -> toolBarListener!!.onEndClicked()
        }
    }

    fun setToolBarListener(toolBarListener: ToolBarListener) {
        this.toolBarListener = toolBarListener
    }
}
