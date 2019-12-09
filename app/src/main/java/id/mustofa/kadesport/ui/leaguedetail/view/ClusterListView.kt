package id.mustofa.kadesport.ui.leaguedetail.view

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.view.ClusterView
import id.mustofa.kadesport.view.base.EntityView
import id.mustofa.kadesport.view.clusterView
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.horizontalPadding
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView

class ClusterListView(context: Context) :
  _FrameLayout(context), EntityView<ClusterListView.Model> {

  data class Model(
    override val id: Long,
    val name: String,
    val adapter: RecyclerView.Adapter<*>,
    inline val onClick: (View) -> Unit
  ) : Entity

  private val cluster: ClusterView
  private lateinit var recyclerView: RecyclerView

  init {
    id = R.id.clusterListView
    lparams(matchParent)
    cluster = clusterView {
      recyclerView = recyclerView {
        clipToPadding = false
        horizontalPadding = dip(14)
      }.lparams(matchParent)
    }
  }

  init {
    val layoutManager = LinearLayoutManager(
      context,
      LinearLayoutManager.HORIZONTAL,
      false
    )
    recyclerView.layoutManager = layoutManager
  }

  override fun bind(e: Model) {
    cluster.setTitle(e.name)
    cluster.setOnClickListener(e.onClick)
    recyclerView.adapter = e.adapter
  }
}
