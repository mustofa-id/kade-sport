/*
 * Mustofa on 11/11/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.listingView
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.onQuerySubmit
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.common.EventAdapter
import id.mustofa.kadesport.ui.common.ListingView
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.searchView
import org.jetbrains.anko.cardview.v7.cardView

class LeagueEventSearchActivity : AppCompatActivity() {

  private val model by viewModel<LeagueEventSearchViewModel>()

  private lateinit var eventSearchView: ListingView
  private lateinit var eventAdapter: EventAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    eventAdapter = EventAdapter(this)
    relativeLayout {
      lparams(matchParent, matchParent)
      cardView {
        id = R.id.searchContainer
        searchView {
          id = R.id.searchInput
          setIconifiedByDefault(false)
          queryHint = getString(R.string.title_event_search)
          requestFocus()
          onQuerySubmit { it?.let { q -> model.search(q) } }
        }.lparams(matchParent)
      }.lparams(matchParent) {
        horizontalMargin = dip(16)
        topMargin = dip(16)
      }

      eventSearchView = listingView {
        layoutManager = LinearLayoutManager(context)
        adapter = eventAdapter
        clipToPadding = true
      }.lparams(matchParent, matchParent) {
        bottomOf(R.id.searchContainer)
        centerInParent()
      }
    }
    eventSearchView.isLoading(false)
    subscribeObservers()
  }

  private fun subscribeObservers() {
    observe(model.events) { eventAdapter.submitList(it) }
    observe(model.loading) { eventSearchView.isLoading(it) }
    observe(model.message) { eventSearchView.setMessage(it) }
  }
}