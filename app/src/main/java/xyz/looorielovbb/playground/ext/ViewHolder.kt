@file:Suppress("unused")

package xyz.looorielovbb.playground.ext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

fun <VB : ViewBinding> RecyclerView.ViewHolder.withBinding(
    bind: (View) -> VB,
    block: VB.(RecyclerView.ViewHolder) -> Unit
) = apply {
    block(getBinding(bind), this@withBinding)
}

fun <VB : ViewBinding> BindingViewHolder<VB>.withBinding(block: VB.(BindingViewHolder<VB>) -> Unit) =
    apply {
        block(binding, this@withBinding)
    }

fun <VB : ViewBinding> RecyclerView.ViewHolder.getBinding(bind: (View) -> VB) =
    itemView.getBinding(bind)

class BindingViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root) {
    constructor(parent: ViewGroup, inflate: (LayoutInflater, ViewGroup, Boolean) -> VB) :
            this(inflate(LayoutInflater.from(parent.context), parent, false))
}

fun <T> OnItemClickListener<T>.onItemClick(holder: RecyclerView.ViewHolder, block: (Int) -> T) =
    onItemClick(block(holder.bindingAdapterPosition), holder.bindingAdapterPosition)

fun interface OnItemClickListener<T> {
    fun onItemClick(item: T, position: Int)
}
