package xyz.looorielovbb.playground.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.FragmentHomeBinding
import xyz.looorielovbb.playground.ext.binding
import xyz.looorielovbb.playground.ui.DefaultItemDecoration

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by binding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var pagingAdapter: HomeDelegateAdapter

    companion object {
//        const val TAG = "HomeFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagingAdapter = HomeDelegateAdapter()
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DefaultItemDecoration()
        with(binding) {
            recyclerView.adapter = pagingAdapter
            recyclerView.layoutManager = layoutManager
            recyclerView.addItemDecoration(dividerItemDecoration)
            swiper.setOnRefreshListener {
                pagingAdapter.refresh()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.flowData.collectLatest(pagingAdapter::submitData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> binding.swiper.isRefreshing = true
                    is LoadState.Error -> binding.swiper.isRefreshing = false
                    is LoadState.NotLoading -> binding.swiper.isRefreshing = false
                }
                when (it.append) {
                    is LoadState.Loading -> binding.bar.visibility = View.VISIBLE
                    is LoadState.Error -> binding.bar.visibility = View.GONE
                    is LoadState.NotLoading -> binding.bar.visibility = View.GONE
                }
            }
        }
    }

}