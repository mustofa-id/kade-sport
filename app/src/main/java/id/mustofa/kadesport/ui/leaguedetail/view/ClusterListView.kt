/*
 * Mustofa on 12/15/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail.view

import android.content.Context
import android.view.ViewManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.State
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.ui.common.EntityListAdapter
import id.mustofa.kadesport.view.ClusterView
import id.mustofa.kadesport.view.StateView
import id.mustofa.kadesport.view.clusterView
import id.mustofa.kadesport.view.stateView
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.dip
import org.jetbrains.anko.horizontalPadding
import org.jetbrains.anko.matchParent

class ClusterListView<T : Entity>(context: Context) : _FrameLayout(context) {

  private val cluster: ClusterView
  private lateinit var stateView: StateView<List<T>>
  private lateinit var adapter: EntityListAdapter

  init {
    id = R.id.clusterListView
    lparams(matchParent)
    cluster = clusterView {
      id = R.id.clusterListContainer
      stateView = stateView<List<T>> {
        setRecyclerView {
          id = R.id.clusterList
          clipToPadding = false
          horizontalPadding = dip(14)
          isNestedScrollingEnabled = true
        }
      }.lparams(matchParent)
    }
  }

  fun setup(
    title: String,
    adapter: EntityListAdapter,
    vertical: Boolean = false,
    onClick: ((String) -> Unit)? = null
  ) {
    this.adapter = adapter
    cluster.setTitle(title)
    val layoutManager = if (vertical) {
      stateView.setRecyclerView { isNestedScrollingEnabled = false }
      LinearLayoutManager(context)
    } else {
      LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
    stateView.setup(adapter, layoutManager)
    onClick?.let { cluster.setOnClickListener { it(title) } }
  }

  fun setState(state: State<List<T>>) {
    stateView.handleState(state, adapter::submitList)
  }

  companion object {
    fun <T : Entity> ViewManager.clusterListView(): ClusterListView<T> {
      return ankoView({ ClusterListView(it) }, 0) {}
    }
  }
}

