package xyz.looorielovbb.playground.ext

import android.app.Dialog
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import kotlin.LazyThreadSafetyMode.NONE

fun <VB : ViewBinding> Dialog.binding(inflate: (LayoutInflater) -> VB) = lazy(NONE) {
    inflate(layoutInflater).also { setContentView(it.root) }
}
