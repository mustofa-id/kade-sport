/*
 * Mustofa on 11/2/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.common

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.kadesport.data.Event
import id.mustofa.kadesport.ext.selectableItemBackground
import id.mustofa.kadesport.ui.leagueeventdetail.LeagueEventDetailActivity
import id.mustofa.kadesport.ui.leagueeventdetail.LeagueEventDetailActivity.Companion.EVENT_ID
import org.jetbrains.anko.startActivity

class EventAdapter(private val context: Context) :
  ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
    val view = EventView(context).apply { background = selectableItemBackground(context) }
    return EventViewHolder(view).apply {
      itemView.setOnClickListener {
        val event = getItem(adapterPosition)
        context.startActivity<LeagueEventDetailActivity>(EVENT_ID to event.id)
      }
    }
  }

  override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
    holder.view.bind(getItem(position))
  }

  inner class EventViewHolder(val view: EventView) : RecyclerView.ViewHolder(view)

  object EventDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(old: Event, new: Event) = old.id == new.id
    override fun areContentsTheSame(old: Event, new: Event) = old == new
  }
}