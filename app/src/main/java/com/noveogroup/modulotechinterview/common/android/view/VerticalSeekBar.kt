package com.noveogroup.modulotechinterview.common.android.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.databinding.ViewVerticalSliderBinding
import kotlin.math.roundToInt

@SuppressLint("ClickableViewAccessibility")
open class VerticalSeekBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_MAX_VALUE = 100
        private const val DEFAULT_PROGRESS = 0
    }

    private var onProgressChangeListener: ((Int) -> Unit)? = null
    private var onPressListener: ((Int) -> Unit)? = null
    private var onReleaseListener: ((Int) -> Unit)? = null

    private val binding =
        ViewVerticalSliderBinding.inflate(LayoutInflater.from(context), this, true)

    var barBackgroundColor: Int
        get() = binding.barBackground.solidColor
        set(value) {
            binding.barBackground.setBackgroundColor(value)
        }

    var barProgressColor: Int
        get() = binding.barProgress.solidColor
        set(value) {
            binding.barProgress.setBackgroundColor(value)
        }

    var maxValue = DEFAULT_MAX_VALUE
        set(value) {
            val newValue = when {
                value < 1 -> 1
                else -> value
            }
            if (progress > newValue) progress = newValue
            field = newValue
            updateViews()
        }
    var progress: Int = DEFAULT_PROGRESS
        set(value) {
            val newValue = when {
                value < 0 -> 0
                value > maxValue -> maxValue
                else -> value
            }
            if (field != newValue) {
                onProgressChangeListener?.invoke(newValue)
            }
            field = newValue
            updateViews()
        }

    private var yDelta: Int = 0

    init {
        attrs?.apply {
            val attributes =
                context.obtainStyledAttributes(attrs, R.styleable.VerticalSeekBar, 0, 0)
            try {
                barBackgroundColor =
                    attributes.getColor(
                        R.styleable.VerticalSeekBar_vsb_bar_background_color,
                        barBackgroundColor
                    )
                barProgressColor =
                    attributes.getColor(
                        R.styleable.VerticalSeekBar_vsb_bar_progress_color,
                        barProgressColor
                    )
                attributes.getInt(R.styleable.VerticalSeekBar_vsb_max_value, maxValue).also {
                    maxValue = it
                }
                attributes.getInt(R.styleable.VerticalSeekBar_vsb_progress, progress).also {
                    progress = it
                }
            } finally {
                attributes.recycle()
            }
        }
        with(binding) {
            thumb.setOnTouchListener { thumb, event ->
                val border = container.measuredHeight.toFloat() * 0.01
                val rawY = event.rawY.roundToInt()
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        yDelta = rawY -
                                (thumb.layoutParams as LayoutParams).topMargin -
                                thumb.measuredHeight / 2
                        onPressListener?.invoke(progress)
                    }

                    MotionEvent.ACTION_MOVE -> {
                        if (yDelta > border) {
                            val positionY = rawY - yDelta
                            val fillHeight = binding.container.measuredHeight
                            when {
                                positionY in 1 until fillHeight -> {
                                    val newValue =
                                        maxValue - (positionY.toFloat() * maxValue / fillHeight)
                                    progress = newValue.roundToInt()
                                }
                                positionY <= 0 -> progress = maxValue
                                positionY >= fillHeight -> progress = 0
                            }
                        }
                    }

                    MotionEvent.ACTION_UP -> onReleaseListener?.invoke(progress)
                }
                true
            }
            container.setOnTouchListener { bar, event ->
                val positionY = event.y.roundToInt()
                val action = {
                    val fillHeight = bar.measuredHeight
                    when {
                        positionY in 1 until fillHeight -> {
                            val newValue = maxValue - (positionY.toFloat() * maxValue / fillHeight)
                            progress = newValue.roundToInt()
                        }
                        positionY <= 0 -> progress = maxValue
                        positionY >= fillHeight -> progress = 0
                    }
                }
                when (event.action) {

                    MotionEvent.ACTION_DOWN -> {
                        action.invoke()
                        onPressListener?.invoke(progress)
                    }

                    MotionEvent.ACTION_MOVE -> action.invoke()

                    MotionEvent.ACTION_UP -> onReleaseListener?.invoke(progress)

                }
                true
            }
        }
    }

    fun setOnProgressChangeListener(listener: ((Int) -> Unit)?) {
        this.onProgressChangeListener = listener
    }

    fun setOnPressListener(listener: ((Int) -> Unit)?) {
        this.onPressListener = listener
    }

    fun setOnReleaseListener(listener: ((Int) -> Unit)?) {
        this.onReleaseListener = listener
    }

    private fun updateViews() {
        with(binding) {
            barProgress.layoutParams.height = barBackground.height * progress / maxValue
            thumb.y = barProgress.top.toFloat()
            barProgress.requestLayout()
            thumb.requestLayout()
        }
    }
}
