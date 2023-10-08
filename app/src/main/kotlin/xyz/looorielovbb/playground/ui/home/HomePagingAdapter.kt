package xyz.looorielovbb.playground.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.looorielovbb.playground.databinding.ItemArticleBinding
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.ui.home.detail.WebActivity

class HomePagingAdapter :
    PagingDataAdapter<Article, HomePagingAdapter.ViewHolder>(diffCallback = Article.diffCallback) {
    lateinit var binding: ItemArticleBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { article ->
            val leftTitle = if (article.author.isNotEmpty()) "作者:%s" else "分享人:%s"
            with(binding) {
                title.text = article.title
                publishAt.text =
                    String.format("发布时间:%s", article.niceShareDate)
                author.text =
                    String.format(
                        leftTitle,
                        article.author.ifEmpty { article.shareUser.ifEmpty { "" } })
                root.setOnClickListener {
                    val intent = Intent(it.context, WebActivity::class.java)
                    intent.putExtra("link", article.link)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    class ViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
}