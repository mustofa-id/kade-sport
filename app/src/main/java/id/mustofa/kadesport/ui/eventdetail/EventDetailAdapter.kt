/*
 * Mustofa on 12/5/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.eventdetail

import android.view.View
import android.view.ViewGroup
import id.mustofa.kadesport.ui.common.EntityListAdapter
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.withHolder

class EventDetailAdapter(private val glide: GlideRequests) : EntityListAdapter() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder<View> {
    val detailView = EventDetailView(parent.context)
    detailView.eventBadge.setGlide(glide)
    return detailView.withHolder()
  }
}