/*
 * Mustofa on 12/6/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.common

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import id.mustofa.kadesport.MainNavGraphDirections
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.data.entity.Team
import id.mustofa.kadesport.ui.team.TeamView
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.EventBadgeView
import id.mustofa.kadesport.view.withHolder

class GroupedAdapter(private val glide: GlideRequests) : EntityListAdapter() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
    R.id.eventBadgeView -> EventBadgeView(glide, parent).withHolder(::navigateToEventDetail)
    R.id.teamView -> TeamView(glide, parent).withHolder(::navigateToTeamDetail)
    else -> throw IllegalArgumentException("Unknown view type: $viewType")
  }

  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is Event -> R.id.eventBadgeView
      is Team -> R.id.teamView
      else -> throw IllegalArgumentException("Item is not subclass of Entity")
    }
  }

  private fun navigateToEventDetail(view: View, position: Int) {
    val item = getItem(position)
    val action = MainNavGraphDirections.actionToEventDetail(item.id)
    view.findNavController().navigate(action)
  }

  private fun navigateToTeamDetail(view: View, position: Int) {
    val item = getItem(position)
    val action = MainNavGraphDirections.actionToTeamDetail(item.id)
    view.findNavController().navigate(action)
  }
}