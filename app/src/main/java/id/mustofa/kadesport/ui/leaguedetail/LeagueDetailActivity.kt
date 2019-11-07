/*
 * Mustofa on 10/30/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leaguedetail

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import id.mustofa.kadesport.R
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView

class LeagueDetailActivity : AppCompatActivity() {

  private val model by viewModel<LeagueDetailViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    subscribeObservers()
    supportActionBar?.apply {
      setDisplayHomeAsUpEnabled(true)
      elevation = 0f
    }
  }

  private fun subscribeObservers() {
    intent.extras?.getLong(EXTRA_LEAGUE_ID)?.let { model.loadLeague(it) }
    observe(model.leagueState) { LeagueDetailView(it).setContentView(this) }
    observe(model.pastEventError) { find<TextView>(R.id.eventPast).setText(it) }
    observe(model.nextEventError) { find<TextView>(R.id.eventNext).setText(it) }
    observe(model.pastEvents) {
      val event = find<TextView>(R.id.eventPast)
      event.text = it.joinToString(separator = "\n") { events ->
        with(events) { "$homeScore : $awayScore $home VS $away" }
      }
    }
    observe(model.nextEvents) {
      val event = find<TextView>(R.id.eventNext)
      event.text = it.joinToString(separator = "\n") { events ->
        with(events) { "$date $time $home VS $away" }
      }
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    return super.onOptionsItemSelected(item)
  }

  companion object {
    const val EXTRA_LEAGUE_ID = "_ExtraLeagueId__"
  }
}