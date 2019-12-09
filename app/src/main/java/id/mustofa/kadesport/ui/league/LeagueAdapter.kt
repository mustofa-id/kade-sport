/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import id.mustofa.kadesport.ui.common.EntityListAdapter
import id.mustofa.kadesport.ui.home.HomeFragmentDirections
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.withHolder

class LeagueAdapter(private val glide: GlideRequests) : EntityListAdapter() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder<View> {
    return LeagueItemView(glide, parent).withHolder(::navigateToDetail)
  }

  private fun navigateToDetail(view: View, position: Int) {
    getItem(position)?.let {
      val action = HomeFragmentDirections.actionToLeagueDetail(it.id)
      view.findNavController().navigate(action)
    }
  }
}