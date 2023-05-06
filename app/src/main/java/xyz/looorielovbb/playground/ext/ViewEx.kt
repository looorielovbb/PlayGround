@file:Suppress("unused")

package xyz.looorielovbb.playground.ext

import android.view.View
import androidx.viewbinding.ViewBinding
import xyz.looorielovbb.playground.R

@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> View.getBinding(bind: (View) -> VB): VB =
    getTag(R.id.tag_view_binding) as? VB ?: bind(this).also { setTag(R.id.tag_view_binding, it) }

