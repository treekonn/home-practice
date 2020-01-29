package by.enolizard.paginglibrary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.enolizard.paginglibrary.api.response.Article
import by.enolizard.paginglibrary.api.response.FeedsPage
import by.enolizard.paginglibrary.base.DiffUtilCallback
import by.enolizard.paginglibrary.base.DiffUtilItemCallback
import by.enolizard.paginglibrary.databinding.FeedItemBinding

class UnpagedListAdapter
    : RecyclerView.Adapter<UnpagedListAdapter.ViewHolder>() {

    private val items: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FeedItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setData(it: List<Article>) {
        items.clear()
        items.addAll(it)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) {
            binding.tvMessage.text = item.title
        }
    }
}