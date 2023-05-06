@file:Suppress("unused")

package xyz.looorielovbb.playground.ext

import android.view.View
import androidx.viewbinding.ViewBinding
import com.google.android.material.navigation.NavigationView

fun <VB : ViewBinding> NavigationView.updateHeaderView(
    index: Int = 0,
    bind: (View) -> VB,
    block: VB.() -> Unit
) = getHeaderView(index)?.let { bind(it) }?.run(block)