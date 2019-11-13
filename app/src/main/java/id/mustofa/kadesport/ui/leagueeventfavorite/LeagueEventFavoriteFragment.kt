/*
 * Mustofa on 11/12/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventfavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.mustofa.kadesport.data.LeagueEvent
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.leagueeventdetail.LeagueEventDetailActivity
import id.mustofa.kadesport.ui.leagueeventdetail.LeagueEventDetailActivity.Companion.EVENT_ID
import id.mustofa.kadesport.ui.leagueevents.LeagueEventAdapter
import id.mustofa.kadesport.util.GlideApp
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.startActivity

class LeagueEventFavoriteFragment : Fragment() {

  private val model by viewModel<LeagueEventFavoriteViewModel>()
  private lateinit var adapter: LeagueEventAdapter
  private lateinit var view: LeagueEventFavoriteView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    adapter = LeagueEventAdapter(GlideApp.with(this), ::toDetail)
    view = LeagueEventFavoriteView(adapter)
    val ankoContext = AnkoContext.Companion.create(inflater.context, this, false)
    return view.createView(ankoContext)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    observe(model.favoriteEvents) { adapter.submitList(it) }
    observe(model.loading) { view.isLoading(it) }
    observe(model.message) { view.setMessage(it) }
  }

  override fun onStart() {
    super.onStart()
    model.loadEvents()
  }

  private fun toDetail(it: LeagueEvent) {
    activity?.startActivity<LeagueEventDetailActivity>(EVENT_ID to it.id)
  }
}