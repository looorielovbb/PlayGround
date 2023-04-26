package xyz.looorielovbb.playground.ext

import android.util.DisplayMetrics
import xyz.looorielovbb.playground.App
import kotlin.math.roundToInt

fun Int.dpToPx(): Int {
    val displayMetrics: DisplayMetrics = App.instance.resources.displayMetrics
    return (this * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun Int.pxToDp(): Int {
    val displayMetrics: DisplayMetrics = App.instance.resources.displayMetrics
    return (this / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}