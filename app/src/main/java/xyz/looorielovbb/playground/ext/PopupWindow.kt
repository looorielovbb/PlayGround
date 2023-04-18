@file:Suppress("unused")

package xyz.looorielovbb.playground.ext

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.LazyThreadSafetyMode.NONE

fun <VB : ViewBinding> Activity.popupWindow(
    inflate: (LayoutInflater) -> VB,
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    focusable: Boolean = false,
    block: VB.() -> Unit
) = lazy(NONE) {
    PopupWindow(inflate(layoutInflater).apply(block).root, width, height, focusable)
}

fun <VB : ViewBinding> Fragment.popupWindow(
    inflate: (LayoutInflater) -> VB,
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    focusable: Boolean = false,
    block: VB.() -> Unit
) = lazy(NONE) {
    PopupWindow(inflate(layoutInflater).apply(block).root, width, height, focusable)
}
