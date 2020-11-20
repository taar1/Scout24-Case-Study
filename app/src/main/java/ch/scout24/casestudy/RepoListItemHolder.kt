package ch.scout24.casestudy

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class RepoListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setListItem(repoDataModel: RepoDataModelItem) {
        itemView.labelTitle.text = "TITLE"
        itemView.labelLanguage.text = "LANGUAGE"
        itemView.labelWatchers.text = "WATCHERS"

        itemView.valueTitle.text = repoDataModel.name
        itemView.valueLanguage.text = repoDataModel.language
        itemView.valueWatchers.text = repoDataModel.watchers.toString()

    }

}