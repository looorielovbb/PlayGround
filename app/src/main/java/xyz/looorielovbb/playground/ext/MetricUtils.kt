package xyz.looorielovbb.playground.ext

import android.util.DisplayMetrics
import xyz.looorielovbb.playground.PlayGround
import kotlin.math.roundToInt

fun Int.dpToPx(): Int {
    val displayMetrics: DisplayMetrics = PlayGround.application.resources.displayMetrics
    return (this * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun Int.pxToDp(): Int {
    val displayMetrics: DisplayMetrics = PlayGround.application.resources.displayMetrics
    return (this / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}