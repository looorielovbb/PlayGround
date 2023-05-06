package xyz.looorielovbb.playground.ui.home

import androidx.recyclerview.widget.DiffUtil
import xyz.looorielovbb.playground.databinding.ItemArticleBinding
import xyz.looorielovbb.playground.ext.toDateStr
import xyz.looorielovbb.playground.pojo.Article

class HomeDelegateAdapter : BindingViewDelegate<Article, ItemArticleBinding>(
    ItemArticleBinding::inflate, diffCallback = diffCallback
) {

    override fun onBindViewHolder(binding: ItemArticleBinding, position: Int) {
        val item = getItem(position)
        item?.apply {

        }
        item?.let {
            val leftTitle = if (it.author.isNotEmpty()) "作者:%s" else "分享人:%s"
            with(binding) {
                title.text = it.title
                publishAt.text = String.format("发布时间:%s", it.publishTime.toDateStr("yyyy-MM-dd"))
                author.text = String.format(leftTitle, it.author.ifEmpty { it.shareUser.ifEmpty { "" } })
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }
}