/*
 * Mustofa on 12/6/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.data.entity.base.Entity
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.util.GlideApp
import id.mustofa.kadesport.view.StateView
import id.mustofa.kadesport.view.onQuerySubmit
import id.mustofa.kadesport.view.stateView
import org.jetbrains.anko.appcompat.v7.searchView
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.dip
import org.jetbrains.anko.horizontalMargin
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.verticalLayout

class SearchFragment : Fragment() {

  private val model: SearchViewModel by viewModel()

  private lateinit var adapter: SearchAdapter
  private lateinit var stateView: StateView<List<Entity>>
  private lateinit var searchView: SearchView

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = UI {
    verticalLayout {
      lparams(matchParent, matchParent)
      cardView {
        id = R.id.searchView
        searchView = searchView {
          id = R.id.searchInput
          setIconifiedByDefault(false)
          queryHint = getString(R.string.title_event_search)
          requestFocus()
          onQuerySubmit { it?.let { query -> model.search(query) }; true }
        }.lparams(matchParent)
      }.lparams(matchParent) {
        horizontalMargin = dip(16)
        topMargin = dip(16)
      }
      stateView = stateView()
    }
  }.view

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupStateView()
    subscribeObservers()
    searchView.setQuery(model.currentQuery, false)
  }

  private fun setupStateView() {
    adapter = SearchAdapter(GlideApp.with(this))
    stateView.setup(adapter, LinearLayoutManager(context))
  }

  private fun subscribeObservers() {
    observe(model.eventState) { stateView.handleState(it, adapter::submitList) }
  }
}