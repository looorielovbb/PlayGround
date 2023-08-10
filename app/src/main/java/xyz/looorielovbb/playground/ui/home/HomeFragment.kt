package xyz.looorielovbb.playground.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.AlphaPageTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.FragmentHomeBinding
import xyz.looorielovbb.playground.ext.binding
import xyz.looorielovbb.playground.pojo.BannerData
import xyz.looorielovbb.playground.ui.home.detail.WebActivity
import xyz.looorielovbb.playground.utils.DefaultItemDecoration

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by binding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var pagingAdapter: HomeDelegateAdapter
    private lateinit var bannerAdapter: BannerImageAdapter<BannerData>

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
                viewModel.fetchBannerData()
            }
            bannerAdapter = object : BannerImageAdapter<BannerData>() {
                override fun onBindView(
                    holder: BannerImageHolder,
                    data: BannerData,
                    position: Int,
                    size: Int
                ) {
                    holder.imageView.load(data.imagePath)
                    holder.itemView.setOnClickListener {
                        if (data.url.isNotBlank()) {
                            val intent = Intent(requireActivity(), WebActivity::class.java)
                            intent.putExtra("link", data.url)
                            startActivity(intent)
                        } else {
                            Toast.makeText(context, "无地址", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            banner.apply {
                addBannerLifecycleObserver(viewLifecycleOwner)
                setBannerRound(20f)
                indicator = CircleIndicator(activity)
                intercept = true
                setAdapter(bannerAdapter)
                //添加魅族效果
                setBannerGalleryMZ(16)
                addPageTransformer(AlphaPageTransformer())
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    pagingAdapter.loadStateFlow.collect {
                        when (it.refresh) {
                            is LoadState.Loading -> binding.swiper.isRefreshing = true
                            is LoadState.Error -> binding.swiper.isRefreshing = false
                            is LoadState.NotLoading -> binding.swiper.isRefreshing = false
                        }
                    }
                }
                launch {
                    viewModel.articlesFlow.collect(pagingAdapter::submitData)
                }
                launch {
                    viewModel.bannerData.collect { state ->
                        when (state) {
                            is HomeState.Loading -> {
                                Toast.makeText(context, "加载中...", Toast.LENGTH_LONG).show()
                            }

                            is HomeState.Failure -> {
                                Toast.makeText(
                                    context,
                                    "加载失败,原因：${state.e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            is HomeState.Success -> {
                                val listData: List<BannerData> = state.data
                                bannerAdapter.setDatas(listData)
                            }
                        }
                    }
                }
            }
        }
        viewModel.fetchBannerData()
    }

}