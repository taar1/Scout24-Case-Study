package ch.scout24.casestudy

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class RepoListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var view: View

    fun RepoListItemHolder(view: View) {
        this.view = view
        view.labelId.text = "ID"
        view.labelNodeId.text = "NODE ID"
        view.labelName.text = "NAME"
    }

    fun setListItem(repoDataModel: RepoDataModelItem) {
        view.valueId.text = repoDataModel.id.toString()
        view.valueNodeId.text = repoDataModel.node_id
        view.valueName.text = repoDataModel.name

    }

}