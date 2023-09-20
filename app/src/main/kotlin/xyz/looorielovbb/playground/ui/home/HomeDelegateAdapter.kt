package xyz.looorielovbb.playground.ui.home

import android.content.Intent
import xyz.looorielovbb.playground.databinding.ItemArticleBinding
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.ui.home.detail.WebActivity

class HomeDelegateAdapter : BindingViewDelegate<Article, ItemArticleBinding>(
    ItemArticleBinding::inflate, diffCallback = Article.diffCallback
) {

    override fun onBindViewHolder(position: Int) {
        val item = getItem(position)
        item?.let { article ->
            val leftTitle = if (article.author.isNotEmpty()) "作者:%s" else "分享人:%s"
            with(binding) {
                root.setOnClickListener { view ->
                    val intent = Intent(view.context, WebActivity::class.java)
                    intent.putExtra("link", article.link)
                    view.context.startActivity(intent)
                }
                title.text = article.title
                publishAt.text =
                    String.format("发布时间:%s", article.niceShareDate)
                author.text =
                    String.format(
                        leftTitle,
                        article.author.ifEmpty { article.shareUser.ifEmpty { "" } })
            }
        }
    }


}