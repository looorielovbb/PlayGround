package xyz.looorielovbb.playground.ext

import android.content.res.Resources
import android.util.TypedValue
import kotlin.math.roundToInt

fun Int.dpToPx(): Int {
    return this.toFloat().dpToPx()
}

fun Float.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    ).roundToInt()
}