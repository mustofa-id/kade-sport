/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.league

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.League
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.util.GlideApp
import id.mustofa.kadesport.view.StateView
import id.mustofa.kadesport.view.stateView
import org.jetbrains.anko.support.v4.UI

class LeagueFragment : Fragment() {

  private val model: LeagueViewModel by viewModel()

  private lateinit var adapter: LeagueAdapter
  private lateinit var stateView: StateView<List<League>>

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = UI { stateView = stateView() }.view

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupStateView()
    subscribeObservers()
  }

  private fun setupStateView() {
    val columnSpan = resources.getInteger(R.integer.list_league_col_span)
    adapter = LeagueAdapter(GlideApp.with(this))
    stateView.setup(adapter, GridLayoutManager(context, columnSpan))
  }

  private fun subscribeObservers() {
    observe(model.leagues) { stateView.handleState(it, adapter::submitList) }
  }
}
