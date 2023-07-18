package xyz.looorielovbb.playground.ui.favorite

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils

class ImageAdapter(private val imageUrls: List<String>) : BannerAdapter<String, ImageAdapter.ImageHolder>(imageUrls) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val imageView = ImageView(parent.context)
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //通过裁剪实现圆角
        BannerUtils.setBannerRound(imageView, 20f)
        return ImageHolder(imageView)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int, payloads: MutableList<Any>) {
        Log.d("TAG", "onBindViewHolder,position: $position")
        Log.d("TAG", "onBindViewHolder,payloads: $payloads")
        holder.imageView.load(imageUrls[0])
    }

    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view as ImageView
    }
}

