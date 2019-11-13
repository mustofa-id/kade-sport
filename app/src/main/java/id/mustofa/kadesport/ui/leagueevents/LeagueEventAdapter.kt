/*
 * Mustofa on 11/2/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueevents

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.util.GlideRequests

class LeagueEventAdapter(
  private val glide: GlideRequests,
  private val onItemClick: (LeagueEvent) -> Unit
) : ListAdapter<LeagueEvent, LeagueEventAdapter.EventViewHolder>(EventDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    EventViewHolder(LeagueEventItemView(parent, glide)).apply {
      itemView.setOnClickListener {
        onItemClick(getItem(adapterPosition))
      }
    }

  override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
    holder.eventItemView.bind(getItem(position))
  }

  inner class EventViewHolder(val eventItemView: LeagueEventItemView) :
    RecyclerView.ViewHolder(eventItemView.create())

  object EventDiffCallback : DiffUtil.ItemCallback<LeagueEvent>() {
    override fun areItemsTheSame(old: LeagueEvent, new: LeagueEvent) = old.id == new.id
    override fun areContentsTheSame(old: LeagueEvent, new: LeagueEvent) = old == new
  }
}