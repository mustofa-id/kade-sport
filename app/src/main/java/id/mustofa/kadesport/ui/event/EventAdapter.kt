/*
 * Mustofa on 11/2/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.event

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import id.mustofa.kadesport.MainNavGraphDirections
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.Event
import id.mustofa.kadesport.ext.isBlank
import id.mustofa.kadesport.ui.common.EntityListAdapter
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.EventBadgeView
import id.mustofa.kadesport.view.EventView
import id.mustofa.kadesport.view.withHolder

class EventAdapter(private val glide: GlideRequests) : EntityListAdapter() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
    R.id.eventBadgeView -> EventBadgeView(glide, parent).withHolder(::onItemClick)
    R.id.eventView -> EventView(parent.context).withHolder(::onItemClick)
    else -> throw IllegalArgumentException("Unknown view type: $viewType")
  }

  override fun getItemViewType(position: Int): Int {
    val entity = getItem(position)
    if (entity !is Event) return 0
    return entity.run {
      if (isBlank(homeBadgePath, awayBadgePath))
        R.id.eventView else R.id.eventBadgeView
    }
  }

  private fun onItemClick(view: View, position: Int) {
    val event = getItem(position)
    val action = MainNavGraphDirections.actionToEventDetail(event.id)
    view.findNavController().navigate(action)
  }
}