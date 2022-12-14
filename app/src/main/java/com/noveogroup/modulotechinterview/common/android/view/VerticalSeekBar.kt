package com.noveogroup.modulotechinterview.common.android.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import com.noveogroup.modulotechinterview.R
import com.noveogroup.modulotechinterview.common.android.ext.hide
import com.noveogroup.modulotechinterview.common.android.ext.show
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

    var onProgressChangeListener: ((Int) -> Unit)? = null
    var onPressListener: ((Int) -> Unit)? = null
    var onReleaseListener: ((Int) -> Unit)? = null

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
            binding.thumb.setColorFilter(value, android.graphics.PorterDuff.Mode.SRC_IN)
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

    private fun updateViews() {
        with(binding) {
            val height = barBackground.height * progress / maxValue
            if (height > 0) {
                barProgress.show()
                barProgress.layoutParams.height = height
            } else {
                barProgress.hide()
            }
            if (progress < 50) {
                thumb.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    topToTop = ConstraintSet.UNSET
                    bottomToTop = barProgress.id
                }
            } else {
                thumb.updateLayoutParams<ConstraintLayout.LayoutParams> {
                    topToTop = barProgress.id
                    bottomToTop = ConstraintSet.UNSET
                }
            }
            barProgress.requestLayout()
        }
    }
}
