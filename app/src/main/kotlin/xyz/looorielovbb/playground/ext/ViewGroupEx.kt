package xyz.looorielovbb.playground.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlin.LazyThreadSafetyMode.NONE

fun <VB : ViewBinding> ViewGroup.inflate(inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB) =
    inflate(LayoutInflater.from(context), this, true)

fun <VB : ViewBinding> ViewGroup.binding(
    inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    attachToParent: Boolean = false
) = lazy(NONE) {
    inflate(LayoutInflater.from(context), if (attachToParent) this else null, attachToParent)
}
