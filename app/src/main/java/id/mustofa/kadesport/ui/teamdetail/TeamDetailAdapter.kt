package id.mustofa.kadesport.ui.teamdetail

import android.view.View
import android.view.ViewGroup
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.ui.common.EntityListAdapter
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.withHolder

class TeamDetailAdapter(private val glide: GlideRequests) : EntityListAdapter() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder<View> {
    return TeamDetailView(glide, parent).withHolder()
  }

  fun setTeam(team: Team?) {
    if (team == null) return
    val list = currentList
      .toMutableList().apply { add(0, team) }
    submitList(list)
  }
}
