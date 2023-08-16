package xyz.looorielovbb.playground.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.AlphaPageTransformer
import xyz.looorielovbb.playground.databinding.LayoutBannerBinding
import xyz.looorielovbb.playground.pojo.BannerData

class BannerHeadAdapter(
    private val bannerAdapter: BannerImageAdapter<BannerData>,
    private val owner: LifecycleOwner
) :
    RecyclerView.Adapter<BannerHeadAdapter.BannerHeadViewHolder>() {

    lateinit var binding: LayoutBannerBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerHeadViewHolder {
        binding = LayoutBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.banner.apply {
            addBannerLifecycleObserver(owner)
            setBannerRound(20f)
            indicator = CircleIndicator(parent.context)
            intercept = true
            adapter = bannerAdapter
            //添加魅族效果
            setBannerGalleryMZ(16)
            addPageTransformer(AlphaPageTransformer())
        }
        return BannerHeadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerHeadViewHolder, position: Int) {
    }


    override fun getItemCount(): Int {
        return 1
    }


    class BannerHeadViewHolder(val binding: LayoutBannerBinding) :
        RecyclerView.ViewHolder(binding.root)

}