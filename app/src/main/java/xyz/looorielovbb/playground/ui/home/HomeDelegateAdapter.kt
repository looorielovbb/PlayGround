package xyz.looorielovbb.playground.ui.home

import android.content.Intent
import androidx.recyclerview.widget.DiffUtil
import xyz.looorielovbb.playground.databinding.ItemArticleBinding
import xyz.looorielovbb.playground.ext.toDateStr
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.ui.home.detail.WebActivity

class HomeDelegateAdapter : BindingViewDelegate<Article, ItemArticleBinding>(
    ItemArticleBinding::inflate, diffCallback = diffCallback
) {

    override fun onBindViewHolder(binding: ItemArticleBinding, position: Int) {
        val item = getItem(position)
        item?.let { article ->
            val leftTitle = if (article.author.isNotEmpty()) "作者:%s" else "分享人:%s"
            with(binding) {
                binding.root.setOnClickListener { view ->
                    val intent = Intent(view.context, WebActivity::class.java)
                    intent.putExtra("link", article.link)
                    view.context.startActivity(intent)
                }
                title.text = article.title
                publishAt.text =
                    String.format("发布时间:%s", article.publishTime.toDateStr("yyyy-MM-dd"))
                author.text =
                    String.format(
                        leftTitle,
                        article.author.ifEmpty { article.shareUser.ifEmpty { "" } })
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