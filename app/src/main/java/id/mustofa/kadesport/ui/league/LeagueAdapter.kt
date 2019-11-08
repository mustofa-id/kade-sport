/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.League
import id.mustofa.kadesport.util.GlideRequests
import org.jetbrains.anko.find

class LeagueAdapter(private val glide: GlideRequests) :
  ListAdapter<League, LeagueAdapter.LeagueViewHolder>(LeagueDiffCallback) {

  var onItemClick: ((League) -> Unit)? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    LeagueViewHolder(LeagueItemView.create(parent)).apply {
      onItemClick?.let {
        itemView.setOnClickListener {
          it(getItem(adapterPosition))
        }
      }
    }


  override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
    holder.bindLeague(getItem(position))
  }

  inner class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindLeague(league: League) {
      with(itemView) {
        find<TextView>(R.id.itemTitle).text = league.name
        glide.load(league.badgeUrl).into(find(R.id.itemBadge))
      }
    }
  }

  object LeagueDiffCallback : DiffUtil.ItemCallback<League>() {
    override fun areItemsTheSame(old: League, new: League) = old.id == new.id
    override fun areContentsTheSame(old: League, new: League) = old == new
  }
}