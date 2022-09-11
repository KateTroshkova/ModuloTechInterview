package com.noveogroup.modulotechinterview.common.android.ext

import android.content.Context
import android.graphics.Insets
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.*
import androidx.annotation.LayoutRes
import com.noveogroup.modulotechinterview.domain.ext.safeCast

val Context.inflater: LayoutInflater
    get() = LayoutInflater.from(this)

val Context.systemServiceInflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE)?.safeCast<LayoutInflater>()!!

fun Context.inflateView(@LayoutRes layoutId: Int, root: ViewGroup? = null): View =
    systemServiceInflater.inflate(layoutId, root)

fun Context.screenViewportWidth(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = windowManager()?.currentWindowMetrics ?: return 0
        val insets: Insets =
            windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager()?.defaultDisplay?.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

fun Context.screenViewportHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = windowManager()?.currentWindowMetrics ?: return 0
        val insets: Insets =
            windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.height() - insets.top - insets.bottom
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager()?.defaultDisplay?.getMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
}

fun Context.screenRawWidth(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = windowManager()?.currentWindowMetrics ?: return 0
        windowMetrics.bounds.width()
    } else {
        val point = Point()
        @Suppress("DEPRECATION")
        windowManager()?.defaultDisplay?.getRealSize(point)
        point.x
    }
}

fun Context.screenRawHeight(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = windowManager()?.currentWindowMetrics ?: return 0
        windowMetrics.bounds.height()
    } else {
        val point = Point()
        @Suppress("DEPRECATION")
        windowManager()?.defaultDisplay?.getRealSize(point)
        point.y
    }
}

fun Context.windowManager(): WindowManager? =
    getSystemService(Context.WINDOW_SERVICE)?.safeCast<WindowManager>()
