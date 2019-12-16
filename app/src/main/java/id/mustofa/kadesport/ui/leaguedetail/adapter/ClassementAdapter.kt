/*
 * Mustofa on 12/14/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail.adapter

import android.view.ViewGroup
import id.mustofa.kadesport.data.entity.Standing
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.ui.common.EntityListAdapter
import id.mustofa.kadesport.ui.leaguedetail.view.ClassementView
import id.mustofa.kadesport.ui.leaguedetail.view.ClassementView.Companion.TYPE_HEADER
import id.mustofa.kadesport.ui.leaguedetail.view.ClassementView.Companion.TYPE_ODD_ROW
import id.mustofa.kadesport.view.withHolder

class ClassementAdapter : EntityListAdapter() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ClassementView(parent.context).withType(viewType).withHolder()

  override fun getItemViewType(position: Int) = when {
    position == 0 -> TYPE_HEADER
    position % 2 != 0 -> TYPE_ODD_ROW
    else -> super.getItemViewType(position)
  }

  override fun submitList(list: List<Entity>?) {
    val header = listOf(Standing.EMPTY)
    val data = list?.let { header + it } ?: header
    super.submitList(data)
  }
}