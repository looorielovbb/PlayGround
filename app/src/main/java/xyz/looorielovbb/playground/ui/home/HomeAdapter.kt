package xyz.looorielovbb.playground.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import xyz.looorielovbb.playground.R
import xyz.looorielovbb.playground.databinding.ItemArticleBinding
import xyz.looorielovbb.playground.pojo.Article

class HomeAdapter :
    PagingDataAdapter<Article, HomeAdapter.ArticleViewHolder>(diffCallback = diffCallback) {

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }


    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemArticleBinding.bind(itemView)
        fun bind(item: Article?) {
            item?.let {
                with(binding) {
                    title.text = it.title
                    date.text = it.niceDate
                    publishAt.text = it.publishTime.toString()
                    author.text = it.author.ifEmpty { it.shareUser.ifEmpty { "" } }
                }
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

