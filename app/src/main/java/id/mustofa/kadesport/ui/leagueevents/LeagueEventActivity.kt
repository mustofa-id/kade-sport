/*
 * Mustofa on 11/1/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueevents

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.listingView
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import id.mustofa.kadesport.ui.common.EventAdapter
import id.mustofa.kadesport.ui.common.EventType
import id.mustofa.kadesport.ui.common.ListingView
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.find

class LeagueEventActivity : AppCompatActivity() {

  private val model by viewModel<LeagueEventViewModel>()

  private lateinit var eventView: ListingView
  private lateinit var eventAdapter: EventAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    eventAdapter = EventAdapter(this)
    eventView = listingView {
      adapter = eventAdapter
      layoutManager = LinearLayoutManager(context)
    }
    subscribeObservers()
    loadEvents()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    return super.onOptionsItemSelected(item)
  }

  private fun subscribeObservers() {
    val root = find<View>(android.R.id.content)
    observe(model.events) { eventAdapter.submitList(it) }
    observe(model.loading) { eventView.isLoading(it) }
    observe(model.notifier) { root.indefiniteSnackbar(it, R.string.close) {} }
    observe(model.message) { eventView.setMessage(it) }
  }

  private fun loadEvents() {
    intent.extras?.let {
      val id = it.getLong(LEAGUE_ID)
      val type = it.getSerializable(EVENT_TYPE) as EventType
      model.loadEvents(id, type)
    }
  }

  companion object {
    const val LEAGUE_ID = "_leagueId_"
    const val EVENT_TYPE = "_eventType_"
  }
}
