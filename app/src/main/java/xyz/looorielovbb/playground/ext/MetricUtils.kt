package xyz.looorielovbb.playground.ext

import android.content.ContextWrapper
import android.util.DisplayMetrics
import xyz.looorielovbb.playground.PlayGround
import kotlin.math.roundToInt

fun Int.dpToPx(): Int {
    return PlayGround.application.dpToPx(this)
}

@Suppress("unused")
fun Int.pxToDp(): Int {
    return PlayGround.application.pxToDp(this)
}

fun ContextWrapper.dpToPx(dp: Int): Int {
    val displayMetrics = this.resources.displayMetrics
    return (dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun ContextWrapper.pxToDp(dp: Int): Int {
    val displayMetrics = this.resources.displayMetrics
    return (dp / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}