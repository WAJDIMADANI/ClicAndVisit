package com.foodline.ui.shared.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.foodline.R
import kotlin.math.min

/**
 * Created by sazouzi on 24/06/2020
 */

class CustomCircleImageView : AppCompatImageView {
    private val drawableRect = RectF()
    private val borderRect = RectF()
    private val shaderMatrix = Matrix()
    private val bitmapPaint = Paint()
    private val borderPaint = Paint()
    private val fillPaint = Paint()
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH
    private var fillColor = DEFAULT_FILL_COLOR
    private var bitmap: Bitmap? = null
    private var bitmapShader: BitmapShader? = null
    private var bitmapWidth = 0
    private var bitmapHeight = 0
    private var drawableRadius = 0f
    private var borderRadius = 0f
    private var colorF: ColorFilter? = null
    private var ready = false
    private var setupPending = false
    private var borderOverlay = false

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context) {
        init()
    }

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int = 0
    ) : super(context, attrs, defStyle) {
        val a =
            context.obtainStyledAttributes(attrs, R.styleable.CustomCircleImageView, defStyle, 0)
        borderWidth = a.getDimensionPixelSize(
            R.styleable.CustomCircleImageView_civ_border_width,
            DEFAULT_BORDER_WIDTH
        )
        borderColor = a.getColor(
            R.styleable.CustomCircleImageView_civ_border_color,
            DEFAULT_BORDER_COLOR
        )
        borderOverlay = a.getBoolean(
            R.styleable.CustomCircleImageView_civ_border_overlay,
            DEFAULT_BORDER_OVERLAY
        )
        fillColor = a.getColor(
            R.styleable.CustomCircleImageView_civ_fill_color,
            DEFAULT_FILL_COLOR
        )
        a.recycle()
        init()
    }

    private fun init() {
        super.setScaleType(SCALE_TYPE)
        ready = true
        if (setupPending) {
            setup()
            setupPending = false
        }
    }

    override fun getScaleType(): ScaleType {
        return SCALE_TYPE
    }

    override fun setScaleType(scaleType: ScaleType) {
        require(scaleType == SCALE_TYPE) {
            String.format("ScaleType %s not supported.", scaleType)
        }
    }

    override fun setAdjustViewBounds(adjustViewBounds: Boolean) {
        require(!adjustViewBounds) { "adjustViewBounds not supported." }
    }

    override fun onDraw(canvas: Canvas) {
        if (bitmap == null) {
            return
        }
        if (fillColor != Color.TRANSPARENT) {
            canvas.drawCircle(width / 2.0f, height / 2.0f, drawableRadius, fillPaint)
        }
        canvas.drawCircle(width / 2.0f, height / 2.0f, drawableRadius, bitmapPaint)
        if (borderWidth != 0) {
            canvas.drawCircle(width / 2.0f, height / 2.0f, borderRadius, borderPaint)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setup()
    }

    fun getBorderColor(): Int {
        return borderColor
    }

    fun setBorderColor(@ColorInt borderColor: Int) {
        if (borderColor == this.borderColor) {
            return
        }
        this.borderColor = borderColor
        borderPaint.color = this.borderColor
        invalidate()
    }

    fun setBorderColorResource(@ColorRes borderColorRes: Int) {
        setBorderColor(context.resources.getColor(borderColorRes))
    }

    fun getFillColor(): Int {
        return fillColor
    }

    fun setFillColor(@ColorInt fillColor: Int) {
        if (fillColor == this.fillColor) {
            return
        }
        this.fillColor = fillColor
        fillPaint.color = fillColor
        invalidate()
    }

    fun setFillColorResource(@ColorRes fillColorRes: Int) {
        setFillColor(context.resources.getColor(fillColorRes))
    }

    fun getBorderWidth(): Int {
        return borderWidth
    }

    fun setBorderWidth(borderWidth: Int) {
        if (borderWidth == this.borderWidth) {
            return
        }
        this.borderWidth = borderWidth
        setup()
    }

    fun isBorderOverlay(): Boolean {
        return borderOverlay
    }

    fun setBorderOverlay(borderOverlay: Boolean) {
        if (borderOverlay == this.borderOverlay) {
            return
        }
        this.borderOverlay = borderOverlay
        setup()
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        bitmap = bm
        setup()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        bitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        bitmap = getBitmapFromDrawable(drawable)
        setup()
    }

    override fun setImageURI(uri: Uri?) {
        super.setImageURI(uri)
        bitmap = if (uri != null) getBitmapFromDrawable(drawable) else null
        setup()
    }

    override fun setColorFilter(cf: ColorFilter) {
        if (cf === colorF) {
            return
        }
        colorF = cf
        bitmapPaint.colorFilter = colorF
        invalidate()
    }

    private fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }
        return if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else try {
            val bitmap: Bitmap = if (drawable is ColorDrawable) {
                Bitmap.createBitmap(
                    COLOR_DRAWABLE_DIMENSION,
                    COLOR_DRAWABLE_DIMENSION,
                    BITMAP_CONFIG
                )
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    BITMAP_CONFIG
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setup() {
        if (!ready) {
            setupPending = true
            return
        }
        if (width == 0 && height == 0) {
            return
        }
        if (bitmap == null) {
            invalidate()
            return
        }
        bitmapShader = BitmapShader(bitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        bitmapPaint.isAntiAlias = true
        bitmapPaint.shader = bitmapShader
        borderPaint.style = Paint.Style.STROKE
        borderPaint.isAntiAlias = true
        borderPaint.color = borderColor
        borderPaint.strokeWidth = borderWidth.toFloat()
        fillPaint.style = Paint.Style.FILL
        fillPaint.isAntiAlias = true
        fillPaint.color = fillColor
        bitmapHeight = bitmap!!.height
        bitmapWidth = bitmap!!.width
        borderRect[0f, 0f, width.toFloat()] = height.toFloat()
        borderRadius = min(
            (borderRect.height() - borderWidth) / 2.0f,
            (borderRect.width() - borderWidth) / 2.0f
        )
        drawableRect.set(borderRect)
        if (!borderOverlay) {
            drawableRect.inset(borderWidth.toFloat(), borderWidth.toFloat())
        }
        drawableRadius =
            min(drawableRect.height() / 2.0f, drawableRect.width() / 2.0f)
        updateShaderMatrix()
        invalidate()
    }

    private fun updateShaderMatrix() {
        val scale: Float
        var dx = 0f
        var dy = 0f
        shaderMatrix.set(null)
        if (bitmapWidth * drawableRect.height() > drawableRect.width() * bitmapHeight) {
            scale = drawableRect.height() / bitmapHeight.toFloat()
            dx = (drawableRect.width() - bitmapWidth * scale) * 0.5f
        } else {
            scale = drawableRect.width() / bitmapWidth.toFloat()
            dy = (drawableRect.height() - bitmapHeight * scale) * 0.5f
        }
        shaderMatrix.setScale(scale, scale)
        shaderMatrix.postTranslate(
            (dx + 0.5f).toInt() + drawableRect.left,
            (dy + 0.5f).toInt() + drawableRect.top
        )
        bitmapShader!!.setLocalMatrix(shaderMatrix)
    }

    companion object {
        private val SCALE_TYPE = ScaleType.CENTER_CROP
        private val BITMAP_CONFIG = Bitmap.Config.ARGB_8888
        private const val COLOR_DRAWABLE_DIMENSION = 2
        private const val DEFAULT_BORDER_WIDTH = 0
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_FILL_COLOR = Color.TRANSPARENT
        private const val DEFAULT_BORDER_OVERLAY = false
    }

}