/*
 * Mustofa on 12/9/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.team

import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import id.mustofa.kadesport.MainNavGraphDirections
import id.mustofa.kadesport.ui.common.EntityListAdapter
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.TeamSimpleView
import id.mustofa.kadesport.view.withHolder

class TeamAdapter(
  private val glide: GlideRequests,
  private val simple: Boolean = false
) : EntityListAdapter() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder<View> {
    return if (simple) TeamSimpleView(glide, parent).withHolder(::navigateToDetail)
    else TeamView(glide, parent).withHolder(::navigateToDetail)
  }

  private fun navigateToDetail(view: View, position: Int) {
    val item = getItem(position)
    val action = MainNavGraphDirections.actionToTeamDetail(item.id)
    view.findNavController().navigate(action)
  }
}