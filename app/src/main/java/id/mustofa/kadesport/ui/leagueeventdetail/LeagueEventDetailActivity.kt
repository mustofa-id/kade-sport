/*
 * Mustofa on 11/8/19
 * https://mustofa.id
 */
package id.mustofa.kadesport.ui.leagueeventdetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import id.mustofa.kadesport.ext.observe
import id.mustofa.kadesport.ext.viewModel
import org.jetbrains.anko.setContentView

class LeagueEventDetailActivity : AppCompatActivity() {

  private val model by viewModel<LeagueEventDetailViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    loadExtras()
    subscribeObservers()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) onBackPressed()
    return super.onOptionsItemSelected(item)
  }

  private fun loadExtras() {
    intent.extras?.let {
      val eventId = it.getLong(EVENT_ID)
      model.loadEvent(eventId)
    }
  }

  private fun subscribeObservers() {
    observe(model.eventState) {
      LeagueEventDetailView(it).setContentView(this)
    }
  }

  companion object {
    const val EVENT_ID = "_ExtraEventId_"
  }
}