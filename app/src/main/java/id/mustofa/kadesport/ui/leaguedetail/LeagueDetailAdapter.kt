/*
 * Mustofa on 12/2/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import android.view.View
import android.view.ViewGroup
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.ui.common.EntityListAdapter
import id.mustofa.kadesport.ui.leaguedetail.view.ClusterListView
import id.mustofa.kadesport.ui.leaguedetail.view.LeagueView
import id.mustofa.kadesport.ui.leaguedetail.view.LoadingView
import id.mustofa.kadesport.ui.leaguedetail.view.MessageView
import id.mustofa.kadesport.util.GlideRequests
import id.mustofa.kadesport.view.withHolder

class LeagueDetailAdapter(private val glide: GlideRequests) : EntityListAdapter() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder<View> {
    return when (viewType) {
      R.id.leagueView -> LeagueView(glide, parent).withHolder()
      R.id.clusterListView -> ClusterListView(parent.context).withHolder()
      R.id.messageView -> MessageView(parent.context).withHolder()
      R.id.loadingView -> LoadingView(parent.context).withHolder()
      else -> throw IllegalArgumentException("Unknown view type: $viewType")
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is League -> R.id.leagueView
      is ClusterListView.Model -> R.id.clusterListView
      is LoadingView.Model -> R.id.loadingView
      is MessageView.Model -> R.id.messageView
      else -> 0
    }
  }
}