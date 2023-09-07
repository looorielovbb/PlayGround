package xyz.looorielovbb.playground.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindingViewDelegate<T : Any, VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup, Boolean) -> VB,
    diffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, BindingViewDelegate.BindingViewHolder<VB>>(diffCallback) {

    protected lateinit var binding: VB

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<VB> {
        binding = inflate(LayoutInflater.from(parent.context), parent, false)
        return BindingViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        onBindViewHolder(position)
    }

    abstract fun onBindViewHolder(position: Int)

    class BindingViewHolder<VB : ViewBinding>(binding: VB) :
        RecyclerView.ViewHolder(binding.root)
}