package xyz.looorielovbb.playground.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.load
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.FragmentFavBinding
import xyz.looorielovbb.playground.ext.binding

@AndroidEntryPoint
class FavFragment : Fragment(R.layout.fragment_fav) {

    private val binding by binding(FragmentFavBinding::bind)

    private var imageUrls = listOf(
        "https://img.zcool.cn/community/01b72057a7e0790000018c1bf4fce0.png",
        "https://img.zcool.cn/community/016a2256fb63006ac7257948f83349.jpg",
        "https://img.zcool.cn/community/01233056fb62fe32f875a9447400e1.jpg",
        "https://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            banner.apply {
                addBannerLifecycleObserver(this@FavFragment)
                setBannerRound(20f)
                indicator = CircleIndicator(activity)
                setAdapter(object :BannerImageAdapter<String>(imageUrls){
                    override fun onBindView(
                        holder: BannerImageHolder,
                        data: String,
                        position: Int,
                        size: Int
                    ) {
                        holder.imageView.load(imageUrls[position])
                    }
                })
            }
        }
    }

}