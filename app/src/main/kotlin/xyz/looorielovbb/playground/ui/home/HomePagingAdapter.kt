package xyz.looorielovbb.playground.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import xyz.looorielovbb.playground.databinding.ItemArticleBinding
import xyz.looorielovbb.playground.pojo.Article
import xyz.looorielovbb.playground.ui.home.detail.WebActivity
import java.util.Locale

class HomePagingAdapter :
    PagingDataAdapter<Article, HomePagingAdapter.ViewHolder>(diffCallback = Article.diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it, position)
        }
    }

    inner class ViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val intent = Intent(binding.root.context, WebActivity::class.java)
        private val clickEvent: (View) -> Unit = {
            it.context.startActivity(intent)
        }

        fun bind(article: Article, position: Int) {
            val formattedTitle = formatTitle(article.title,position)
            val formattedAuthor = formatAuthor(article)
            val formattedPublishAt = formatPublishAt(article.niceShareDate)
            binding.apply {
                title.text = formattedTitle
                publishAt.text = formattedPublishAt
                author.text = formattedAuthor
                intent.putExtra("link", article.link)
                root.setOnClickListener(clickEvent)
            }
        }

        private fun formatTitle(title:String,position: Int): String {
//            val format = "%s.$title"
//            return String.format(Locale.getDefault(), format, position)
            return "${position+1}.$title"
        }

        private fun formatPublishAt(str: String): String {
            val format = "发布时间:%s"
            return String.format(Locale.getDefault(), format, str)
        }

        private fun formatAuthor(article: Article): String {
            val format = if (article.author.isNotEmpty()) "作者:%s" else "分享人:%s"
            return String.format(
                Locale.getDefault(),
                format,
                article.author.ifEmpty { article.shareUser.ifEmpty { "" } })
        }
    }
}