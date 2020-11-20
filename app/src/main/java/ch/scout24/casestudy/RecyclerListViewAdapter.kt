package ch.scout24.casestudy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerListViewAdapter(val onRepoClicked: OnRepoClicked) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var repoList: List<RepoDataModelItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RepoListItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoDataModelItem: RepoDataModelItem = repoList.get(position)

        val repoListItemHolder: RepoListItemHolder = holder as RepoListItemHolder
        repoListItemHolder.setListItem(repoDataModelItem)

        repoListItemHolder.itemView.setOnClickListener { v ->
            onRepoClicked.onRepoClicked(repoDataModelItem)
        }
    }

    fun setListOfRepos(repoDataModelList: List<RepoDataModelItem>) {
        repoList = repoDataModelList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

}